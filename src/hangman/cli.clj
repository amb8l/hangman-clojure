(ns hangman.cli
  (:require [clojure.string :as str]
            [hangman.dictionary :refer :all]
            [hangman.hangman :refer :all])
  (:import (java.io FileNotFoundException)))

(declare get-guess)

(defn- game-result [game]
  (if (and (= :game-over (game :state)) (= 0 (game :guesses-remaining)))
    (str "You lost! The correct word was '" (str/join "" (game :letters)) "'.")
    "Congratulations, you won!")
  )

(defn- show-game-state [game]
  (println)
  (println (str "Guessed letters:   " (str/join " " (game :guessed-letters))))
  (println (str "Guesses remaining: " (game :guesses-remaining)))
  (println (str "Used letters:      " (str/join " " (game :used-letters))))
  (println (str "Incorrect letters: " (str/join " " (game :incorrect-letters))))
  (println)
  (if (= :game-over (game :state))
    (println (game-result game)))
  )

(defn valid-guess [guess]
  (not (nil? (re-matches #"[A-Za-z]" guess))))

(defn- validate-guess [guess]
  (if (valid-guess guess)
    guess
    (do (println "Invalid guess. Try again.")
        (get-guess))))

(defn- get-guess []
  (print "Guess a letter > ")
  (flush)
  (-> (read-line)
      (validate-guess)))

(defn- play-until-game-over [game]
  (show-game-state game)
  (if-not (= :game-over (game :state))
    (->> (get-guess)
         (play-turn game)
         (play-until-game-over))))

(defn start-cli
  ([]
   (println "Hangman")
   (-> (new-game (choose-random-word rand-nth (get-words-from-file)))
       (play-until-game-over)))
  ([file]
   (println "Hangman")
   (try
     (-> (new-game (choose-random-word rand-nth (get-words-from-file file)))
         (play-until-game-over))
     (catch FileNotFoundException e
       (do (println "File not found, using default dictionary.")
         (start-cli))))))
