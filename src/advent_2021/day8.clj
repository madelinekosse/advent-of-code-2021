(ns advent-2021.day8
  (:require [clojure.string :as string]
            [advent-2021.utils.input :as i]
            [clojure.set :as set]))

(defn- parse-line [[signals digits]]
  (let [split-words (fn [s] (string/split s #"\s"))]
    {:signals (split-words signals)
     :digits (split-words digits)}))

(defn parse-input [file]
  (->> file
       i/lines
       (map #(string/split % #"\s\|\s"))
       (map parse-line)))

(defn- filter-easy-digits [digit-strs]
  (filter #(contains? #{2 4 3 7} (count %))
          digit-strs))

(defn count-easy-digits [displays]
  (->> displays
       (map :digits)
       (map filter-easy-digits)
       (apply concat)
       count))

(def easy-digits {1 #{\c \f}
                  4 #{\b \c \d \f}
                  7 #{\a \c \f}
                  8 #{\a \b \c \d \e \f \g}})

(defn easy-digits-lookup [signals]
  (let [filtered (->> signals
                      filter-easy-digits
                      (map set))]
    (reduce-kv (fn [m k v]
                 (assoc m k (first (filter #(= (count %) (count v)) filtered))))
               {}
               easy-digits)))

(defn add-nine [{:keys [digit-lookup character-lookup]} signals]
  (let [a-wire (first (set/difference (get digit-lookup 7) (get digit-lookup 1)))
        nine-without-g (conj (get digit-lookup 4)
                             a-wire)
        nine-sig (first (filter #(and (= 6 (count %))
                                      (set/subset? nine-without-g %)) signals))
        g-wire (first (set/difference nine-sig nine-without-g))
        e-wire (first (set/difference (get digit-lookup 8) nine-sig))]
    {:digit-lookup (assoc digit-lookup 9 nine-sig)
     :character-lookup (merge character-lookup {\a a-wire \g g-wire \e e-wire})}))

(defn add-zero-and-six [{:keys [digit-lookup character-lookup]} signals]
  (let [zero-and-six (filter (fn [s] (and (= 6 (count s))
                                          (not= s (get digit-lookup 9)))) signals)
        zero (first (filter (fn [s] (set/subset? (get digit-lookup 7) s)) zero-and-six))
        six (first (filter (partial not= zero) zero-and-six))
        d-wire (first (set/difference (get digit-lookup 8) zero))
        c-wire (first (set/difference (get digit-lookup 8) six))]
    {:digit-lookup (merge digit-lookup {0 zero 6 six})
     :character-lookup (merge character-lookup {\d d-wire \c c-wire})}))

(defn finish-digit-lookup [{:keys [digit-lookup character-lookup]} signals]
  (let [two-three-five (filter #(not (contains? (set (vals digit-lookup)) %)) signals)
        five (first (filter #(not (contains? % (get character-lookup \c))) two-three-five))
        three (first (filter #(and (not= % five)
                                   (not (contains? % (get character-lookup \e)))) two-three-five))
        two (first (filter #(and (not= % five)
                                 (not= % three)) two-three-five))]
    (merge digit-lookup {5 five 3 three 2 two})))

(defn decode-display [{:keys [signals digits]}]
  (let [sig-sets (map set signals)
        lookup (-> {:digit-lookup (easy-digits-lookup signals)
                    :character-lookup {}}
                   (add-nine sig-sets)
                   (add-zero-and-six sig-sets)
                   (finish-digit-lookup sig-sets)
                   (set/map-invert))]
    (->> digits
         (map set)
         (map #(get lookup %))
         (apply str)
         Integer/parseInt)))

(defn sum-digits [displays]
  (->> displays
       (map decode-display)
       (apply +)))
