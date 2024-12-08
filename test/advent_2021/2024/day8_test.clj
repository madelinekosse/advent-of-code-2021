(ns advent-2021.2024.day8-test
  (:require  [clojure.test :refer :all]
             [advent-2021.2024.day8 :as sut]))

(def test-data [["." "." "." "." "." "." "." "." "." "." "." "."]
                ["." "." "." "." "." "." "." "." "0" "." "." "."]
                ["." "." "." "." "." "0" "." "." "." "." "." "."]
                ["." "." "." "." "." "." "." "0" "." "." "." "."]
                ["." "." "." "." "0" "." "." "." "." "." "." "."]
                ["." "." "." "." "." "." "A" "." "." "." "." "."]
                ["." "." "." "." "." "." "." "." "." "." "." "."]
                ["." "." "." "." "." "." "." "." "." "." "." "."]
                ["." "." "." "." "." "." "." "." "A" "." "." "."]
                ["." "." "." "." "." "." "." "." "." "A" "." "."]
                ["." "." "." "." "." "." "." "." "." "." "." "."]
                ["." "." "." "." "." "." "." "." "." "." "." "."]])

(deftest test-antenna-loacations
  (testing "Get all locations"
    (is (= {"A" [[5 6] [8 8] [9 9]]
            "0" [[1 8] [2 5] [3 7] [4 4]]}
           (sut/antenna-locations test-data)))))

(deftest test-calculate-antinode-locations
  (testing "For test data"
    (is (= [[-1 9] [5 6]]
           (sut/calculate-antinode-locations [[3 7] [1 8]]))
        "Diagonal top right to bottom left")
    (is (= [[2 4] [11 10]]
           (sut/calculate-antinode-locations [[5 6] [8 8]]))
        "Diagonal top left to bottom right")))

(deftest test-part-1
  (testing "Part 1 for sample data"
    (is (= 14
           (sut/part-1 test-data)))))
