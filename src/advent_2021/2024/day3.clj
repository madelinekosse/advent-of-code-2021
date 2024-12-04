(ns advent-2021.2024.day3)

(defn matches [instr]
  (vec (re-seq #"mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)" instr)))

(defn multiply-match [match]
  (->> match
       (re-find #"mul\((\d{1,3}),(\d{1,3})\)")
       rest
       (map #(Integer/parseInt %))
       (apply *)))

(defn- parse [file-lines]
  (flatten (map matches file-lines)))

(defn part-1 [file-lines]
  (->> file-lines
       parse
       (map multiply-match)
       (apply +)))

(defn part-2 [file-lines]
  (loop [instructions (parse file-lines)
         enabled? true
         sum 0]
    (cond
      (empty? instructions) sum
      (= "do()" (first instructions)) (recur (rest instructions)
                                             true
                                             sum)
      (= "don't()" (first instructions)) (recur (rest instructions)
                                                false
                                                sum)
      (false? enabled?) (recur (rest instructions)
                               false
                               sum)
      :else (recur (rest instructions)
                   enabled?
                   (+ sum (multiply-match (first instructions)))))))
