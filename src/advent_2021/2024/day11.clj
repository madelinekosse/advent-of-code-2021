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

(defn blink-and-count-one-stone [stone num-times]
  (loop [stones (lazy-seq [stone])
         n num-times]
    (if (= 0 n)
      (count stones)
      (recur (mapcat blink-at stones) (dec n)))))


(defn blink-n-times-and-count [stones num-times]
   (let [cache (atom {})] ; Cache for storing precomputed results
     (loop [to-do (lazy-seq stones)
            acc 0]
       (if-let [stone (first to-do)]
         (let [result (if-let [cached (get @cache [stone num-times])]
                        cached
                       (let [computed (blink-and-count-one-stone stone num-times)]
                          (swap! cache assoc [stone num-times] computed)
                         computed))]
           (recur (rest to-do) (+ acc result)))
        acc))))

(defn part-1 [stones]
  (blink-n-times-and-count stones 25))


(defn read-file [filename]
  (string/split (input/single-line filename) #"\s"))

