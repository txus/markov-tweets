(ns markov.tokenizer-test
  (:require [clojure.test :refer :all]
            [markov.tokenizer :refer :all]))

(deftest tokenize-test
  (testing "Keeps contextual mentions as tokens"
    (let [tweet (tokenize "This is a tweet for @user")
          tokens (tweet :tokens)]
      (is (= (last tokens) "@user"))))

  (testing "Removes and remembers /cc'd users"
    (let [tweet (tokenize "This is a tweet. /cc @user")
          tokens (set (tweet :tokens))
          cc (tweet :cc)]
      (is (not (contains? tokens "@user")))
      (is (contains? cc "@user"))))

  (testing "Removes and remembers replied users"
    (let [tweet (tokenize "@user @another_user that is true")
          tokens (set (tweet :tokens))
          reply-to (tweet :reply-to)]
      (is (not (contains? tokens "@user")))
      (is (not (contains? tokens "@another_user")))
      (is (contains? reply-to "@user"))
      (is (contains? reply-to "@another_user")))))
