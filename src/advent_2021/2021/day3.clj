(ns advent-2021.2021.day3)

(defn split-digits [binary-string]
  (map #(Character/digit % 2) binary-string))

(defn- count-digits [length digit-vecs]
  (let [init (repeat length {0 0 1 0})]
    (reduce (fn [agg digits]
              (map-indexed
               (fn [i counts]
                 (update counts (nth digits i) inc))
               agg))
            init
            digit-vecs)))

(defn- most-common-digit [count-lookup]
  (->> count-lookup
       seq
       ;; sort by the digit, so 1 gets priority if count is the same
       (sort-by first)
       (sort-by last)
       last
       first))

(defn gamma-digits [binary-strings]
  (let [len (count (first binary-strings))]
    (if (every? #(= (count %) len) binary-strings)
      (->> binary-strings
           (map split-digits)
           (count-digits len)
           (map most-common-digit))
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

(defn- filter-for-rating [digit-vecs use-least-common?]
  (loop [remaining digit-vecs
         digit-index 0]
    (if (= (count remaining) 1)
      (first remaining)
      (let [counts (count-digits (count (first remaining)) remaining)
            most-common-nth-digit (most-common-digit (nth counts digit-index))
            filter-digit (if use-least-common? (case most-common-nth-digit 0 1 1 0) most-common-nth-digit)]
        (recur
         (filter #(= (nth % digit-index) filter-digit) remaining)
         (inc digit-index))))))

(defn life-support-rating [report]
  (let [digit-vecs (map split-digits report)
        oxygen (filter-for-rating digit-vecs false)
        co2-scrubber (filter-for-rating digit-vecs true)]
    (* (binary-digits->int co2-scrubber)
       (binary-digits->int oxygen))))
