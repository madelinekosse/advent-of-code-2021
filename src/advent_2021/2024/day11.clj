(ns advent-2021.2024.day11
  (:require [advent-2021.utils.input :as input]
            [clojure.string :as string]))


(defn- split-in-half [stone]
  (->> stone
       (partition (/ (count stone) 2))
       (map (partial apply str))
       (map #(Long/parseLong %))
       (map str)))

(defn blink-at [stone]
  (cond
    (= stone "0") ["1"]
    (even? (count stone)) (split-in-half stone)
    :else (-> (Long/parseLong stone)
              (* 2024)
              str
              vector)))

(defn blink [stones]
  (->> stones
       (map blink-at)
       flatten))

(defn blink-n-times [stones n]
  (loop [state stones
         counter 0]
    (if (= counter n)
      state
      (recur (blink state)
             (inc counter)))))

(defn part-1 [stones]
  (count (blink-n-times stones 25)))

(defn read-file [filename]
  (string/split (input/single-line filename) #"\s"))

