(ns org.bartleby.cljsoda
  (:require [hato.client :as hc]))

(defprotocol SodaRequest
            (-get-dataset-columns [this]))

(defrecord SampleRequest [endpoint app-token user pass]
  SodaRequest
  (-get-dataset-columns [_]
    (let [resp (try (hc/get (str endpoint "?$limit=0"))
                    (catch clojure.lang.ExceptionInfo e
                      (let [req-status (:status e)]
                        (condp = req-status
                          403 (do (println "This is a private dataset. You must be logged in to view it.")
                                  (if (and app-token user pass)
                                    (hc/get (str endpoint "?$limit=0")
                                            {:basic-auth {:user user
                                                          :pass pass}
                                             :headers {"X-App-Token" app-token}})
                                    (println "You need to supply your credentials.")))
                          404 (println "Resource not found.")
                          :else (println "Something went wrong.")))))]
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
