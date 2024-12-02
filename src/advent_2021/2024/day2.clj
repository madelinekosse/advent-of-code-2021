(ns advent-2021.2024.day2
  (:require [advent-2021.utils.input :as i]))


(defn all-increasing-or-decreasing? [report]
  (or (= report (sort report))
      (= report (reverse (sort report)))))

(defn levels-differ-by-1-to-3 [report]
  (loop [remaining report]
    (if (<  (count remaining) 2)
      true
      (let [l1 (first remaining)
            l2 (nth remaining 1)
            diff (max (- l1 l2) (- l2 l1))]
        (if (and (>= diff 1)
                 (<= diff 3))
          (recur (rest remaining))
          false)))))

(defn safe? [report]
  (and (all-increasing-or-decreasing? report)
       (levels-differ-by-1-to-3 report)))

(defn dampen
  [report pos]
  (into (subvec report 0 pos) (subvec report (inc pos))))


(defn safe-with-dampener? [report]
  (->> report
       count
       range
       (map (partial dampen report))
       (filter safe?)
       seq
       boolean))

(defn num-safe [safe-fn reports]
  (->> reports
       (filter safe-fn)
       count))
