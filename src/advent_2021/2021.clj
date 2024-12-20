(ns advent-2021.2021
  (:require
   [advent-2021.2021.day1 :as day1]
   [advent-2021.2021.day2 :as day2]
   [advent-2021.2021.day3 :as day3]
   [advent-2021.2021.day4 :as day4]
   [advent-2021.2021.day5 :as day5]
   [advent-2021.2021.day6 :as day6]
   [advent-2021.2021.day7 :as day7]
   [advent-2021.2021.day8 :as day8]
   [advent-2021.2021.day9 :as day9]
   [advent-2021.2021.day10 :as day10]
   [advent-2021.utils.input :as i]
   [clj-time.core :as t]))

(def days [{:input-file "2021/day1"
            :input-parse-fn i/file->int-vec
            :p1-func day1/count-increases
            :p2-func day1/count-increases-sliding-window
            :day-num 1}
           {:input-file "2021/day2"
            :input-parse-fn i/parse-space-delim-rows
            :p1-func day2/move-and-multiply-location-p1
            :p2-func day2/move-and-multiply-location-p2
            :day-num 2}
           {:input-file "2021/day3"
            :input-parse-fn i/lines
            :p1-func day3/power-consumption
            :p2-func day3/life-support-rating
            :day-num 3}
           {:input-file {:boards "2021/day4-boards" :numbers "2021/day4-numbers"}
            :input-parse-fn day4/parse-input-files
            :p1-func day4/run-part-1
            :p2-func day4/run-part-2
            :day-num 4}
           {:input-file "2021/day5"
            :input-parse-fn i/lines
            :p1-func day5/run-part-1
            :p2-func day5/run-part-2
            :day-num 5}
           {:input-file "2021/day6"
            :input-parse-fn (comp first i/parse-comma-delim-rows)
            :p1-func (partial day6/count-lanternfish-after-n-days 80)
            :p2-func (partial day6/count-lanternfish-after-n-days 256)
            :day-num 6}
           {:input-file "2021/day7"
            :input-parse-fn (comp first i/parse-comma-delim-rows)
            :p1-func day7/align-submarines-p1
            :p2-func day7/align-submarines-p2
            :day-num 7}
           {:input-file "2021/day8"
            :input-parse-fn day8/parse-input
            :p1-func day8/count-easy-digits
            :p2-func day8/sum-digits
            :day-num 8}
           {:input-file "2021/day9"
            :input-parse-fn i/parse-matrix
            :p1-func day9/total-risk-level
            :p2-func day9/find-and-multiply-largest-basins
            :day-num 9}
           {:input-file "2021/day10"
            :input-parse-fn i/lines
            :p1-func day10/score-syntax-errors
            :p2-func day10/complete-and-find-middle-score
            :day-num 10}])

(defn- with-timer
  [fn input]
  (let [start-time (t/now)
        res (fn input)
        end-time (t/now)]
    {:result res
     :time (t/in-millis (t/interval start-time end-time))}))

(defn run-day [{:keys [input-file input-parse-fn p1-func p2-func day-num]}]
  (let [input (input-parse-fn input-file)]
    {:label (str "Day " day-num)
     :part-1 (when p1-func (with-timer p1-func input))
     :part-2 (when p2-func (with-timer p2-func input))}))

(defn all-days
  []
  (let [res (map run-day days)
        total-time (->> res
                        (map (fn [{:keys [part-1 part-2]}]
                               (+ (:time part-1) (:time part-2))))
                        (apply +))]
    {:dailies res
     :cume-time total-time}))
