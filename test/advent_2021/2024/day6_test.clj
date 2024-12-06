(ns advent-2021.2024.day6-test
  (:require [advent-2021.2024.day6 :as sut]
            [clojure.test :refer :all]
            [advent-2021.utils.input :as input]))

(def sample-grid (input/parse-str-matrix "2024/sample-input/day6"))

(deftest test-parse-input-grid
  (testing "guard position"
    (is (= [6 4] (sut/position-of-guard sample-grid))))
  (testing "grid without guard"
    (let [output (sut/parse-input-grid sample-grid)]
      (is (=  "." (nth (nth (:grid output) 6) 4)))
      (is (= [6 4] (:guardpos output)))
      (is (= :up (:direction output))))))

(deftest test-part-1
  (testing "For sample input"
    (is (= 41
           (sut/part-1 sample-grid)))))

(deftest test-part-2
  (testing "For sample input"
    (is (= 6
           (sut/part-2 sample-grid)))))
