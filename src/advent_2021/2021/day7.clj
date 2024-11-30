(ns advent-2021.2021.day7)

(defn- abs [n] (max n (- n)))

(defn fuel-to-align [position current-positions fuel-cost-fn]
  (->> current-positions
       (map (partial - position))
       (map abs)
       (map fuel-cost-fn)
       (apply +)))

(defn- align-submarines [current-positions fuel-cost-fn]
  (->> (range (apply min current-positions) (inc (apply max current-positions)))
       (map (fn [pos] {:position pos :fuel (fuel-to-align pos current-positions fuel-cost-fn)}))
       (sort-by :fuel)
       first))

(defn align-submarines-p1 [current-positions]
  (align-submarines current-positions identity))

(defn- sum-nums-to-n [n]
  (int (* (/ n 2) (inc n))))

(defn align-submarines-p2 [current-positions]
  (align-submarines current-positions sum-nums-to-n))
