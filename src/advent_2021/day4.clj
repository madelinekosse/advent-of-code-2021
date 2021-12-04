(ns advent-2021.day4
  (:require [advent-2021.utils.input :as i]))

(defn- parse-boards-from-file [file]
  (->> file
       i/parse-space-delim-rows
       (filter #(not (empty? %)))
       (partition 5)))

(defn parse-input-files [{:keys [boards numbers]}]
  {:numbers (first (i/parse-comma-delim-rows numbers))
   :boards (parse-boards-from-file boards)})

(defn- new-cell [num] {:val num :marked? false})

(defn board [rows]
  (mapv
   (partial mapv new-cell)
   rows))

(defn call-number [board number]
  (mapv
   (partial mapv (fn [{:keys [val] :as cell}]
                   (if (= val number)
                     (assoc cell :marked? true)
                     cell)))
   board))

(defn- all-marked? [row-or-coll]
  (every? true? (map :marked? row-or-coll)))

(defn- contains-winning-row? [rows-or-colls]
  (some? (some all-marked? rows-or-colls)))

(defn- columns [board]
  (apply map vector board))

(defn winner? [board]
  (cond
    (contains-winning-row? board) true
    (contains-winning-row? (columns board)) true
    :else false))

(defn- call-and-check-winner-state [player-states number]
  (->> player-states
       (map (fn [state]
              (update state :board #(call-number % number))))
       (map (fn [{:keys [board] :as state}]
              (if (winner? board)
                (assoc state :winning-number number)
                state)))))

(defn play-bingo [player-boards numbers]
  (loop [boards player-boards
         to-call numbers]
    (if (empty? to-call)
      {} ;; no winner
      (let [updated (call-and-check-winner-state boards (first to-call))
            winners (filter :winning-number updated)]
        (if (empty? winners)
          (recur updated (rest to-call))
          winners)))))

(defn- sum-unmarked [board]
  (->> board
       (map (partial filter #(not (:marked? %))))
       (map (partial map :val))
       (map (partial apply +))
       (apply +)))

(defn score [{:keys [board winning-number]}]
  (* winning-number
     (sum-unmarked board)))

(defn run-part-1 [{:keys [boards numbers]}]
  (let [players (map-indexed (fn [i b]
                               {:player i :board (board b)})
                             boards)
        winner (first (play-bingo players numbers))]
    (score winner)))
