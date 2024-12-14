(ns advent-2021.2024.day13
  (:require [advent-2021.utils.input :as i]))


(defn parse-input [input-lines]
  (->> input-lines
        (partition-by #(= "" %))
        (filter #(not= [""] %))
       (map (fn [lines]
                (map (fn [line]
                       (let [[_ x y] (re-find #"X[\+|=](\d+), Y[\+|=](\d+)" line)]
                         [(Integer/parseInt x) (Integer/parseInt y)]
                         ))
                     lines)))
       (map (fn [[a b c]]
              {:a a :b b :prize c}))
       ))

(defn max-b-presses [{:keys [b prize]}]
  (let [[bx by] b
        [px py] prize
        max-b-xways (quot px bx)
        max-b-yways (quot py by)]
      (min max-b-xways max-b-yways)))

(defn play-machine [{:keys [a b prize] :as machine}]
  (let [[ax ay] a
        [bx by] b
        [px py] prize]
    (loop [b-count (max-b-presses machine)]
      (if (< b-count 0)
        nil
        (let [remainder-x (- px (* b-count bx))
              remainder-y (- py (* b-count by))
              a-count-x (quot remainder-x ax)
              a-count-y (quot remainder-y ay)]
          (if (and (= a-count-x a-count-y)
                   (zero? (- remainder-x (* a-count-x ax)))
                   (zero? (- remainder-y (* a-count-y ay))))
            {:a-count a-count-x
             :b-count b-count}
            (recur (dec b-count))))))))

(defn total-price [{:keys [a-count b-count]}]
  (+ (* 3 a-count)
     b-count))


(defn part-1 [input-lines]
  (let [machines (parse-input input-lines)]
    (->> machines
         (map play-machine)
         (filter some?)
         (map total-price)
         (apply +))))
