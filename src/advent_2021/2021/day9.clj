(ns advent-2021.2021.day9)

(defn- adjacent [grid [row col]]
  (filter
   #(some? (get-in grid %))
   [[row (inc col)] [row (dec col)] [(inc row) col] [(dec row) col]]))

(defn- low? [grid [row col]]
  (let [val (get-in grid [row col])
        neighbours (adjacent grid [row col])]
    (every?
     #(< val (get-in grid %))
     neighbours)))
(defn- all-coords [grid]
  (for [row (range (count grid))
        col (range (count (first grid)))]
    (vector row col)))

(defn low-points [grid]
  (filter (partial low? grid) (all-coords grid)))

(defn total-risk-level [grid]
  (->> grid
       low-points
       (map #(get-in grid %))
       (map inc)
       (apply +)))

(defn- drains-to [grid position]
  (let [val (get-in grid position)]
    (first (filter
            #(< (get-in grid %) val)
            (adjacent grid position)))))

(defn- drains-to-low [grid position]
  (loop [current position]
    (cond
      (= 9 (get-in grid current)) nil
      (low? grid current) current
      :else (recur (drains-to grid current)))))

(defn find-basins [grid]
  (->> grid
       all-coords
       (map (partial drains-to-low grid))
       (filter some?)
       frequencies))

(defn find-and-multiply-largest-basins [grid]
  (->> grid
       find-basins
       vals
       sort
       (take-last 3)
       (apply *)))
