(ns markov.tokenizer-test
  (:require [clojure.test :refer :all]
            [markov.tokenizer :refer :all]))

(deftest tokenize-test
  (testing "Keeps contextual mentions as tokens"
    (let [tweet (tokenize "This is a tweet for @user")
          tokens (tweet :tokens)]
      (is (= (last tokens) "@user"))))

  (testing "Stems punctuation and groups it"
    (let [tweet (tokenize "What?? No. Maybe!!!! /cc")
          tokens (tweet :tokens)]
      (is (= tokens ["What" "??" "No" "." "Maybe" "!!!!" "/cc"]))))

  (testing "Removes and remembers replied users"
    (let [tweet (tokenize "@user @another_user that is true")
          tokens (set (tweet :tokens))
          reply-to (tweet :reply-to)]
      (is (= #{"that" "is" "true"} tokens))
      (is (= #{"@user" "@another_user"} reply-to)))))

(deftest mentions-test
  (testing "Finds all mentioned users in a text"
    (let [ms (mentions "@user hey @another_user what up @other")]
      (is (= #{"@user" "@another_user" "@other"} ms)))))
