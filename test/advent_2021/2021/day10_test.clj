(ns advent-2021.2021.day10-test
  (:require [advent-2021.2021.day10 :as sut]
            [clojure.test :refer :all]))

(def sample-input ["[({(<(())[]>[[{[]{<()<>>"
                   "[(()[<>])]({[<{<<[]>>("
                   "{([(<{}[<>[]}>{[]{[(<()>"
                   "(((({<>}<{<{<>}{[]{[]{}"
                   "[[<[([]))<([[{}[[()]]]"
                   "[{[{({}]{}}([{[{{{}}([]"
                   "{<[[]]>}<{[{[{[]{()[[[]"
                   "[<(<(<(<{}))><([]([]()"
                   "<{([([[(<>()){}]>(<<{{"
                   "<{([{{}}[<[[[<>{}]]]>[]]"])

(deftest test-read-syntax-error
  (testing "When line is corrupted, return the first illegal character"
    (is (= {:type :curly :mode :close}
           (:unexpected (sut/read-syntax-error "{([(<{}[<>[]}>{[]{[(<()>")))))
  (testing "When line is incomplete"
    (let [{:keys [unexpected stack]} (sut/read-syntax-error "[({(<(())[]>[[{[]{<()<>>")]
      (testing "no syntax error"
        (is (nil? unexpected)))
      (testing "returns stack of unmatched brackets"
        (is (= (list {:type :curly :mode :open}
                     {:type :curly :mode :open}
                     {:type :square :mode :open}
                     {:type :square :mode :open}
                     {:type :paren :mode :open}
                     {:type :curly :mode :open}
                     {:type :paren :mode :open}
                     {:type :square :mode :open})
               stack))))))

(deftest test-score-syntax-errors
  (testing "Correct score for sample input"
    (is (= 26397
           (sut/score-syntax-errors sample-input)))))

(deftest test-complete-line
  (testing "Closing bracket added for each one left on stack"
    (is (= [{:type :curly :mode :close}
            {:type :curly :mode :close}
            {:type :square :mode :close}
            {:type :square :mode :close}
            {:type :paren :mode :close}
            {:type :curly :mode :close}
            {:type :paren :mode :close}
            {:type :square :mode :close}]

           (sut/complete-line (list {:type :curly :mode :open}
                                    {:type :curly :mode :open}
                                    {:type :square :mode :open}
                                    {:type :square :mode :open}
                                    {:type :paren :mode :open}
                                    {:type :curly :mode :open}
                                    {:type :paren :mode :open}
                                    {:type :square :mode :open}))))))

(deftest test-score-completion
  (testing "Correct score for completed line"
    (is (= 288957
           (sut/score-completion [{:type :curly :mode :close}
                                  {:type :curly :mode :close}
                                  {:type :square :mode :close}
                                  {:type :square :mode :close}
                                  {:type :paren :mode :close}
                                  {:type :curly :mode :close}
                                  {:type :paren :mode :close}
                                  {:type :square :mode :close}])))))

(deftest test-complete-and-find-middle-score
  (testing "For sample input, correct p2 solution"
    (is (= 288957
           (sut/complete-and-find-middle-score sample-input)))))
