(ns org.bartleby.cljsoda.impl.http-test
  (:require [clojure.test :refer [deftest is testing run-tests]]
            [org.bartleby.cljsoda.impl.http :refer [make-async-http-request
                                                    make-sync-http-request
                                                    SODA-PASSWORD
                                                    SODA-TOKEN]]))

(deftest can-request-data-from-private-dataset
  (testing "that credentials when supplied allow access to private dataset."
    (let [url "https://www.nmdataxchange.gov/resource/aws9-xwqm.json"
          request-opts {:user "blieberman@da.state.nm.us"
                        :pass SODA-PASSWORD
                        :app-token SODA-TOKEN}
          {:keys [status body] :as sync-resp}
          (make-sync-http-request url request-opts)
          {async-status :status} 
          (make-async-http-request url (merge request-opts {:async? true}))] 
      (is (= status 200))
      (is (= async-status 200)))))

(run-tests)