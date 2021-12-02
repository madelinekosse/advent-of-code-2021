(ns advent-2021.day2)

(def directions-p1 {'forward (fn [position distance] (update position :x + distance))
                    'up (fn [position distance] (update position :y + distance))
                    'down (fn [position distance] (update position :y - distance))})


(defn- apply-direction [direction-fns position [direction distance]]
  (let [update-fn (get direction-fns direction)]
    (update-fn position distance)))

(defn apply-directions [direction-fns start-pos directions]
  (reduce (partial apply-direction direction-fns) start-pos directions))

(defn move-and-multiply-location-p1 [directions]
  (let [{:keys [x y]} (apply-directions directions-p1 {:x 0 :y 0} directions)]
    (* x (- 0 y))))


(def directions-p2 {'forward (fn [{:keys [aim] :as position} x] (-> position
                                                                    (update :x-pos + x)
                                                                    (update :depth + (* aim x))))
                    'up (fn [position x] (update position :aim - x))
                    'down (fn [position x] (update position :aim + x))})

(defn move-and-multiply-location-p2 [directions]
  (let [{:keys [x-pos depth]} (apply-directions directions-p2 {:x-pos 0 :depth 0 :aim 0} directions)]
    (* x-pos depth)))
