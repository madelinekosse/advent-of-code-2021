(ns advent-2021.core
  (:require
   [advent-2021.day1 :as day1]
   [advent-2021.utils.input :as i]))

(def day1-input (i/file->int-vec "day1"))

(def days [{:input-file "day1"
            :input-parse-fn i/file->int-vec
            :p1-func day1/count-increases
            :p2-func day1/count-increases-sliding-window
            :day-num 1}])

(defn run-day [{:keys [input-file input-parse-fn p1-func p2-func day-num]}]
  (let [input (input-parse-fn input-file)]
    {:label (str "Day " day-num)
     :part-1 (when p1-func (p1-func input))
     :part-2 (when p2-func (p2-func input))}))

(defn all-days
  "I don't do a whole lot."
  []
  (map run-day days))
