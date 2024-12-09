(ns advent-2021.2024.day9
  (:require [clojure.string :as string]))


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
