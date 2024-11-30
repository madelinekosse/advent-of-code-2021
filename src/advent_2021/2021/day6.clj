(ns advent-2021.2021.day6)

(defn- lanternfish-state [[days-to-reproduce frequency]]
  (if (= days-to-reproduce 0)
    {6 frequency 8 frequency}
    {(dec days-to-reproduce) frequency}))


(defn lanternfish-day [school]
  (->> school
       vec
       (map lanternfish-state)
       (apply merge-with +)))

(defn lanternfish-days [num-days school]
  ((apply comp (repeat num-days lanternfish-day)) school))

(defn count-lanternfish-after-n-days [days school]
  (->> school
       frequencies
       (lanternfish-days days)
       vals
       (apply +)))
