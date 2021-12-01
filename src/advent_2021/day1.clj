(ns advent-2021.day1
  (:require [advent-2021.utils.input :as i]))

(defn count-increases [input-vec]
  (loop [previous-num (first input-vec)
         nums-to-process (rest input-vec)
         increase-count 0]
    (cond
      (empty? nums-to-process) increase-count
      (> (first nums-to-process) previous-num) (recur (first nums-to-process) (rest nums-to-process) (inc increase-count))
      :else (recur (first nums-to-process) (rest nums-to-process) increase-count))))

(defn sliding-window-groups [input-vec]
  (loop [groups []
         remaining input-vec]
    (if (< (count remaining) 3)
      groups
      (let [group (vec (take 3 remaining))]
        (recur (conj groups group) (drop 1 remaining))))))

(defn sum-sliding-window [input-vec]
  (->> input-vec
       sliding-window-groups
       (map #(apply + %))))

(defn count-increases-sliding-window [input-vec]
  (-> input-vec
      sum-sliding-window
      count-increases))
