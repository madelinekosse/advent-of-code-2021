(ns advent-2021.day1-test
  (:require [advent-2021.day1 :as sut]
            [clojure.test :refer :all]))

(def input [199 200 208 210 200 207 240 269 260 263])

(deftest test-count-increases
  (testing "Counts increases for sample input"
    (is (= 7 (sut/count-increases input)))))

(deftest test-count-increases-sliding-window
  (testing "Counts increases by sliding window for sample input"
    (is (= 5
           (sut/count-increases-sliding-window input)))))
