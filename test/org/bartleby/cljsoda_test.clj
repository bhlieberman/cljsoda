(ns org.bartleby.cljsoda-test
  (:require [clojure.test :refer [deftest is testing run-test]]
            org.bartleby.cljsoda
            [org.bartleby.cljsoda.impl.http :refer [SODA-PASSWORD SODA-TOKEN]])
  (:import [org.bartleby.cljsoda SampleRequest]))

(deftest can-load-credentials-from-env
  (testing "that appropriate credentials are exposed in the environment."
    (is (and (some? SODA-PASSWORD)
             (some? SODA-TOKEN)))))

(deftest private-dataset
  (testing "that a private dataset cannot be accessed without proper credentials."
    (let [req (SampleRequest. "https://www.nmdataxchange.gov/resource/aws9-xwqm.json" nil nil nil)
          resp (org.bartleby.cljsoda/-get-dataset-columns req)] 
      (is (= 403 resp)))))

(run-test can-load-credentials-from-env)