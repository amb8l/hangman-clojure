(ns hangman.hangman-spec
  (:require [speclj.core :refer :all]
            [hangman.hangman :refer :all]))

(describe "a game of hangman"
          (it "has a word, 8 guesses remaining, no guessed letters and no incorrect guesses"
              (should= {
                        :letters           ["r", "a", "n", "d", "o", "m"]
                        :used-letters      []
                        :guessed-letters   ["_", "_", "_", "_", "_", "_"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :playing
                        }
                       (new-game "random")))

          (it "can be initialised with any word"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      []
                        :guessed-letters   ["_", "_", "_", "_"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :playing
                        }
                       (new-game "word")))

          (it "does not play a turn when the game is over"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      []
                        :guessed-letters   ["_", "_", "_", "_"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :game-over
                        }
                       (play-turn {
                                   :letters           ["w", "o", "r", "d"]
                                   :used-letters      []
                                   :guessed-letters   ["_", "_", "_", "_"]
                                   :incorrect-letters []
                                   :guesses-remaining 8
                                   :state             :game-over
                                   } "a")))

          (it "does not use a guess when an already used incorrect letter is used again"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      ["a"]
                        :guessed-letters   ["_", "_", "_", "_"]
                        :incorrect-letters ["a"]
                        :guesses-remaining 7
                        :state             :playing
                        }
                       (play-turn {
                                   :letters           ["w", "o", "r", "d"]
                                   :used-letters      ["a"]
                                   :guessed-letters   ["_", "_", "_", "_"]
                                   :incorrect-letters ["a"]
                                   :guesses-remaining 7
                                   :state             :playing
                                   } "a")))

          (it "does not use a guess when a correct letter is guessed"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      ["w"]
                        :guessed-letters   ["w", "_", "_", "_"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :playing
                        }
                       (play-turn {
                                   :letters           ["w", "o", "r", "d"]
                                   :used-letters      []
                                   :guessed-letters   ["_", "_", "_", "_"]
                                   :incorrect-letters []
                                   :guesses-remaining 8
                                   :state             :playing
                                   } "w")))

          (it "recognises all occurrences of a letter for a correct guess"
              (should= {
                        :letters           ["w", "a", "l", "l", "y"]
                        :used-letters      ["l"]
                        :guessed-letters   ["_", "_", "l", "l", "_"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :playing
                        }
                       (play-turn {
                                   :letters           ["w", "a", "l", "l", "y"]
                                   :used-letters      []
                                   :guessed-letters   ["_", "_", "_", "_", "_"]
                                   :incorrect-letters []
                                   :guesses-remaining 8
                                   :state             :playing
                                   } "l")))

          (it "ignores the case of letters"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      ["d"]
                        :guessed-letters   ["_", "_", "_", "d"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :playing
                        }
                       (play-turn {
                                   :letters           ["w", "o", "r", "d"]
                                   :used-letters      []
                                   :guessed-letters   ["_", "_", "_", "_"]
                                   :incorrect-letters []
                                   :guesses-remaining 8
                                   :state             :playing
                                   } "D")))

          (it "is game over when the word has been correctly guessed"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      ["d", "o", "r", "w"]
                        :guessed-letters   ["w", "o", "r", "d"]
                        :incorrect-letters []
                        :guesses-remaining 8
                        :state             :game-over
                        }
                       (play-turn {
                                   :letters           ["w", "o", "r", "d"]
                                   :used-letters      ["o", "r", "w"]
                                   :guessed-letters   ["w", "o", "r", "_"]
                                   :incorrect-letters []
                                   :guesses-remaining 8
                                   :state             :playing
                                   } "d")))

          (it "is game over when the word has not been correctly guessed and no guesses remain"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      ["a", "c", "e", "f", "i", "p", "u", "z"]
                        :guessed-letters   ["_", "_", "_", "_"]
                        :incorrect-letters ["a", "c", "e", "f", "i", "p", "u", "z"]
                        :guesses-remaining 0
                        :state             :game-over
                        }
                       (play-turn {
                                   :letters           ["w", "o", "r", "d"]
                                   :used-letters      ["a", "c", "e", "i", "p", "u", "z"]
                                   :guessed-letters   ["_", "_", "_", "_"]
                                   :incorrect-letters ["a", "c", "e", "i", "p", "u", "z"]
                                   :guesses-remaining 1
                                   :state             :playing
                                   } "f")))

          (it "plays a game from start to end"
              (should= {
                        :letters           ["w", "o", "r", "d"]
                        :used-letters      ["a", "d", "o", "r", "w"]
                        :guessed-letters   ["w", "o", "r", "d"]
                        :incorrect-letters ["a"]
                        :guesses-remaining 7
                        :state             :game-over
                        }
                       (-> (new-game "word")
                           (play-turn "a")
                           (play-turn "w")
                           (play-turn "o")
                           (play-turn "r")
                           (play-turn "d")))))
