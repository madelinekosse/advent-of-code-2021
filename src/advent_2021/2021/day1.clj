(ns advent-2021.2021.day1)

(defn- count-increases-by-window-size [input-vec window-size]
  (loop [previous-sum (->> input-vec (take window-size) (apply +))
         nums-to-process (rest input-vec)
         increase-count 0]
    (if (>= (count nums-to-process) window-size)
      (let [window-sum (->> nums-to-process (take window-size) (apply +))]
        (recur window-sum
               (rest nums-to-process)
               (if (> window-sum previous-sum) (inc increase-count) increase-count)))
      increase-count)))

(defn count-increases [input-vec]
  (count-increases-by-window-size input-vec 1))

(defn count-increases-sliding-window [input-vec]
  (count-increases-by-window-size input-vec 3))
