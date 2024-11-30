(ns advent-2021.2021.day6-test
  (:require [advent-2021.2021.day6 :as sut]
            [clojure.test :refer :all]))

(def sample-input [3 4 3 1 2])

(deftest test-lanternfish-day
  (testing "After one day, lanternfish have decreased days to reproduction"
    (is (= {0 1 1 1 2 2 3 1}
           (sut/lanternfish-day (frequencies sample-input)))))
  (testing "After two days, the lanternfish with 0 days reproduces"
    (is (= {0 1 1 2 2 1 6 1 8 1}
           (sut/lanternfish-day (sut/lanternfish-day (frequencies sample-input)))))))

(deftest test-lanternfish-days
  (testing "After two days, the lanternfish with 0 days reproduces"
    (is (= (frequencies [1 2 1 6 0 8])
           (sut/lanternfish-days 2 (frequencies sample-input)))))
  (testing "Correct state after 18 days"
    (is (= (frequencies [6 0 6 4 5 6 0 1 1 2 6 0 1 1 1 2 2 3 3 4 6 7 8 8 8 8])
           (sut/lanternfish-days 18 (frequencies sample-input))))))

(deftest test-count-lanternfish-after-n-days
  (testing "Simple case"
    (let [input [1 1 2]]
      (testing "After 1 day"
        (is (= 3 (sut/count-lanternfish-after-n-days 1 input))))
      (testing "After 2 days"
        (is (= 5 (sut/count-lanternfish-after-n-days 2 input))))))
  (testing "With sample input"
    (testing "After 80 days"
      (is (= 5934
             (sut/count-lanternfish-after-n-days 80 sample-input)))))
  (testing "After 256 days"
    (is (= 26984457539
           (sut/count-lanternfish-after-n-days 256 sample-input)))))
