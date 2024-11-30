(ns advent-2021.2021.day7-test
  (:require [advent-2021.2021.day7 :as sut]
            [clojure.test :refer :all]))

(def sample-input [16 1 2 0 4 2 7 1 2 14])

(deftest test-p1
  (testing "Correct fuel and position for sample input using linear fuel calculation"
    (is (= {:position 2 :fuel 37}
           (sut/align-submarines-p1 sample-input)))))

(deftest test-p2
  (testing "Correct fuel and position for sample input with increasing fuel consumption"
    (is (= {:position 5 :fuel 168}
           (sut/align-submarines-p2 sample-input)))))
