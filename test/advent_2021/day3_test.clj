(ns advent-2021.day3-test
  (:require [advent-2021.day3 :as sut]
            [clojure.test :refer :all]))

(deftest test-split-digits
  (testing "Splits binary number into digits"
    (is (= [0 0 1 0 1]
           (sut/split-digits "00101")))))

(def sample-input ["00100"
                   "11110"
                   "10110"
                   "10111"
                   "10101"
                   "01111"
                   "00111"
                   "11100"
                   "10000"
                   "11001"
                   "00010"
                   "01010"])

(deftest test-gamma-digits
  (testing "Most common digits for each position are found"
    (is (= [1 0 1 1 0]
           (sut/gamma-digits sample-input)))))

(deftest test-epsilon
  (testing "Epsilon digits are just flipped gamma digits"
    (is (= [1 0 1 0 1]
           (sut/epsilon [0 1 0 1 0])))))

(deftest test-power-consumption
  (testing "Power consumption is product of gamma and epsilon"
    (is (= 198
           (sut/power-consumption sample-input)))))
