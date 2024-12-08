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

(deftest test-gradient
  (testing "gradient simplificantion"
    (is (= [0 2]
           (sut/gradient [0 2])))

    (is (= [7 4]
           (sut/gradient [7 4])))
    (is (= [2 1]
           (sut/gradient [4 2])))
    (is (= [1 1]
           (sut/gradient [3 3])))
    (is (= [1 3]
           (sut/gradient [3 9])))
    (is (= [-1 3]
           (sut/gradient [-3 9])))))

(deftest test-calculate-antinode-locations-part-2
  (testing "Horizontal"
    (is (= [[3 1] [3 3] [3 5] [3 7] [3 9]]
           (sort (sut/calculate-antinode-locations-part-2
            10 10 [[3 5] [3 7]])))))
  (testing "Vertical"
    (is (= [[3 0] [3 3] [3 6] [3 9]]
           (sort (sut/calculate-antinode-locations-part-2 10 10 [[3 3] [3 6]])))))
  (testing "Diagonal to the right"
    (is (= [[0 0] [1 1] [2 2] [3 3] [4 4] [5 5] [6 6] [7 7] [8 8] [9 9]]
           (sort
            (sut/calculate-antinode-locations-part-2 10 10 [[3 3] [6 6]])))))
  (testing "Diagonal to the left"
    (is (= [[0 4] [3 3] [6 2] [9 1]]
           (sort
            (sut/calculate-antinode-locations-part-2 10 10 [[3 3] [6 2]]))))
    (is (= [[1 8] [4 4] [7 0]]
           (sort
            (sut/calculate-antinode-locations-part-2 11 11 [[1 8] [4 4]]))))))

(deftest test-part-2
  (testing "Part 2 for sample data"
    (is (= 34
           (sut/part-2 test-data)))))




