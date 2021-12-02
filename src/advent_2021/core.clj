(ns advent-2021.core
  (:require
   [advent-2021.day1 :as day1]
   [advent-2021.day2 :as day2]
   [advent-2021.utils.input :as i]))

(def days [{:input-file "day1"
            :input-parse-fn i/file->int-vec
            :p1-func day1/count-increases
            :p2-func day1/count-increases-sliding-window
            :day-num 1}
           {:input-file "day2"
            :input-parse-fn i/parse-space-delim-rows
            :p1-func day2/move-and-multiply-location
            :day-num 2}])

(defn run-day [{:keys [input-file input-parse-fn p1-func p2-func day-num]}]
  (let [input (input-parse-fn input-file)]
    {:label (str "Day " day-num)
     :part-1 (when p1-func (p1-func input))
     :part-2 (when p2-func (p2-func input))}))

(defn all-days
  []
  (map run-day days))
