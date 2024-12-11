(ns advent-2021.2024.day11
  (:require [advent-2021.utils.input :as input]
            [clojure.string :as string]))


(defn- split-in-half [stone]
  (let [half (quot (count stone) 2)
        p1 (subs stone 0 half)
        p2 (string/replace (subs stone half) #"^0+" "")]
    (lazy-seq [p1 (if (empty? p2) "0" p2)])))


(defn blink-at [stone]
  (cond
    (= stone "0") ["1"]
    (even? (count stone)) (split-in-half stone)
    :else (conj (lazy-seq []) (str (* 2024 (Long/parseLong stone))))))

(defn blink [stones]
  (mapcat blink-at stones))

(defn blink-n-times [stones n]
  (loop [state (lazy-seq stones)
         counter 0]
    (if (= counter n)
      state
      (recur (blink state)
             (inc counter)))))

(defn part-1 [stones]
  (count (blink-n-times stones 25)))

(defn read-file [filename]
  (string/split (input/single-line filename) #"\s"))

