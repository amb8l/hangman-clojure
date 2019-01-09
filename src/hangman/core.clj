(ns hangman.core
  (:require [hangman.cli :refer :all]))

(defn -main
  [& args]
  (if (= 0 (count args))
    (start-cli)
    (start-cli (nth args 0))))
