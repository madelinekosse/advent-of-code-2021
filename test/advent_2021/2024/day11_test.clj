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

(deftest test-blink
  (testing "First blink"
    (is (= ["253000" "1" "7"]
           (sut/blink sample-stones)))))

(deftest test-n-blinks
  (testing "second blink"
    (is (= ["253" "0" "2024" "14168"]
           (sut/blink-n-times sample-stones 2))))
  (testing "6 blinks"
    (is (= ["2097446912" "14168" "4048" "2" "0" "2" "4" "40" "48" "2024" "40" "48" "80" "96" "2" "8" "6" "7" "6" "0" "3" "2"]
           (sut/blink-n-times sample-stones 6)))))

(deftest test-part-1
  (testing "ssample input"
    (is (= 55312
           (sut/part-1 sample-stones)))))
