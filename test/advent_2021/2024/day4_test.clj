(ns advent-2021.2024.day4-test
  (:require [advent-2021.2024.day4 :as sut]
            [clojure.test :refer :all]
            [advent-2021.utils.input :as i]))


(deftest test-part-1
  (let [sample (i/parse-str-matrix "2024/sample-input/day4")]
    (testing "part 1 for sample"
      (is (= 18 (sut/part-1 sample))))))

(deftest test-x-mas-found?
  (testing "found x-mas"
    (is (true? (sut/x-mas-found? [["M" "." "S"]
                                  ["." "A" "."]
                                  ["M" "." "S"]])))))
(deftest test-3-by-3-blocks
  (testing "3 by 3"
    (is (= [[[1 2 3]
             [5 6 7]
             [9 10 11]]
            [[2 3 4]
             [6 7 8]
             [10 11 12]]
            [[5 6 7]
             [9 10 11]
             [13 14 15]]
            [[6 7 8]
             [ 10 11 12]
             [ 14 15 16]]]
           (sut/get-3-by-3-blocks [[1 2 3 4]
                                   [5 6 7 8]
                                   [9 10 11 12]
                                   [13 14 15 16]])))))

(deftest test-part-2
  (let [sample (i/parse-str-matrix "2024/sample-input/day4")]
    (testing "part 2 for sample"
      (is (= 9 (sut/part-2 sample))))))

