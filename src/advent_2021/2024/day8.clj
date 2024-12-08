(ns advent-2021.2024.day8
  (:require
   [clojure.math.combinatorics :as combo]
   [advent-2021.utils.input :as input]))


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
