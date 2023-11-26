(ns org.bartleby.cljsoda.impl.http
  (:require [hato.client :as hc]
            [org.bartleby.cljsoda.impl.macros :refer [create-query]]))

(def SODA-TOKEN (System/getenv "SODA_TOKEN"))

(def SODA-PASSWORD (System/getenv "SODA_PASS"))

(defn make-sync-http-request
  ([endpoint]
   (make-sync-http-request endpoint {}))
  ([endpoint {:keys [user pass app-token] :as opts}]
   (hc/get endpoint {:basic-auth {:user user
                                  :pass pass}
                     :headers {"X-App-Token" app-token}})))

(defn make-async-http-request
  ([endpoint]
   (make-async-http-request endpoint {}))
  ([endpoint {:keys [user pass app-token]}]
   (hc/get endpoint {:basic-auth {:user user
                                  :pass pass}
                     :headers {"X-App-Token" app-token}
                     :async? true
                     :as :json}
           (fn [resp] (->> resp
                           :body
                           ;; it would be sweet if this is actually faster!
                           (into [] (create-query :* nil 10 20))
                           println
                           time))
           (fn [err] err))))

(comment
  (dotimes [_ 10]
    (make-async-http-request
     "https://www.nmdataxchange.gov/resource/aws9-xwqm.json"
     {:user "blieberman@da.state.nm.us"
      :pass SODA-PASSWORD
      :app-token SODA-TOKEN})))