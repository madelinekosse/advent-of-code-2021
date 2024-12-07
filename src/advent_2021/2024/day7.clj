(ns advent-2021.2024.day7
  (:require [advent-2021.utils.input :as input]
            [clojure.string :as string]
            [clojure.math.combinatorics :as combo]
            ))

(defn- parse-line [line]
  (let [[test-value value-str] (string/split line #": ")
        values (string/split value-str #" ")]
    {:test-value (Long/parseLong test-value)
     :numbers (mapv #(Long/parseLong %) values)}))

(defn parse-lines [lines]
  (map parse-line lines))

(defn- permutations
  [num-plusses num-timeses num-concats]
  (combo/permutations (concat (repeat num-plusses :plus)
                              (repeat num-timeses :times)
                              (repeat num-concats :concat))))

(defn- pairs-sum-to-n
  [n]
  (->> (range 0 (inc n))
       (map
        (fn [i] [i (- n i)]))))

(defn- trios-sum-to-n
  [n]
  (let [pairs (pairs-sum-to-n n)]
    (->> pairs
         (map (fn [[n1 n2]]
                (let [n2-pairs (pairs-sum-to-n n2)]
                  (map
                   (fn [[p1 p2]]
                     [n1 p1 p2])
                   n2-pairs))))
         (apply concat))))


(defn operation-permutations [number include-concat?]
  (let [combinations (if include-concat?
                       (trios-sum-to-n number)
                       (map
                        (fn [[plus times]] [plus times 0])
                        (pairs-sum-to-n number)))]
    (->> combinations
         (map (partial apply permutations))
         (apply concat)
         set)))

(defn op-permutations-by-input-count [include-concat? longest-input]
  (->> (range 1 longest-input)
       (map (fn [i] {(inc i) (operation-permutations i include-concat?)}))
       (into {})))

(defn- numeric-concat
  [n1 n2]
  (Long/parseLong (str n1 n2)))

(defn ops-satisfy-expression? [ops {:keys [test-value numbers]}]
  (let [ops (map {:plus + :times * :concat numeric-concat} ops)
        total (reduce-kv (fn [total i number]
                           (let [op (nth ops i)]
                             (op total number)))
                         (first numbers)
                         (vec (rest numbers)))]

    (= total test-value)))

(defn can-calibrate?
  [all-op-permutations {:keys [test-value numbers] :as equation}]
  (let [op-permutations (get  all-op-permutations (count numbers))]
    (loop [ops-to-check op-permutations]
      (cond
        (empty? ops-to-check) false
        (ops-satisfy-expression? (first ops-to-check) equation) true
        :else (recur (rest ops-to-check))))))

(defn sum-calibration-values [parsed-input include-concat?]
  (let [all-permutations (->> parsed-input
                              (map :numbers)
                              (map count)
                              (apply max)
                              (op-permutations-by-input-count include-concat?))]
    (->> parsed-input
         (filter (partial can-calibrate? all-permutations))
         (map :test-value)
         (apply +))))

(defn part-1 [input-lines]
  (-> input-lines
      parse-lines
      (sum-calibration-values false)))

(defn part-2 [input-lines]
  (-> input-lines
      parse-lines
      (sum-calibration-values true)))
