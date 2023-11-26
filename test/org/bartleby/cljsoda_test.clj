(ns org.bartleby.cljsoda-test
  (:require [clojure.test :refer [deftest is testing run-test]]
            org.bartleby.cljsoda)
  (:import [org.bartleby.cljsoda SampleRequest]))

(deftest private-dataset
  (testing "that a private dataset cannot be accessed without proper credentials."
    (let [req (SampleRequest. "https://www.nmdataxchange.gov/resource/aws9-xwqm.json" nil nil nil)
          resp (org.bartleby.cljsoda/-get-dataset-columns req)] 
      (is (= 403 resp)))))

(run-test private-dataset)