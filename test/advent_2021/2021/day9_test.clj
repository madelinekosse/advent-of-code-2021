(ns advent-2021.2021.day9-test
  (:require [advent-2021.2021.day9 :as sut]
            [clojure.test :refer :all]))

(def sample-input [[2 1 9 9 9 4 3 2 1 0]
                   [3 9 8 7 8 9 4 9 2 1]
                   [9 8 5 6 7 8 9 8 9 2]
                   [8 7 6 7 8 9 6 7 8 9]
                   [9 8 9 9 9 6 5 6 7 8]])


(deftest test-get-low-points
  (testing "Returns row and column of each low point"
    (is (= [[0 1] [0 9] [2 2] [4 6]]
           (sut/low-points sample-input)))))

(deftest test-sum-risk-level
  (testing "Correct risk level for part 1"
    (is (= 15
           (sut/total-risk-level sample-input)))))

(deftest test-find-basins
  (testing "Finds correct size of all basins part 1"
    (is (= {[0 1] 3
            [0 9] 9
            [2 2] 14
            [4 6] 9}
           (sut/find-basins sample-input)))))

(deftest test-multiply-largest-basins
  (testing "Correct p2 solution for sample input"
    (is (= 1134
           (sut/find-and-multiply-largest-basins sample-input)))))
