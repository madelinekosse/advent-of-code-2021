(ns advent-2021.2024.day1-test
  (:require  [clojure.test :refer :all]
             [advent-2021.2024.day1 :as sut]))

(deftest test-parse-rows
  (let [rows [[3 4] [4 3] [2 5] [1 3] [3 9] [3 3]]]
    (is (= [[3 4 2 1 3 3]
            [4 3 5 3 9 3]]
           (sut/parse-rows rows)))))

(deftest test-pair-up
  (is (= [[1 3] [2 3] [3 3] [3 4] [3 5] [4 9]]
         (sut/pair-up [[3 4 2 1 3 3]
                       [4 3 5 3 9 3]]))))

(deftest test-add-distances
  (is (= 11
         (sut/add-distances
          [[1 3] [2 3] [3 3] [3 4] [3 5] [4 9]]))))

(deftest test-part-1
  (is (= 11
         (sut/part-1 [[3 4 2 1 3 3]
                      [4 3 5 3 9 3]]))))

(deftest test-single-similarity-score
  (is (= 9 (sut/single-similarity-score [3 4 2 1 3 3] 3))))


(deftest test-similarity-score
  (is (= 31
         (sut/similarity-score [[3 4 2 1 3 3]
                                [4 3 5 3 9 3]]))))

