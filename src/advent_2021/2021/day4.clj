(ns advent-2021.2021.day4
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

(defn play-one-board-bingo [player-board numbers]
  (loop [state player-board
         num-idx 0]
    (if (= num-idx (count numbers))
      state
      (let [next-num (nth numbers num-idx)
            new-state (update state :board #(call-number % next-num))]
        (if (winner? (:board new-state))
          (merge new-state {:winning-number next-num :win-index num-idx})
          (recur new-state (inc num-idx)))))))

(defn- play-and-sort-results [player-boards numbers]
  (->> player-boards
       (map #(play-one-board-bingo % numbers))
       (sort-by :win-index)))

(defn play-bingo [player-boards numbers]
  (first (play-and-sort-results player-boards numbers)))

(defn play-bingo-to-lose [player-boards numbers]
  (last (play-and-sort-results player-boards numbers)))

(defn- sum-unmarked [board]
  (->> board
       (map (partial filter #(not (:marked? %))))
       (map (partial map :val))
       (map (partial apply +))
       (apply +)))

(defn score [{:keys [board winning-number]}]
  (* winning-number
     (sum-unmarked board)))

(defn- init-players [boards]
  (map-indexed (fn [i b]
                 {:player i :board (board b)})
               boards))

(defn run-part-1 [{:keys [boards numbers]}]
  (let [players (init-players boards)
        winner (play-bingo players numbers)]
    (score winner)))

(defn run-part-2 [{:keys [boards numbers]}]
  (let [players (init-players boards)
        loser (play-bingo-to-lose players numbers)]
    (score loser)))
