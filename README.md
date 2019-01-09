# hangman-clojure

A game of hangman written in clojure.

## Getting the code

Clone the repo:

```bash
git clone https://github.com/AshleyByeUK/hangman-clojure && cd hangman-clojure
```

## Running

To run with Leiningen:

```bash
lein run
```

Or to specify a custom dictionary file (one word per line):

```bash
lein run -- /usr/share/dict/words
```

If the specified file does not exist or cannot be opened, hangman uses its own word list.

## Testing

To run the unit tests with Leiningen:

```bash
lein spec
```
