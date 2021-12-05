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

(defn- horizontal? [[from to]]
  (let [{from-y :y} from
        {to-y :y} to]
    (= from-y to-y)))

(defn- vertical? [[from to]]
  (let [{from-x :x} from
        {to-x :x} to]
    (= from-x to-x)))

(defn- diagonal-45? [coords]
  (let [[x1 x2] (sort (map :x coords))
        [y1 y2] (sort (map :y coords))]
    (= (- x2 x1)
       (- y2 y1))))

(defn filter-remove-diagonals [lines]
  (filter #(or (vertical? %) (horizontal? %)) lines))

(defn- points-crossed-horiz [[from to]]
  (let [{x1 :x y :y} from
        {x2 :x} to
        from-x (min x1 x2)
        to-x (max x1 x2)]
    (map (fn [x] {:x x :y y})
         (range from-x (inc to-x)))))

(defn- points-crossed-vert [[from to]]
  (let [{x :x y1 :y} from
        {y2 :y} to
        from-y (min y1 y2)
        to-y (max y1 y2)]
    (map (fn [y] {:x x :y y})
         (range from-y (inc to-y)))))

(defn- points-crossed-diag [[from to]]
  (let [{x1 :x y1 :y} from
        {x2 :x y2 :y} to
        x-direction (if (< x1 x2) inc dec)
        y-direction (if (< y1 y2) inc dec)]
    (loop [points [from]]
      (let [{last-x :x last-y :y :as last-point} (last points)]
        (if (= last-point to)
          points
          (recur (conj points {:x (x-direction last-x) :y (y-direction last-y)})))))))

(defn points-crossed [line]
  (cond
    (vertical? line) (points-crossed-vert line)
    (horizontal? line) (points-crossed-horiz line)
    (diagonal-45? line) (points-crossed-diag line)))

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

(defn run-part-1 [input-lines]
  (-> input-lines
      input-rows->lines
      filter-remove-diagonals
      count-intersections))

(defn run-part-2 [input-lines]
  (-> input-lines
      input-rows->lines
      count-intersections))
