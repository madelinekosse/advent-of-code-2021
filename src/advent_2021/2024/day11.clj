(ns advent-2021.2024.day11
  (:require [advent-2021.utils.input :as input]
            [clojure.string :as string]))


(defn- split-in-half [stone]
  (let [half (quot (count stone) 2)
        p1 (subs stone 0 half)
        p2 (string/replace (subs stone half) #"^0+" "")]
    (lazy-seq [p1 (if (empty? p2) "0" p2)])))


(defn blink-at [stone]
  (cond
    (= stone "0") ["1"]
    (even? (count stone)) (split-in-half stone)
    :else (conj (lazy-seq []) (str (* 2024 (Long/parseLong stone))))))

(defn blink-and-count-one-stone [stone num-times]
  (loop [stones (lazy-seq [stone])
         n num-times]
    (if (= 0 n)
      (count stones)
      (recur (mapcat blink-at stones) (dec n)))))


(defn sidelong-reduce
  "Totally copied this from someone smarter:
  https://jeffterrell.tech/posts/2015-02-24-lazy-reduce-in-clojure/

  Do a lazy reduce, returning the original (lazy) sequence,
  and passing the final accumulator value to `store-result!`
  when the sequence is fully realized."
  [seq f accum store-result!]
  (lazy-seq
   (if (empty? seq)
     (do (store-result! accum) nil)
     (let [[fst & rst] seq]
       (cons fst
             (sidelong-reduce rst f (f accum fst) store-result!))))))

(defn sidelong-sum
  [nums result-atom]
  (sidelong-reduce nums + 0 #(reset! result-atom %)))

(defn blink-n-times-and-count [stones num-times]
  (let [lazy-results (map
                      #(blink-and-count-one-stone % num-times)
                      (lazy-seq stones))
        sum-atom (atom nil)]
    (dorun (sidelong-sum lazy-results sum-atom))
    @sum-atom
  ))


(defn part-1 [stones]
  (blink-n-times-and-count stones 25))


(defn read-file [filename]
  (string/split (input/single-line filename) #"\s"))

