(ns advent-2021.2024.day13
  (:require [advent-2021.utils.input :as i]))


(defn parse-input [input-lines]
  (->> input-lines
        (partition-by #(= "" %))
        (filter #(not= [""] %))
       (map (fn [lines]
                (map (fn [line]
                       (let [[_ x y] (re-find #"X[\+|=](\d+), Y[\+|=](\d+)" line)]
                         [(Integer/parseInt x) (Integer/parseInt y)]))
                     lines)))
       (map (fn [[a b c]]
              {:a a :b b :prize c}))))

(defn play-machine [{:keys [a b prize] :as machine}]
  (let [[ax ay] a
        [bx by] b
        [px py] prize
        num-bs (/ (- (* ay px) (* ax py))
                  (- (* ay bx) (* ax by)))]
    {:b-count num-bs
     :a-count (/ (- px (* bx num-bs))
                 ax)}))

(defn total-price [{:keys [a-count b-count]}]
  (+ (* 3 a-count)
     b-count))


(defn part-1 [input-lines]
  (let [machines (parse-input input-lines)]
    (->> machines
         (map play-machine)
         (filter (fn [{:keys [a-count b-count]}]
                   (and (not (ratio? a-count))
                        (not (ratio? b-count)))))
         (map total-price)
         (apply +))))


(defn part-2 [input-lines]
  (let [machines (parse-input input-lines)]
    (->> machines
         (map (fn [{:keys [prize] :as machine}]
                (assoc machine :prize [(+ 10000000000000 (first prize))
                                       (+ 10000000000000 (last prize))])))
         (map play-machine)
         (filter (fn [{:keys [a-count b-count]}]
                   (and (not (ratio? a-count))
                        (not (ratio? b-count)))))
         (map total-price)
         (apply +))))
