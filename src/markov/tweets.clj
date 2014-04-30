(ns markov.tweets
  (:use environ.core)
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as twitter]
            [markov.tokenizer :as t]
            [markov.chain :as c]))

(def ^:private twitter-credentials (oauth/make-oauth-creds (env :app-consumer-key)
                                                           (env :app-consumer-secret)
                                                           (env :user-access-token)
                                                           (env :user-access-token-secret)))

(defn last-statuses [user n]
  (->> (twitter/statuses-user-timeline :oauth-creds twitter-credentials
                                       :params { :screen_name user :count n })
       :body
       (map :text)
       (remove #(re-seq #"^RT" %))
       t/tokenize))

(defn tweet [from-user to-user context]
  (let [statuses (last-statuses from-user context)]
    nil ; do something
    ))
