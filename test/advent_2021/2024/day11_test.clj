(ns advent-2021.2024.day11-test
  (:require [advent-2021.2024.day11 :as sut]
            [clojure.test :refer :all]))

(def sample-stones ["125" "17"])

(deftest test-blink-at
  (testing "0 stone"
    (is (= ["1"]
           (sut/blink-at "0"))))
  (testing "Even number split"
    (is (= ["9" "9"]
           (sut/blink-at "99"))
        "2 digits")
    (is (= ["10" "0"]
           (sut/blink-at "1000"))
        "With trailing zeroes"))
  (testing "Uneven digits"
    (is (= ["2024"]
           (sut/blink-at "1"))
        "One")
    (is (= ["2021976"]
           (sut/blink-at "999"))
        "big num")))

(deftest test-blink-and-count-one-stone
  (testing "125"
    (is (= 1 (sut/blink-and-count-one-stone "125" 1)) "One time")
    (is (= 2 (sut/blink-and-count-one-stone "125" 2)) "2 times")
    (is (= 2 (sut/blink-and-count-one-stone "125" 3)) "3 times")
    (is (= 3 (sut/blink-and-count-one-stone "125" 4)) "4 times")))

(deftest test-blink-n-times-and-count
  (testing "6 blinks"
    (is (= 22 (sut/blink-n-times-and-count sample-stones 6)))))

(deftest test-part-1
  (testing "ssample input"
    (is (= 55312
           (sut/part-1 sample-stones)))))
