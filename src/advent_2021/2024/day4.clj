
(ns advent-2021.2024.day4
  (:require [clojure.set :as set]
            [advent-2021.utils.input :as i]))

(defn l->r [input-lines]
  (map (partial apply str) input-lines))


(defn top->bottom [input-lines]
  (->> input-lines
       (apply map vector)
       (map (partial apply str))))

(defn reverse-a-string [some-string]
  (apply str (reverse some-string)))


(defn diagonal [matrix start-row start-col row-op col-op]
  (loop [values []
         row start-row
         col start-col]
    (let [thisval (get (get matrix row) col)]
      (if (nil? thisval)
        values
        (recur (conj values thisval)
               (row-op row)
               (col-op col))))))

(defn diagonals [matrix direction]
  (let [rows (count matrix)
        cols (count (first matrix))
        start-points (if (= direction :down)
                       (concat (map #(vector 0 %) (range cols))
                               (map #(vector % 0) (range 1 rows)))
                       (concat (map #(vector (dec rows) %) (range cols))
                               (map #(vector % 0) (range (dec rows)))))
        row-op (if (= direction :down) inc dec)
        col-op inc]
    (map (fn [[row col]]
           (apply str (diagonal matrix row col row-op col-op)))
         start-points)))

(defn count-xmas [some-string]
  (count (re-seq (re-pattern "(XMAS)") some-string)))


(defn part-1 [input-lines]
  (let [horizontals (l->r input-lines)
        horizontal-reverse (map reverse-a-string horizontals)
        verticals (top->bottom input-lines)
        vertical-reverse (map reverse-a-string verticals)
        diagonals-down (diagonals input-lines :down)
        diagonals-down-reverse (map reverse-a-string diagonals-down)
        diagonals-up (diagonals input-lines :up)
        diagonals-up-reverse (map reverse-a-string diagonals-up)]
    (->> [horizontals horizontal-reverse verticals vertical-reverse diagonals-down diagonals-down-reverse diagonals-up diagonals-up-reverse]
         flatten
         (map count-xmas)
         (apply +))))

(defn x-mas-found? [submatrix-3]
  (let [[r1 r2 r3] submatrix-3
        diag-down [(nth r1 0) (nth r2 1) (nth r3 2)]
        diag-up [(nth r3 0) (nth r2 1) (nth r1 2)]
        ok? (fn [diag] (or (= diag ["M" "A" "S"])
                           (= diag ["S" "A" "M"])))]
    (and (ok? diag-down)
         (ok? diag-up))))

(defn get-3-by-3-blocks [inputs]
  (let [rows (count inputs)
        cols (count (first inputs))]
    (loop [blocks []
           startrow 0
           startcol 0]
      (if (= startrow (- rows 2))
        blocks
        (if (= startcol (- cols 2))
          (recur blocks (inc startrow) 0)
          (let [newblock [(subvec (nth inputs startrow) startcol (+ startcol 3))
                          (subvec (nth inputs (+ startrow 1)) startcol (+ startcol 3))
                          (subvec (nth inputs (+ startrow 2)) startcol (+ startcol 3))]]
            (recur (conj blocks newblock) startrow (inc startcol))))))))


(defn part-2 [input-lines]
  (let [partitions (get-3-by-3-blocks input-lines)]
    (->> partitions
         (map x-mas-found?)
         (filter true?)
         count)))

