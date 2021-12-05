(ns advent-2021.day5-test
  (:require [advent-2021.day5 :as sut]
            [advent-2021.utils.input :as i]
            [clojure.test :refer :all]))

(deftest test-parse-input
  (let [input-rows (i/lines "day5-sample")]
    (testing "Coordinates parsed from input"
      (is (= [[{:x 0 :y 9} {:x 5 :y 9}]
              [{:x 8 :y 0} {:x 0 :y 8}]]
             (take 2 (sut/input-rows->lines input-rows)))))))

(deftest test-filter-remove-diagonals
  (let [lines [[{:x 0 :y 9} {:x 5 :y 9}]
               [{:x 8 :y 0} {:x 0 :y 8}]
               [{:x 1 :y 2} {:x 1 :y 3}]]]
    (testing "Lines are filtered to contain only horizontal and vertical lines"
      (is (= [[{:x 0 :y 9} {:x 5 :y 9}]
              [{:x 1 :y 2} {:x 1 :y 3}]]
             (sut/filter-remove-diagonals lines))))))

(deftest test-points-crossed
  (testing "Gives a list of all points crossed by the line"
    (is (= [{:x 0 :y 9} {:x 1 :y 9} {:x 2 :y 9} {:x 3 :y 9}]
           (sut/points-crossed [{:x 0 :y 9} {:x 3 :y 9}])))
    (is (= [{:x 7 :y 0} {:x 7 :y 1} {:x 7 :y 2} {:x 7 :y 3} {:x 7 :y 4}]
           (sut/points-crossed [{:x 7 :y 0} {:x 7 :y 4}])))))

(deftest test-count-points-crossed
  (let [lines (-> (i/lines "day5-sample")
                  sut/input-rows->lines
                  sut/filter-remove-diagonals)]
    (testing "Counts points crossed by more than one line for sample input"
      (is (= 5 (sut/count-intersections lines))))))
