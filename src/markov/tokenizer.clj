(ns markov.tokenizer
  (:require [clojure.string :as str]))

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

(defn- extract-cc
  "Separates a tweet text from its /cc'd users. Returns an (a b) tuple where a
  is the tweet text without the cc'd users and b is a set of its cc'd users."
  [source]
  (let [[to-strip tail] (re-find #"[ \/]cc(.*)$" source)]
    (if to-strip
      (let [text (-> source
                     (str/replace to-strip "")
                     str/trim)
            cc (-> tail
                   str/trim
                   (str/split #" ")
                   set)]
        [text cc])
      [source #{}])))

(defn tokenize
  "Tokenizes a single tweet source into a tweet."
  [source]
  (let [[text cc] (extract-cc source)
        [text reply-to] (extract-reply-to text)
        tokens (-> text
                   (str/split #" "))]
    {:tokens tokens :cc cc, :reply-to reply-to}))
