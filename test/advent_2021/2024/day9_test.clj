(ns advent-2021.2024.day9-test
  (:require [advent-2021.2024.day9 :as sut]
            [clojure.test :refer :all]))

(deftest test-parse-disc-map
  (testing "Simple example"
    (is (= [0 nil nil 1 1 1 nil nil nil nil 2 2 2 2 2]
           (sut/parse-disc-map "12345"))))
  (testing "Sample input"
    (is (= [0 0
            nil nil nil
            1 1 1
            nil nil nil
            2
            nil nil nil
            3 3 3
            nil
            4 4
            nil
            5 5 5 5
            nil
            6 6 6 6
            nil
            7 7 7
            nil
            8 8 8 8 9 9]
           (sut/parse-disc-map "2333133121414131402")))))


(deftest test-compact-filesystem
  (testing "Simple example"
    (is (= [0 2 2 1 1 1 2 2 2 nil nil nil nil nil nil]
           (sut/compact-filesystem [0 nil nil 1 1 1 nil nil nil nil 2 2 2 2 2]))))
  (testing "Sample input"
    (is (= [0 0 9 9 8 1 1 1 8 8 8 2 7 7 7 3 3 3 6 4 4 6 5 5 5 5 6 6
            nil nil nil nil nil nil nil nil nil nil nil nil nil nil ]
           (sut/compact-filesystem [0 0
                                    nil nil nil
                                    1 1 1
                                    nil nil nil
                                    2
                                    nil nil nil
                                    3 3 3
                                    nil
                                    4 4
                                    nil
                                    5 5 5 5
                                    nil
                                    6 6 6 6
                                    nil
                                    7 7 7
                                    nil
                                    8 8 8 8 9 9])))))

(deftest test-checksum
  (testing "Simplest example"
    (is (= 14
           (sut/checksum [0 1 2 3 nil nil nil nil nil nil ] ))))
  (testing "Sample inputl"
    (is (= 1928
           (sut/checksum [0 0 9 9 8 1 1 1 8 8 8 2 7 7 7 3 3 3 6 4 4 6 5 5 5 5 6 6
                          nil nil nil nil nil nil nil nil nil nil nil nil nil nil ])))))

(deftest test-part-1
  (testing "Part 1 for sample input"
    (is (= 1928
           (sut/part-1 "2333133121414131402")))))

