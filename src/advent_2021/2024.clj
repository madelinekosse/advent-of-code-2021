(ns advent-2021.2024
  (:require [advent-2021.2024.day1 :as d1]
            [advent-2021.2024.day2 :as d2]
            [advent-2021.utils.input :as i]))

(defn- sample-input-file [daynum]
  (format "2024/sample-input/day%s" daynum))

(defn- puzzle-input-file [daynum]
  (format "2024/puzzle-input/day%s" daynum))

(def days
  [{:day 1
    :parse-fn d1/file->lists
    :part1 d1/part-1
    :part2 d1/similarity-score}
   {:day 2
    :parse-fn i/parse-space-delim-rows
    :part1 (partial d2/num-safe d2/safe?)
    :part2 (partial d2/num-safe d2/safe-with-dampener?)}])

(defn run-day [{:keys [day parse-fn part1 part2]}]
  (let [sample-input (parse-fn (sample-input-file day))
        puzzle-input (parse-fn (puzzle-input-file day))]
    {:part1 {:sample (part1 sample-input)
             :puzzle (part1 puzzle-input)}

     :part2 {:sample (part2 sample-input)
             :puzzle (part2 puzzle-input)}}))

(defn run []
  (map run-day days))
