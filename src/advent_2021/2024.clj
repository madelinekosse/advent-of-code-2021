(ns advent-2021.2024
  (:require [advent-2021.2024.day1 :as d1]
            [advent-2021.2024.day2 :as d2]
            [advent-2021.2024.day3 :as d3]
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
    :part2 (partial d2/num-safe d2/safe-with-dampener?)}
   {:day 3
    :parse-fn i/lines
    :part1 d3/part-1
    :part2 d3/part-2
    :sample-input-2 "2024/sample-input/day3_p2"}])

(defn run-day [{:keys [day parse-fn part1 part2 sample-input-2]}]
  (let [sample-input-1 (parse-fn (sample-input-file day))
        sample-input-2 (if sample-input-2
                         (parse-fn sample-input-2)
                         sample-input-1)
        puzzle-input (parse-fn (puzzle-input-file day))]
    {:part1 {:sample (part1 sample-input-1)
             :puzzle (part1 puzzle-input)}

     :part2 {:sample (part2 sample-input-2)
             :puzzle (part2 puzzle-input)}}))

(defn run []
  (map run-day days))
