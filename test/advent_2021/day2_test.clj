(ns advent-2021.day2-test
  (:require [advent-2021.day2 :as sut]
            [clojure.test :refer :all]))

(def input [['forward 5]
            ['down 5]
            ['forward 8]
            ['up 3]
            ['down 8]
            ['forward 2]])

(deftest test-apply-directions
  (testing "Sample directions applied from sample input"
    (is (= {:x 15 :y -10}
           (sut/apply-directions {:x 0 :y 0} input)))))

(deftest test-move-and-multiply-location
  (testing "Directions are applied and new position multiplied"
    (is (= 150
           (sut/move-and-multiply-location input)))))
