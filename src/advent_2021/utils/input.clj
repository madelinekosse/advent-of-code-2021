(ns advent-2021.utils.input
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn lines [file]
  (let [f (->> file
               (str "input/")
               io/resource)]
    (with-open [reader (io/reader f)]
      (vec (line-seq reader)))))

(defn- strs->ints [strs]
  (mapv
   #(Integer/parseInt %)
   strs))

(defn file->int-vec [file]
  (->> file
       lines
       strs->ints))

(defn- parse-delim-rows [file delim]
  (->> file
       lines
       (map #(string/split % delim))
       (map #(filter (partial not= "") %))
       (map #(map read-string %))
       (map vec)
       vec))

(defn parse-space-delim-rows [file]
  (parse-delim-rows file #"\s+"))

(defn parse-comma-delim-rows [file]
  (parse-delim-rows file #","))

(defn parse-matrix [file]
  (->> file
      lines
      (mapv #(string/split % #""))
      (mapv strs->ints)))
