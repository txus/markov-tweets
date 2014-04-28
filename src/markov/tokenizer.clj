(ns markov.tokenizer
  (:require [clojure.string :as str]))

(defn mentions
  "Finds all mentioned users in a text and returns them as a set."
  [source]
  (->> source
      (re-seq #"\@\w+")
      set))

(defn- extract-reply-to
  "Separates a reply tweet text from the users it is replying to. Returns an
  (a b) tuple where a is the tweet text without the replied to users and b is
  a set of replied to users."
  [source]
  (let [reply-to (->> (str/split source #" ")
                      (take-while #(re-find #"^@" %)))]
    (if (not (empty? reply-to))
      (let [to-strip (str/join " " reply-to)
            text (str/replace source to-strip "")]
        [text (set reply-to)])
      [source #{}])))

(defn- stem-punctuation
  "Takes a sequence of tokens which may contain punctuation and returns a
  flattened sequence of the same tokens plus tokens with that grouped
  punctuation if any."
  [tokens]
  (->> tokens
       (map #(->> %
                  (re-find #"([^\!\?\.]+)(.*)")
                  (drop 1)))
       flatten))

(defn tokenize
  "Tokenizes a single tweet source into a tweet."
  [source]
  (let [[text reply-to] (extract-reply-to source)
        tokens (-> text
                   (str/split #" ")
                   stem-punctuation)]
    {:tokens (remove empty? tokens) :reply-to reply-to}))
