(ns advent-2021.2024.day12
  (:require [advent-2021.utils.input :as i]
            [clojure.set :as set]))


(defn adjacent? [[r1 c1] [r2 c2]]
  (let [rowdiff (- r1 r2)
        coldiff (- c1 c2)]
    (or
     (and (= c1 c2)
          (= 1 (Math/abs rowdiff)))
     (and (= r1 r2)
             (= 1 (Math/abs coldiff))))))

(defn all-squares [garden]
  (for [row (range (count garden))
        col (range (count (first garden)))]
    [row col]))


(defn matches-for-square [garden [row col]]
  (let [candidate-squares (for [r (range (max 0 (dec row)) (inc (inc row)))
                                c (range (max 0 (dec col)) (inc (inc col)))]
                            [r c])]
  (filter
   (fn [square]
     (and (= (get-in garden square) (get-in garden [row col]))
          (not= square [row col])
          (adjacent? square [row col])))
   candidate-squares)))

(defn all-matches-all-squares [garden]
  (for [square (all-squares garden)]
    (conj (matches-for-square garden square) square)))


(defn find-regions [input]
  (reduce (fn [groups items]
            (let [connected (filter #(not (empty? (set/intersection % (set items)))) groups)
                  remaining (remove #(not (empty? (set/intersection % (set items)))) groups)
                  merged (reduce set/union (conj connected (set items)))]
              (conj remaining merged)))
          []
          input))


(defn area-and-perimiter [group]
  {:area (count group)
   :perimiter (loop [to-do (vec group)
                     perimiter 0]
                (if-let [[row col] (first to-do)]
                  (let [top-edge? (not (contains? group [(dec row) col]))
                        bottom-edge? (not (contains? group [(inc row) col]))
                        left-edge? (not (contains? group [row (dec col)]))
                        right-edge? (not (contains? group [row (inc col)]))
                        edges (cond-> 0
                                top-edge? (inc)
                                bottom-edge? (inc)
                                left-edge? (inc)
                                right-edge? (inc))]
                    (recur (rest to-do)
                           (+ edges perimiter)))
                  perimiter))})

(defn part-1 [garden]
  (->> garden
       all-matches-all-squares
       find-regions
       (map area-and-perimiter)
       (map (fn [{:keys [area perimiter]}]
              (* area perimiter)))
       (apply +)))
