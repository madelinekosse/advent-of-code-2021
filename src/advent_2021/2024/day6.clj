(ns advent-2021.2024.day6
  (:require [advent-2021.utils.input :as input]
            [clojure.core.async :refer [timeout]]))


(defn position-of-guard [grid]
  (->> grid
       (map-indexed (fn [i row]
                      [i (.indexOf row "^")]
                      ))
       (filter (fn [[r c]] (not= c -1)))
       first))

(defn parse-input-grid [grid]
  (let [guardpos (position-of-guard grid)]
    {:grid (assoc-in grid [(first guardpos) (last guardpos)] ".")
     :guardpos guardpos
     :direction :up}))

(def turn-right-direction {:up :right
                           :right :down
                           :down :left
                           :left :up})

(defn move-one [{:keys [grid guardpos direction]}]
  (let [[row col] guardpos
        [newrow newcol] (case direction
                          :up [(dec row) col]
                          :down [(inc row) col]
                          :right [row (inc col)]
                          :left [row (dec col)])

        finished? (or (= newrow (count grid))
                      (= newrow -1)
                      (= newcol (count (first grid)))
                      (= newcol -1))]
    (if finished?
      {:finished? true}
      (let [obstacle? (= "#" (nth (nth grid newrow) newcol))]
        (if obstacle?
          (move-one {:grid grid
                     :guardpos guardpos
                     :direction (get turn-right-direction direction)} )

          {:grid grid
           :guardpos [newrow newcol]
           :direction direction
           :finished? false})))))

(defn move-guard [start-state]
  (loop [state start-state
         positions-seen #{(:guardpos start-state)}]
    (let [{:keys [finished?] :as new-state} (move-one state)]
      (if finished?
        (count positions-seen)
        (recur new-state (conj positions-seen (:guardpos new-state)))))))

(defn part-1 [grid]
  (-> grid
      parse-input-grid
      move-guard))

(defn infinite-loop? [state [obstacle-row obstacle-col]]
  (let [new-state (assoc-in state [:grid obstacle-row obstacle-col] "#")
        fut (future (move-guard new-state))]
    (try
      (let [result (deref fut 100 :timeout)]
        (if (= result :timeout)
          (do
            (future-cancel fut)
            true)
          false))
      (catch Exception e
        (println "An error occurred: " e)))))

(defn part-2 [grid]
  (let [start-state (parse-input-grid grid)
        positions-to-check (for [r (range (count grid))
                                 c (range (count (first grid)))]
                             [r c])]
    (->> positions-to-check
         (map (partial infinite-loop? start-state))
         (filter true?)
         count)))
