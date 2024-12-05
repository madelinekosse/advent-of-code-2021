(ns advent-2021.2024.day5
  (:require [advent-2021.utils.input :as i]
            [clojure.string :as string]))


(defn parse-input-lines [file-lines]
  (let [[rules _ updates] (partition-by empty? file-lines)]
    {:rules (i/parse-delim-rows rules #"\|")
     :updates (i/parse-delim-rows updates #",")}))

(defn update-passes-rule? [update must-come-first must-come-after]
  (let [relevant-pages (filterv
                        #(contains? #{must-come-first must-come-after} %)
                               update)]
  (if (= (count relevant-pages) 2)
    (= relevant-pages [must-come-first must-come-after])
    true)))

(defn update-ok? [rules update]
  (->> rules
       (map (fn [[e1 e2]]
              (update-passes-rule? update e1 e2)))
       (every? true?)))

(defn add-middle-numbers [lists-of-numbers]
  (->> lists-of-numbers
       (map (fn [update]
              (nth update (quot (count update) 2))))
       (apply +)))

(defn sum-middle-of-good-updates [rules updates]
  (->> updates
       (filter (partial update-ok? rules))
       add-middle-numbers))

(defn part-1 [file-lines]
  (let [{:keys [rules updates]} (parse-input-lines file-lines)]
    (sum-middle-of-good-updates rules updates)))

(defn index-of [e coll]
  (first (keep-indexed #(if (= e %2) %1) coll)))

(defn comparator [rules n1 n2]
  (loop [rules-to-check rules]
    (if (empty? rules-to-check)
      0
      (let [[earlier later] (first rules-to-check)]
        (if (and (= n1 earlier) (= n2 later))
          -1
          (if (and (= n1 later) (= n2 earlier))
            1
            (recur (rest rules-to-check))))))))

(defn sort-by-rules [rules numbers]
  (sort-by identity (partial comparator rules) numbers))

(defn sum-middle-of-sorted-bad-updates [rules updates]
  (->> updates
       (filter (fn [u] (not (update-ok? rules u))))
       (map (partial sort-by-rules rules))
       add-middle-numbers))

(defn part-2 [file-lines]
  (let [{:keys [rules updates]} (parse-input-lines file-lines)]
    (sum-middle-of-sorted-bad-updates rules updates)))
