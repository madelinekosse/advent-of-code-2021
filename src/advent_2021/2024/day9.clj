(ns advent-2021.2024.day9
 (:require [clojure.string :as string]
            [advent-2021.utils.input :as i]
            [clojure.set :as set]))


(defn parse-disc-map [input-str]
  (->> input-str
       (map #(Integer/parseInt (str %)))
       (partition 2 2 [0])
       (map-indexed (fn [i [n1 n2]]
                      [(repeat n1 i)
                       (repeat n2 nil)]))
       flatten
       vec))

(defn compact-filesystem [filesystem]
  (let [actual-files-in-reverse (->> filesystem
                                     (filter some?)
                                     reverse)
        empty-spots (->> (range 0 (count filesystem))
                         (filter (fn [idx]
                                   (nil? (nth filesystem idx)))))]
    (loop [spots-to-fill empty-spots
           files-to-move actual-files-in-reverse
           new-system filesystem]
      (if (empty? spots-to-fill)
        (concat (subvec new-system 0 (- (count filesystem) (count empty-spots)))
                (vec (repeat (count empty-spots) nil)))
        (recur (rest spots-to-fill)
               (rest files-to-move)
               (assoc new-system (first spots-to-fill) (first files-to-move)))))))

(defn checksum [filesystem]
  (->> filesystem
       (map-indexed (fn [i val]
                      (if (nil? val)
                        0
                        (* i val))))
       (apply +)))

(defn part-1 [input-str]
  (-> input-str
      parse-disc-map
      compact-filesystem
      checksum))

(defn add-index-to-lookup
  [lookup val i]
  (if (contains? lookup val)
    (update lookup val conj i)
    (assoc lookup val #{i})))

(defn parse-part-2 [input-str]
  (let [simple-parsed (parse-disc-map input-str)]
    (loop [lookup {}
           i 0]
      (if (= i (count simple-parsed))
        lookup
        (let [val (nth simple-parsed i)]
          (recur (add-index-to-lookup lookup (or val :empty) i)
                 (inc i)))))))

(defn consecutive? [indices]
  (apply = (map - indices (range (first indices)))))


(defn first-n-consecutive-empties [{:keys [empty] :as filesystem} n max-index]
  (loop [sorted-empties (sort empty)]
    (if (or
         (>= (first sorted-empties) max-index)
         (empty? sorted-empties))
      nil
      (let [thischunk (take n sorted-empties)]
        (if (and (= n (count thischunk))
                 (consecutive? thischunk))
          thischunk
          (recur (rest sorted-empties)))))))

(defn compact-one-step-p2 [filesystem file-num-to-process]
  (let [file-locations (get filesystem file-num-to-process)
        num-files-to-move (count file-locations)
        target-locations (first-n-consecutive-empties
                          filesystem
                          num-files-to-move
                          (apply min file-locations))]
    (if target-locations
      (let [new-empties (set/union file-locations
                                   (set (remove
                                         (set target-locations)
                                         (:empty filesystem))))]
        (assoc filesystem
               :empty new-empties
               file-num-to-process (set target-locations)))
      filesystem)))

(defn compact-filesystem-p2 [filesystem]
  (let [files-to-process (->> filesystem
                              keys
                              (filter (partial not= :empty))
                              sort
                              reverse)]
    (loop [files files-to-process
           state filesystem]
      (if (empty? files)
        state
        (recur
         (rest files)
         (compact-one-step-p2 state (first files)))))))

(defn checksum-p2 [filesystem]
  (->> filesystem
       (map (fn [[num values]]
              (if (= num :empty)
                0
                (->> values
                   (map (partial * num))
                   (apply +)))))
       (apply +)))

(defn part-2 [input-str]
  (-> input-str
      parse-part-2
      compact-filesystem-p2
      checksum-p2))
