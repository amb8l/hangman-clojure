(ns hangman.cli-spec
  (:require [speclj.core :refer :all]
            [hangman.cli :refer :all]))

(describe "getting user input"
          (it "numbers are invalid"
              (should= false (valid-guess "1")))

          (it "symbols are invalid"
              (should= false (valid-guess "@")))

          (it "spaces are invalid"
              (should= false (valid-guess " ")))

          (it "newlines are invalid"
              (should= false (valid-guess "\n")))

          (it "tabs are invalid"
              (should= false (valid-guess "\t")))

          (it "words are invalid"
              (should= false (valid-guess "word")))

          (it "uppercase letters are valid"
              (should= true (valid-guess "A")))

          (it "lowercase letters are valid"
              (should= true (valid-guess "a"))))
