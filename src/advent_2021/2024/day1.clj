(ns advent-2021.2024.day1
  (:require [advent-2021.utils.input :as i]))

(defn parse-rows [rows]
  (reduce (fn [[list1 list2] [n1 n2]]
               [(conj list1 n1)
                (conj list2 n2)]
               )
          [[][]]
          rows))

(defn pair-up [lists]
  (let [[list1 list2] (map sort lists)]
    (->> (interleave list1 list2)
         (partition 2))))

(defn add-distances [pairs]
  (->> pairs
       (map (fn [[n1 n2]]
              (max (- n1 n2)
                   (- n2 n1))))
       (apply +)))

(defn part-1 [rows]
  (-> rows
      pair-up
      add-distances))

(defn single-similarity-score [list number]
  (->> list
       (filter (partial = number))
       count
       (* number)))

(defn similarity-score [[list1 list2]]
  (->> list1
       (map (partial single-similarity-score list2))
       (apply +)))

(defn file->lists [file]
  (-> file
      i/parse-space-delim-rows
      parse-rows))
