(ns advent-2021.2024.day3-test
  (:require [advent-2021.2024.day3 :as sut]
            [clojure.test :refer :all]))


(deftest test-matches
  (testing "Valid matches"
    (is (= ["mul(222,444)"]
           (sut/matches "mul(222,444)"))
        "Simplest case")
    (is (= ["mul(1,2)" "mul(3,4)"]
           (sut/matches "mul(1,2)mul(3,4)"))
        "Multiple matches")
    (is (= ["mul(5,5)"]
           (sut/matches "7]!@^do_not_mul(5,5)+m"))
        "Stripping extra")
    (is (= ["mul(2,4)" "mul(5,5)" "mul(11,8)" "mul(8,5)"]
           (sut/matches "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"))
        "Sample data"))
  (testing "Invalid"
    (is (= [] (sut/matches "notavalidline")))
    (is (= [] (sut/matches "mul(1234,4321)"))
        "Too long")
    (is (= [] (sut/matches "mul(12,3])"))
        "Interrupted")
    (is (= [] (sut/matches "abcmul(123,45*"))
        "Unterminated"))
  (testing "With dos and donts"
    (is (= ["don't()" "mul(1,3)" "do()"]
           (sut/matches "xxx%%%don't()&*(fa)blamul(1,3)))mu(]do()d_"))
        "Simple test")
    (is (= ["mul(2,4)" "don't()" "mul(5,5)" "mul(11,8)" "do()" "mul(8,5)"]
           (sut/matches "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
        "Sample data")))

(deftest test-mutliply-match
  (testing "Parsing numbers from matches"
    (is (= 0 (sut/multiply-match "mul(0,1)"))
        "2 1-digit nums")
    (is (= 100 (sut/multiply-match "mul(10,10)"))
        "2 2-digit nums")))

(deftest test-part-1
  (testing "Simple case"
    (is (= 101 (sut/part-1 ["mul(1,1)mul(10,10)"]))))
  (testing "Two lines"
    (is (= 101 (sut/part-1 ["mul(1,1)mul(10,10)"
                            "notavalidline"]))
        "One invalid")
    (is (= 102 (sut/part-1 ["_mul(1,1))abcd(mul(1,1))"
                            "_xxxx_+*£mul(10,10)))[]"]))
        "Both valid"))
  (testing "Test input"
    (is (= 161
           (sut/part-1
            ["xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"])))))


(deftest test-part-2
  (testing "Part 1 examples without do are still valid"
    (is (= 101 (sut/part-2 ["mul(1,1)mul(10,10)"])))
    (is (= 101 (sut/part-2 ["mul(1,1)mul(10,10)"
                            "notavalidline"])))
    (is (= 102 (sut/part-2 ["_mul(1,1))abcd(mul(1,1))"
                            "_xxxx_+*£mul(10,10)))[]"])))
    (is
     (= 161
        (sut/part-2
         ["xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"])))
   )
  (testing "Disabled"
    (is (= 0 (sut/part-2 ["*+_don't()mul(1,2)"]))))
  (testing "Enabled"
    (is (= 2 (sut/part-2 ["*+_don't()blahdo()mul(1,2)"]))))
  (testing "Two lines"
    (is (= 101 (sut/part-2 ["_mul(1,1))don't()abcd(mul(1,1))do()"
                            "_xxxx_+*£mul(10,10)))[]"]))))
  (testing "Test input"
    (is (= 48
           (sut/part-2
            ["xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"])))))
