(ns advent-2021.day3)

(defn split-digits [binary-string]
  (map #(Character/digit % 2) binary-string))

(defn gamma-digits [binary-strings]
  (let [len (count (first binary-strings))]
    (if (every? #(= (count %) len) binary-strings)
      (->> binary-strings
           (map split-digits)
           (reduce (fn [agg digits]
                     (map-indexed
                      (fn [i counts]
                        (update counts (nth digits i) inc))
                      agg))
                   (repeat len {0 0 1 0}))
           (map (fn [lookup] (->> lookup
                                  seq
                                  (sort-by last)
                                  last
                                  first))))
      (throw (ex-info "All inputs must be same length" {})))))

(defn epsilon [gamma]
  (map #(case % 0 1 1 0) gamma))

(defn binary-digits->int [digit-vec]
  (Integer/parseInt
   (apply str digit-vec)
   2))

(defn power-consumption [report]
  (let [gamma (gamma-digits report)
        epsilon (epsilon gamma)]
    (* (binary-digits->int gamma)
       (binary-digits->int epsilon))))
