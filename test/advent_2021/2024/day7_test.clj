(ns advent-2021.2024.day7-test
  (:require [advent-2021.2024.day7 :as sut]
            [clojure.test :refer :all]))

(deftest test-parse-lines
  (testing "Extract test value and values"
    (is (= [{:test-value 1
             :numbers [2 3 4]}
            {:test-value 2345
             :numbers [56 44]}]
           (sut/parse-lines ["1: 2 3 4 "
                             "2345: 56 44"]))))
  (testing "For very big numbers"
    (is (= [{:test-value 37603097701038
             :numbers [475 8 68 9 9 5 878 38 3]}]
           (sut/parse-lines
            ["37603097701038: 475 8 68 9 9 5 878 38 3"])))))

(deftest test-operation-permutations
  (testing "Without ||"
    (testing "With 2 elements"
      (is (= #{[:plus :plus]
               [:plus :times]
               [:times :plus]
               [:times :times]}
             (sut/operation-permutations 2 false))))
    (testing "With 3 elements"
      (is (= #{[:plus :plus :plus]
               [:plus :plus :times]
               [:plus :times :plus]
               [:plus :times :times]
               [:times :plus :plus]
               [:times :plus :times]
               [:times :times :plus]
               [:times :times :times]}
             (sut/operation-permutations 3 false)))))
  (testing "With ||"
    (testing "With 2 elements"
      (is (= #{[:plus :plus]
               [:plus :times]
               [:plus :concat]
               [:times :plus]
               [:times :times]
               [:times :concat]
               [:concat :plus]
               [:concat :times]
               [:concat :concat]}
             (sut/operation-permutations 2 true))))))

(def two-op-permutations-up-to-4-inputs
  {2 #{[:plus] [:times]}
   3 #{[:plus :plus]
       [:plus :times]
       [:times :plus]
       [:times :times]}
   4 #{[:plus :plus :plus]
       [:plus :plus :times]
       [:plus :times :plus]
       [:plus :times :times]
       [:times :plus :plus]
       [:times :plus :times]
       [:times :times :plus]
       [:times :times :times]}})

(def three-op-permutations-up-to-4-inputs
  {2 #{[:plus] [:times] [:concat]}
   3 #{[:plus :plus]
       [:plus :times]
       [:plus :concat]
       [:times :plus]
       [:times :times]
       [:times :concat]
       [:concat :plus]
       [:concat :times]
       [:concat :concat]}
   4 #{[:plus :plus :plus]
       [:plus :plus :times]
       [:plus :plus :concat]
       [:plus :times :plus]
       [:plus :times :times]
       [:plus :times :concat]
       [:plus :concat :plus]
       [:plus :concat :times]
       [:plus :concat :concat]
       [:times :plus :plus]
       [:times :plus :times]
       [:times :plus :concat]
       [:times :times :plus]
       [:times :times :times]
       [:times :times :concat]
       [:times :concat :plus]
       [:times :concat :times]
       [:times :concat :concat]
       [:concat :plus :plus]
       [:concat :plus :times]
       [:concat :plus :concat]
       [:concat :times :plus]
       [:concat :times :times]
       [:concat :times :concat]
       [:concat :concat :plus]
       [:concat :concat :times]
       [:concat :concat :concat]}})

(deftest test-op-permutations-by-input-count
  (testing "All op permutations for input counts up to max length"
    (is (= two-op-permutations-up-to-4-inputs
           (sut/op-permutations-by-input-count false 4))
        "Two operations only")
    (is (= three-op-permutations-up-to-4-inputs
           (sut/op-permutations-by-input-count true 4))
        "With concat op")))

(deftest test-ops-satisfy-expression
  (testing "With only 2 operations"
    (testing "2 elements, satisfied"
      (is (true? (sut/ops-satisfy-expression?
                  [:times]
                  {:test-value 6
                   :numbers [2 3]})))
      (is (true? (sut/ops-satisfy-expression?
                  [:plus]
                  {:test-value 5
                   :numbers [2 3]}))))
    (testing "2 elements, not satisfied"
      (is (false? (sut/ops-satisfy-expression?
                   [:plus]
                   {:test-value 5
                    :numbers [2 2]}))))
    (testing "4 elements"
      (is (true? (sut/ops-satisfy-expression?
                  [:plus :times :plus]
                  {:test-value 292
                   :numbers [11 6 16 20]}))
          "Satisfied")
      (is (false? (sut/ops-satisfy-expression?
                   [:times :plus :plus]
                   {:test-value 292
                    :numbers [11 6 16 20]}))
          "Unsatisfied by order of operations")))
  (testing "With concat"
    (testing "2 elements"
      (is (true? (sut/ops-satisfy-expression?
                  [:concat]
                  {:test-value 156
                   :numbers [15 6]}))
          "Satisfied")
      (is (false? (sut/ops-satisfy-expression?
                   [:concat]
                   {:test-value 615
                    :numbers [15 6]}))
          "Unatisfied"))
    (testing "Multiple elements and ops"
      (is (true? (sut/ops-satisfy-expression?
                  [:times :concat :times]
                  {:test-value 7290
                   :numbers [6 8 6 15]}))
          "Satisfied")
      (is (false? (sut/ops-satisfy-expression?
                   [:concat :times :times]
                   {:test-value 7290
                    :numbers [6 8 6 15]}))
          "Unsatisfied"))))

(deftest test-can-calibrate
  (testing "Two operators only"
    (testing "Can calibrate in one way only"
      (is (true? (sut/can-calibrate?
                  two-op-permutations-up-to-4-inputs
                  {:test-value 292
                   :numbers [11 6 16 20]}))))
    (testing "Can calibrate in multiple ways"
      (is (true? (sut/can-calibrate?
                  two-op-permutations-up-to-4-inputs
                  {:test-value 3267
                   :numbers [81 40 27]}))))
    (testing "Can't calibrate"
      (is (false? (sut/can-calibrate?
                   two-op-permutations-up-to-4-inputs
                   {:test-value 21037
                    :numbers [9 7 18 13]})))))
  (testing "With three operators"
    (testing "No concat required examples still work"
      (is (true? (sut/can-calibrate?
                  three-op-permutations-up-to-4-inputs
                  {:test-value 292
                   :numbers [11 6 16 20]})))
      (is (true? (sut/can-calibrate?
                  three-op-permutations-up-to-4-inputs
                  {:test-value 3267
                   :numbers [81 40 27]}))))
    (testing "concat required"
      (is (true? (sut/can-calibrate?
                  three-op-permutations-up-to-4-inputs
                  {:test-value 192
                   :numbers [17 8 14]})))
      (is (true? (sut/can-calibrate?
                  three-op-permutations-up-to-4-inputs
                  {:test-value 7290
                   :numbers [6 8 6 15]}))))))

(deftest test-sum-calibration-values
  (let [sample-input
        [{:test-value 190, :numbers [10 19]}
         {:test-value 3267, :numbers [81 40 27]}
         {:test-value 83, :numbers [17 5]}
         {:test-value 156, :numbers [15 6]}
         {:test-value 7290, :numbers [6 8 6 15]}
         {:test-value 161011, :numbers [16 10 13]}
         {:test-value 192, :numbers [17 8 14]}
         {:test-value 21037, :numbers [9 7 18 13]}
         {:test-value 292, :numbers [11 6 16 20]}]]
    (testing "Part 1 for sample input"
      (is (= 3749
             (sut/sum-calibration-values sample-input false))))
    (testing "Part 2 for sample input"
      (is (= 11387
             (sut/sum-calibration-values sample-input true))))))
