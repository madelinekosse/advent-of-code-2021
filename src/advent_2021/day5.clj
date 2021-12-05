(ns advent-2021.day5
  (:require [clojure.string :as string]))

(defn- parse-coords [coord-str]
  (let [[x y] (string/split coord-str #",")]
    {:x (Integer/parseInt x)
     :y (Integer/parseInt y)}))

(defn- input-row->line [row]
  (let [[from to] (string/split row #" -> ")]
    [(parse-coords from) (parse-coords to)]))

(defn input-rows->lines [rows]
  (map input-row->line rows))

(defn filter-remove-diagonals [lines]
  (filter
   (fn [[from to]]
     (or (= (:x from) (:x to)) (= (:y from) (:y to))))
   lines))

(defn points-crossed [[from to]]
  (let [{x1 :x y1 :y} from
        {x2 :x y2 :y} to
        x-direction (cond (< x1 x2) inc (> x1 x2) dec :else identity)
        y-direction (cond (< y1 y2) inc (> y1 y2) dec :else identity)]
    (loop [points [from]]
      (let [{last-x :x last-y :y :as last-point} (last points)]
        (if (= last-point to)
          points
          (recur (conj points {:x (x-direction last-x) :y (y-direction last-y)})))))))

(defn- line-count-by-point [lines]
  (let [points-crossed (->> lines
                            (map points-crossed)
                            (apply concat))]
    (loop [points points-crossed
           lookup {}]
      (if (empty? points)
        lookup
        (let [count (get lookup (first points) 0)]
          (recur (rest points) (assoc lookup (first points) (inc count))))))))

(defn count-intersections [lines]
  (->> lines
       line-count-by-point
       vals
       (filter #(>= % 2))
       count))

(defn- count-intersections-from-input [input-lines exclude-diagonals?]
  (let [filter-fn (if exclude-diagonals? filter-remove-diagonals identity)]
    (-> input-lines
        input-rows->lines
        filter-fn
        count-intersections)))

(defn run-part-1 [input-lines]
  (count-intersections-from-input input-lines true))

(defn run-part-2 [input-lines]
  (count-intersections-from-input input-lines false))
