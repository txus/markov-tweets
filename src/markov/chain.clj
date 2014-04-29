(ns markov.chain)

(defn n-grams
  "Makes n-grams out of tokens"
  [n tokens]
  (set (partition n 1 tokens)))

(defn make-chain
  "Creates a Markov chain as a lazy sequence of tokens."
  [ngrams tokens]
  (when (seq tokens)
    (cons
      (first tokens)
      (lazy-seq
        (let [matching (filter (fn [ngram]
                                 (let [length (count tokens)
                                       prefix (take length ngram)
                                       ts (take length tokens)]
                                   (= prefix ts))) ngrams)]
          (if (seq matching)
            (let [random-ngram (nth matching (rand (count matching)))]
              (make-chain ngrams (drop 1 random-ngram)))
            (make-chain ngrams (rest tokens))))))))
