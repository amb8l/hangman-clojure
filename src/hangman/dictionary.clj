(ns hangman.dictionary
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn get-words-from-file
  ([]
   (str/split-lines (slurp (io/resource "words.txt"))))
  ([file]
    (str/split-lines (slurp (io/file file)))))

(defn choose-random-word [rand-func word-list]
  (str/lower-case (rand-func word-list)))
