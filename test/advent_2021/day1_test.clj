(ns advent-2021.day1-test
  (:require [advent-2021.day1 :as sut]
            [clojure.test :refer :all]))

(def input [199 200 208 210 200 207 240 269 260 263])

(deftest test-count-increases
  (testing "Counts increases for sample input"
    (is (= 7 (sut/count-increases input)))))

(deftest test-sliding-window-groups
  (testing "Splits vec into groups of 3 starting at each index"
    (is (= [[1 2 3] [2 3 4] [3 4 5] [4 5 6] [5 6 7]]
           (sut/sliding-window-groups [1 2 3 4 5 6 7])))))

(deftest test-sum-sliding-window
  (testing "Sums each group of 3"
    (is (= [607 618 618 617 647 716 769 792]
           (sut/sum-sliding-window input)))))

(deftest test-count-increases-sliding-window
  (testing "Counts increases by sliding window for sample input"
    (is (= 5
           (sut/count-increases-sliding-window input)))))
