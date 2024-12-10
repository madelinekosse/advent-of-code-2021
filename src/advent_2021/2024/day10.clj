(ns advent-2021.2024.day10
  (:require [advent-2021.utils.input :as i]))


(defn valid-steps [this-pos trail-map]
  (let [this-val (get-in trail-map this-pos)]
    (filter
     (fn [pos] (and (some? (get-in trail-map pos))
                    (= (inc this-val)
                       (get-in trail-map pos))))
     [[(first this-pos) (dec (last this-pos))]
      [(first this-pos) (inc (last this-pos))]
      [(inc (first this-pos)) (last this-pos)]
      [(dec (first this-pos)) (last this-pos)]])))

(defn summits-reachable [trailmap start-position]
  (let [current-val (get-in trailmap start-position)]
    (if (= current-val 9)
      [start-position]
        (->> (valid-steps start-position trailmap)
             (map (partial summits-reachable trailmap))
             (apply concat)
             set))))

(defn check-trailhead [trailmap start-position]
  (let [val (get-in trailmap start-position)]
    (if (not= 0 val)
      0
      (count (summits-reachable trailmap start-position)))))

(defn part-1 [trailmap]
  (->> (for [r (range (count trailmap))
             c (range (count (first trailmap)))]
         [r c])
       (map (partial check-trailhead trailmap))
       (apply +)))
