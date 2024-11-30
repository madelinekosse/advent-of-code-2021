(ns advent-2021.2021.day10
  (:require [clojure.string :as string]))

(def brackets {"{" {:type :curly :mode :open}
               "}" {:type :curly :mode :close}
               "(" {:type :paren :mode :open}
               ")" {:type :paren :mode :close}
               "[" {:type :square :mode :open}
               "]" {:type :square :mode :close}
               "<" {:type :chevron :mode :open}
               ">" {:type :chevron :mode :close}})

(defn- read-brackets [line]
  (mapv
   #(get brackets %)
   (string/split line #"")))

(defn- opposite [bracket]
  (update bracket :mode #(case % :open :close :close :open)))

(defn read-syntax-error [line]
  (let [elems (read-brackets line)]
    (loop [stack (list)
           to-process elems]
      (if (empty? to-process)
        {:stack stack}
        (let [thischar (first to-process)
              lastchar (peek stack)]
          (cond (nil? lastchar) (recur (conj stack thischar) (rest to-process))
                (= :open (:mode thischar)) (recur (conj stack thischar) (rest to-process))
                (= lastchar (opposite thischar)) (recur (pop stack) (rest to-process))
                :else {:unexpected thischar}))))))

(def error-scores {:paren 3
                   :square 57
                   :curly 1197
                   :chevron 25137})

(defn score-syntax-errors [lines]
  (->> lines
       (map read-syntax-error)
       (map :unexpected)
       (filter some?)
       (map :type)
       (map #(get error-scores %))
       (apply +)))

(defn complete-line [stack-remaining]
  (loop [s stack-remaining
         output []]
    (let [next (peek s)]
      (if next
        (recur (pop s) (conj output (opposite next)))
        output))))

(def completion-scores {:paren 1
                        :square 2
                        :curly 3
                        :chevron 4})

(defn score-completion [completion]
  (loop [chars (mapv :type completion)
         score 0]
    (if (empty? chars)
      score
      (let [this-char-score (->> chars first (get completion-scores))]
        (recur (rest chars) (+ (* score 5) this-char-score))))))

(defn- median [scores]
  (let [c (count scores)]
    (nth (sort scores) (quot c 2))))

(defn complete-and-find-middle-score [lines]
  (->> lines
       (map read-syntax-error)
       (map :stack)
       (filter some?)
       (map score-completion)
       median))
