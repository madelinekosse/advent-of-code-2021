(ns advent-2021.2024.day6
  (:require [advent-2021.utils.input :as input]))


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
         states-seen #{start-state}]
    (let [{:keys [finished?] :as new-state} (move-one state)]
      (cond finished? {:status :finished
                       :num-positions (->> states-seen
                                           (map :guardpos)
                                           set
                                           count)}
            (contains? states-seen new-state) {:status :loop}
            :else (recur new-state (conj states-seen new-state))))))


(defn part-1 [grid]
  (-> grid
      parse-input-grid
      move-guard
      :num-positions))

(defn infinite-loop? [state [obstacle-row obstacle-col]]
  (-> state
      (assoc-in [:grid obstacle-row obstacle-col] "#")
      move-guard
      :status
      (= :loop)))

(defn part-2 [grid]
  (let [start-state (parse-input-grid grid)
        positions-to-check (for [r (range (count grid))
                                 c (range (count (first grid)))]
                             [r c])]
    (->> positions-to-check
         (map (partial infinite-loop? start-state))
         (filter true?)
         count)))
