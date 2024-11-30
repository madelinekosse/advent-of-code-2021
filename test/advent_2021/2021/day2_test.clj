(ns advent-2021.2021.day2-test
  (:require [advent-2021.2021.day2 :as sut]
            [clojure.test :refer :all]))

(def input [['forward 5]
            ['down 5]
            ['forward 8]
            ['up 3]
            ['down 8]
            ['forward 2]])

(deftest test-part-1
  (testing "Sample directions applied from sample input"
    (is (= {:x 15 :y -10}
           (sut/apply-directions sut/directions-p1 {:x 0 :y 0} input))))
  (testing "Directions are applied and new position multiplied"
    (is (= 150
           (sut/move-and-multiply-location-p1 input)))))

(deftest test-part-2
  (testing "Sample directions applied using different update functions"
    (is (= {:x-pos 15 :depth 60 :aim 10}
           (sut/apply-directions sut/directions-p2 {:x-pos 0 :depth 0 :aim 0} input))))
  (testing "New position horizontal position and depth are multiplied"
    (is (= 900
           (sut/move-and-multiply-location-p2 input)))))
