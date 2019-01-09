(ns hangman.hangman
  (:require [clojure.string :as str]))

(defn- initialise-letters [word]
  (str/split word #""))

(defn- initialise-guessed-letters [word]
  (vec (for [x (range (count word))] "_")))

(defn new-game [word]
  (hash-map
    :letters (initialise-letters word)
    :used-letters []
    :guessed-letters (initialise-guessed-letters word)
    :incorrect-letters []
    :guesses-remaining 8
    :state :playing))

(defn- update-guessed-letter [idx ltr letter guessed-letters]
  (if (= ltr letter)
    ltr
    (guessed-letters idx)))

(defn- update-guessed-letters [guessed-letters letters letter]
  (map-indexed (fn [idx ltr] (update-guessed-letter idx ltr letter guessed-letters)) letters))

(defn- correct-guess [game letter]
  (hash-map
    :letters (game :letters)
    :used-letters (vec (sort (cons letter (game :used-letters))))
    :guessed-letters (vec (assoc (game :guessed-letters) (.indexOf (game :letters) letter) letter))
    :guessed-letters (vec (update-guessed-letters (game :guessed-letters) (game :letters) letter))
    :incorrect-letters (game :incorrect-letters)
    :guesses-remaining (game :guesses-remaining)
    :state (game :state)))

(defn- incorrect-guess [game letter]
  (hash-map
    :letters (game :letters)
    :used-letters (vec (sort (cons letter (game :used-letters))))
    :guessed-letters (game :guessed-letters)
    :incorrect-letters (vec (sort (cons letter (game :incorrect-letters))))
    :guesses-remaining (- (game :guesses-remaining) 1)
    :state (game :state)))

(defn- update-state [game]
  (if (or
        (= (game :letters) (game :guessed-letters))
        (= 0 (game :guesses-remaining)))
    (assoc game :state :game-over)
    (assoc game :state :playing)))

(defn- word-includes-letter [letters letter]
  (nil? (some #(= letter %) letters)))

(defn- apply-guess [game letter]
  (if-not (word-includes-letter (game :letters) letter)
    (update-state (correct-guess game letter))
    (update-state (incorrect-guess game letter))))

(defn- process-guess [game letter]
  (if (nil? (some #(= letter %) (game :used-letters)))
    (apply-guess game letter)
    game))

(defn play-turn [game letter]
  (if (= (game :state) :game-over)
    game
    (process-guess game (str/lower-case letter))))
