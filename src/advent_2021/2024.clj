(ns advent-2021.2024
  (:require [advent-2021.2024.day1 :as d1]
            [advent-2021.2024.day2 :as d2]
            [advent-2021.2024.day3 :as d3]
            [advent-2021.2024.day4 :as d4]
            [advent-2021.2024.day5 :as d5]
            [advent-2021.2024.day6 :as d6]
            [advent-2021.2024.day7 :as d7]
            [advent-2021.utils.input :as i]
            [clojure.data :as data]
            [clojure.edn :as edn]))

(defn- sample-input-file [daynum]
  (format "2024/sample-input/day%s" daynum))

(defn- puzzle-input-file [daynum]
  (format "2024/puzzle-input/day%s" daynum))

(def result-file "output/2024.edn")

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
    :sample-input-2 "2024/sample-input/day3_p2"}
   {:day 4
    :parse-fn i/parse-str-matrix
    :part1 d4/part-1
    :part2 d4/part-2}
   {:day 5
    :parse-fn i/lines
    :part1 d5/part-1
    :part2 d5/part-2}
   {:day 6
    :parse-fn i/parse-str-matrix
    :part1 d6/part-1
    :part2 d6/part-2}])
   {:day 7
    :parse-fn i/lines
    :part1 d7/part-1
    :part2 d7/part-2}])

(defn run-day [{:keys [day parse-fn part1 part2 sample-input-2]}]
  (let [sample-input-1 (parse-fn (sample-input-file day))
        sample-input-2 (if sample-input-2
                         (parse-fn sample-input-2)
                         sample-input-1)
        puzzle-input (parse-fn (puzzle-input-file day))]
    (merge
     {:day day
      :part1 {:sample (part1 sample-input-1)
              :puzzle (part1 puzzle-input)}}
     (when part2
       {:part2 {:sample (part2 sample-input-2)
                :puzzle (part2 puzzle-input)}}))))


(defn run []
  (mapv run-day days))

(defn compare [old-results new-results]
  (let [to-compare (take (count old-results) new-results)
        to-compare (concat (butlast to-compare)
                          [(if (:part2 (last old-results))
                            (last to-compare)
                            (dissoc (last to-compare) :part2))])
        [old-diff new-diff in-both] (data/diff old-results to-compare)]
    (if (and (nil? old-diff) (nil? new-diff))
      nil
      {:current old-diff
       :new new-diff})))

(defn run-and-persist
  "Update the output file, checking that previous results haven't changed"
  []
  (let [current-results (-> result-file
                            slurp
                            edn/read-string)
        new-results (run)
        comparison (compare current-results new-results)]
    (if (nil? comparison)
      (spit result-file (prn-str new-results))
      (throw (ex-info "Output for previous days has changed" comparison)))))



(comment
(run-and-persist)

  )
