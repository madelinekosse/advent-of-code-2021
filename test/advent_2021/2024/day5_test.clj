(ns advent-2021.2024.day5-test
  (:require [advent-2021.2024.day5 :as sut]
            [clojure.test :refer :all]))

(deftest test-parse-input-lines
  (testing "Convert input lines to rules and updates"
    (is (= {:rules [[47 43] [53 29] [1 2]]
            :updates [[1 21 313] [23 45 67 3 2] [432 4 72]]}
           (sut/parse-input-lines ["47|43" "53|29" "1|2" "" "1,21,313" "23,45,67,3,2" "432,4,72"])))))

(deftest test-update-passes-rule
  (testing "Updates that pass"
    (is (sut/update-passes-rule? [1 2 3 4] 7 8)
        "Neither rule page in update")
    (is (sut/update-passes-rule? [1 2 3 4] 3 9)
        "Update contains only second rule page")
    (is (sut/update-passes-rule? [1 2 3 4] 9 3)
        "Update contains only first rule page")
    (is (sut/update-passes-rule? [2 3] 2 3)
        "Simple case")
    (is (sut/update-passes-rule? [1 2 3 3 4] 2 3)
        "With other elemnets in the way"))
  (testing "Updates that fail"
    (is (false? (sut/update-passes-rule? [1 2 3 4] 3 2))
        "Values out of order")))

(deftest test-update-ok
  (testing "No rules apply"
    (is (sut/update-ok? [[1 2] [2 3] [1 3]]
                        [101 102 103])))
  (testing "All rules satisfied"
    (is (sut/update-ok? [[75 47] [75 61] [75 53] [75 29]]
                        [75 47 61 53 29])
        "Simple case - no overlapping rules")
    (is (sut/update-ok? [[75 47] [75 61] [75 53] [75 29] [47 61] [47 29] [61 29]]
                        [75 47 61 53 29])
        "Multiple rules over same numbers")
    (is (sut/update-ok? [[1 2] [1 3] [1 4] [2 3] [2 4] [3 4]]
                        [1 2 3 4])
        "Rules govern entire order of update"))
  (testing "Not satisfied"
    (is (false? (sut/update-ok? [[1 2]]
                                [4 2 1 3]))
        "One rule and it's not satisfied")
    (is (false? (sut/update-ok? [[4 2] [2 1] [1 3] [3 4]]
                                [4 2 1 3]))
        "Some rules are satisfied but one s not")
    (is (false? (sut/update-ok? [[3 4] [1 2] [2 4]]
                                [4 2 1 3]))
        "All rules failing")))

(deftest test-sum-middle-of-good-updates
  (testing "Updates filtered by rules, sum of middle element"
    (let [rules [[1 2] [21 22] [31 32] [2 22]]]
      (is (= 125
             (sut/sum-middle-of-good-updates
              rules
              [[1 2 3] [21 22 23] [100 101 102]]))
          "All valid")
      (is (= 123
             (sut/sum-middle-of-good-updates
              rules
              [[2 1 3] [21 22 23] [100 101 102]]))
          "One invalid")
      (is (= 125
             (sut/sum-middle-of-good-updates
              rules
              [[77 1 2 3 79] [55 56 21 22 23 61 62] [100 101 102]]))
          "All valid, different lengths"))))

(deftest test-sort-by-rules
  (testing "Updates sorted accordign to rules"
    (is (= [97 75 47 61 53]
           (sut/sort-by-rules [[97 75]] [75 97 47 61 53]))
        "One rule failed")
    (is (= [61 29 13]
           (sut/sort-by-rules [[29 13]] [61 13 29])))
    (is (= [97 75 47 29 13]
           (sut/sort-by-rules  [[29 13] [47 13] [47 29] [75 13]] [97 13 75 29 47]))
        "Multiple rules failed")))

(deftest test-sum-middle-of-sorted-bad-updates
  (testing "For sample input"
    (is (= 123
           (sut/sum-middle-of-sorted-bad-updates
            [[47 53] [97 13] [97 61] [97 47] [75 29] [61 13] [75 53] [29 13] [97 29] [53 29] [61 53] [97 53] [61 29] [47 13] [75 47] [97 75] [47 61] [75 61] [47 29] [75 13] [53 13]]
            [[75 47 61 53 29] [97 61 53 29 13] [75 29 13] [75 97 47 61 53] [61 13 29] [97 13 75 29 47]])))))
