(ns advent-2021.day2)

(def directions {'forward {:axis :x :updater +}
                 'up {:axis :y :updater +}
                 'down {:axis :y :updater -}})

(defn- apply-direction [position [direction distance]]
  (let [{:keys [axis updater]} (get directions direction)]
    (update position axis #(updater % distance))))

(defn apply-directions [start-pos directions]
  (reduce apply-direction start-pos directions))

(defn move-and-multiply-location [directions]
  (let [{:keys [x y]} (apply-directions {:x 0 :y 0} directions)]
    (* x (- 0 y))))
