(ns advent-2021.2024.day8
  (:require
   [clojure.math.combinatorics :as combo]
   [advent-2021.utils.input :as input]
   [clojure.set :as set]))


(defn antenna-locations [input-matrix]
  (->>
   (for [row-idx (range (count input-matrix))
         col-idx (range (count (first input-matrix)))]
     (let [val (nth (nth input-matrix row-idx) col-idx)]
       (if (= val ".")
         []
         [val [row-idx col-idx]])))
   (filter not-empty)
   (reduce (fn [lookup [freq pos]]
             (update lookup freq conj pos))
           {})
   (reduce-kv (fn [m k v]
                (assoc m k (sort v))) {})))

(defn- absdiff [num1 num2]
  (let [diff (- num1 num2)]
    (max diff (- 0 diff))))

(defn calculate-antinode-locations [same-signal-positions]
  (let [[[a1row a1col] [a2row a2col]] (sort same-signal-positions)
        rowdiff (absdiff a1row a2row)
        coldiff (absdiff a1col a2col)]
    (if (and (< a2col a1col) (> a2row a1row))
      [[(- a1row rowdiff) (+ a1col coldiff)]
       [(+ a2row rowdiff) (- a2col coldiff)]]
      [[(- a1row rowdiff) (- a1col coldiff)]
       [(+ a2row rowdiff) (+ a2col coldiff)]])))

(defn gradient [numbers]
  (let [[a b] numbers]
    (cond
      (<= -1 a 1) [a b]
      (<= -1 b 1) [a b]
      :else (let [fraction (rationalize (/ a b))]
              (if (ratio? fraction)
                [(numerator fraction)
                 (denominator fraction)]
                [fraction 1])))))

(defn all-positions [num-rows
                     num-cols
                     start-row start-col
                     step-vertical step-horizontal]
  (loop [positions [[start-row start-col]]]
    (let [[current-row current-col] (last positions)
          next-row (+ current-row step-vertical)
          next-col (+ current-col step-horizontal)]
      (if (and (< -1 next-row num-rows)
               (< -1 next-col num-cols))
        (recur (conj positions [next-row next-col]))
        positions))))


(defn calculate-antinode-locations-part-2 [num-rows num-cols same-signal-positions]
  (let [[[a1row a1col] [a2row a2col]] (sort same-signal-positions)
        rowdiff (absdiff a1row a2row)
        coldiff (absdiff a1col a2col)
        [rowdiff coldiff] (gradient [rowdiff coldiff])]

    (set
     (if (and (< a2col a1col) (> a2row a1row))
       (concat
        (all-positions num-rows num-cols a1row a1col
                       rowdiff (- 0 coldiff))
        (all-positions num-rows num-cols a1row a1col
                       (- 0 rowdiff) coldiff))

       (concat
        (all-positions num-rows num-cols a1row a1col
                       (- 0 rowdiff) (- 0 coldiff))
        (all-positions num-rows num-cols a1row a1col
                       rowdiff coldiff))))))

(defn part-2 [input-matrix]
  (let [locations (vals (antenna-locations input-matrix))
        combos (map
                #(combo/combinations % 2)
                locations)]
    (->> combos
         (apply concat)
         (map (partial calculate-antinode-locations-part-2 (count input-matrix)
                       (count (first input-matrix))))
         (apply set/union)
         count)))

(defn part-1 [input-matrix]
  (let [locations (vals (antenna-locations input-matrix))
        combos (map
                #(combo/combinations % 2)
                locations)]
    (->> combos
         (apply concat)
         (map calculate-antinode-locations)
         (apply concat)
         set
         (filter (fn [[r c]]
                   (and (>=  r 0)
                        (>=  c 0)
                        (< r (count input-matrix))
                        (< c (count (first input-matrix))))))
         count)))
