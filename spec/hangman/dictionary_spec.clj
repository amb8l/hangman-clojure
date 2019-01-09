(ns hangman.dictionary-spec
  (:require [speclj.core :refer :all]
            [hangman.dictionary :refer :all]))

(defn word-list []
  ["Word", "another", "more"])

(defn rand-nth-stub [words]
  (get (words) 0))

(describe "dictionary of words"
          (it "selects a random word"
              (should= "word" (choose-random-word rand-nth-stub word-list)))

          )
