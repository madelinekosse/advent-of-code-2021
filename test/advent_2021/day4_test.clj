(ns advent-2021.day4-test
  (:require [advent-2021.day4 :as sut]
            [clojure.test :refer :all]))

(def board-1 [[22 13 17 11  0]
              [8  2 23  4 24]
              [21  9 14 16  7]
              [6 10  3 18  5]
              [1 12 20 15 19]])

(def board-2 [[3 15  0  2 22]
              [9 18 13 17  5]
              [19  8  7 25 23]
              [20 11 10 24  4]
              [14 21 16 12  6]])

(def board-3 [[14 21 17 24  4]
              [10 16 15  9 19]
              [18  8 23 26 20]
              [22 11 13  6  5]
              [2  0 12  3  7]])

(def numbers-to-call [7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1])

(deftest test-call-number
  (let [board (sut/board board-1)]
    (testing "When number is not on the board, it's unchanged"
      (is (= board
             (sut/call-number board 25))))
    (testing "When number is on the board, it is marked"
      (is (= (assoc-in board [0 0 :marked?] true)
             (sut/call-number board 22))))))

(defn- call-all-numbers [board numbers]
  (reduce (fn [b n] (sut/call-number b n)) board numbers))

(deftest test-check-win
  (testing "A non-winning board"
    (let [board (call-all-numbers (sut/board board-1) [11 3 18 6])]
      (is (false? (sut/winner? board)))))
  (testing "A board with a horizontal row wins"
    (let [board (call-all-numbers (sut/board board-1) [21 9 14 16 7])]
      (is (sut/winner? board))))
  (testing "A board with a vertical row wins"
    (let [board (call-all-numbers (sut/board board-1) [11 4 16 18 15])]
      (is (sut/winner? board)))))

(deftest test-play-bingo
  (let [players [{:player 1 :board (sut/board board-1)}
                 {:player 2 :board (sut/board board-2)}
                 {:player 3 :board (sut/board board-3)}]
        winners (sut/play-bingo players numbers-to-call)]
    (testing "There is only one winner"
      (is (= 1 (count winners))))
    (testing "Winner is player 3"
      (is (= 3 (:player (first winners)))))
    (testing "Winning number was 24"
      (is (= 24 (:winning-number (first winners)))))
    (testing "Winning store is calculated"
      (is (= 4512
             (sut/score (first winners)))))))

(deftest test-parse-input-files
  (let [inputs (sut/parse-input-files {:boards "day4-sample-boards" :numbers "day4-sample-numbers"})]
    (testing "Numbers parsed"
      (is (= numbers-to-call
             (:numbers inputs))))
    (testing "Right number of boards parsed"
      (is (= 3
             (count (:boards inputs)))))
    (testing "Correct boards parsed"
      (is (= [board-1 board-2 board-3]
             (:boards inputs))))))
