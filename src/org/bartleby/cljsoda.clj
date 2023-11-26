(ns org.bartleby.cljsoda
  (:require [clojure.pprint :as pp]
            [hato.client :as hc]))

(defprotocol SodaRequest
            (-get-dataset-columns [this]))

(defrecord SampleRequest [endpoint app-token user pass]
  SodaRequest
  (-get-dataset-columns [_]
    (let [resp (try (hc/get (str endpoint "?$limit=0"))
                    (catch clojure.lang.ExceptionInfo e
                      (let [req-status (-> e ex-data :status)] 
                        (condp = req-status
                          403 403
                          404 (println "Resource not found.")
                          (println "Something went wrong.")))))]
      resp)))

(defn create-query-string [m]
  (reduce (fn [acc [k v]]
            (if (and (= k :select)
                     (= v :*))
              (str acc "$select=*")
              acc)) "" m))

(comment
  (create-query-string {:select :*})
  (let [req (SampleRequest. "https://www.nmdataxchange.gov/resource/aws9-xwqm.json" nil nil nil)]
    (-get-dataset-columns req)))
