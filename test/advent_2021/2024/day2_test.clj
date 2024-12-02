(ns advent-2021.2024.day2-test
  (:require [advent-2021.2024.day2 :as sut]
            [clojure.test :refer :all]))


(deftest test-all-increasing-or-decreasing
  (is (sut/all-increasing-or-decreasing? [1 2 3 4 5]))
  (is (sut/all-increasing-or-decreasing? [5 4 3 2 1]))
  (is (false? (sut/all-increasing-or-decreasing? [4 5 3 2 1]))))

(deftest test-levels-differ-by-1-to-3
  (is (sut/levels-differ-by-1-to-3 [7 6 4 2 1]))
  (is (sut/levels-differ-by-1-to-3 [1 2 5 3 6]))
  (is (false? (sut/levels-differ-by-1-to-3 [1 2 5 3 7])))
  (is (false? (sut/levels-differ-by-1-to-3 [1 2 2 3 6]))))

(deftest test-safe
  (is (sut/safe? [7 6 4 2 1]))
  (is (false? (sut/safe? [1 2 7 8 9])))
  (is (false? (sut/safe? [9 7 6 2 1])))
  (is (false? (sut/safe? [1 3 2 4 5])))
  (is (false? (sut/safe? [8 6 4 4 1])))
  (is (sut/safe? [1 3 6 7 9])))


(deftest test-safe-with-dampener
  (is (sut/safe-with-dampener? [7 6 4 2 1]))
  (is (false? (sut/safe-with-dampener? [1 2 7 8 9])))
  (is (false? (sut/safe-with-dampener? [9 7 6 2 1])))
  (is (sut/safe-with-dampener? [1 3 2 4 5]))
  (is (sut/safe-with-dampener? [8 6 4 4 1]))
  (is (sut/safe-with-dampener? [1 3 6 7 9])))
