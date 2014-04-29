(ns markov.chain-test
  (:require [clojure.test :refer :all]
            [markov.chain :refer :all]))

(deftest n-grams-test
  (testing "Makes n-grams of a series of tokens"
    (let [tokens ["Today" "is" "a" "good" "day"]
          trigrams (n-grams 3 tokens)]
      (is (= 3 (count trigrams)))
      (is (contains? trigrams ["Today" "is" "a"]))
      (is (contains? trigrams ["is" "a" "good"]))
      (is (contains? trigrams ["a" "good" "day"])))))

(deftest generator-test
  (testing "Makes a markov chain generator out of a set of ngrams"
    (let [ngrams #{["Today" "is" "a"] ["is" "a" "good"] ["a" "good" "day"]}
          chain (make-chain ngrams ["Today"])
          result (take 8 chain)]
      (is (= result ["Today" "is" "a" "good" "day"]))))

  (testing "Works with more tokens than n"
    (let [ngrams #{["Today" "is" "a"] ["is" "a" "good"] ["a" "good" "day"]}
          chain (make-chain ngrams ["Today" "is" "a" "good"])
          result (take 8 chain)]
      (is (= result ["Today" "is" "a" "good" "day"]))))

  (testing "Returns just the token if there is no matching ngram"
    (let [ngrams #{["Today" "is" "a"] ["is" "a" "good"] ["a" "good" "day"]}
          chain (make-chain ngrams ["Foo"])
          result (take 8 chain)]
      (is (= result ["Foo"])))))
