(ns advent-2021.utils.input
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn lines [file]
  (let [f (->> file
               (str "input/")
               io/resource)]
    (with-open [reader (io/reader f)]
      (vec (line-seq reader)))))

(defn single-line [file]
  (let [file-lines (lines file)]
    (if (> (count file-lines) 1)
      (throw (ex-info "Expected single line in file" {:file file}))
      (first file-lines))))

(defn- strs->ints [strs]
  (mapv
   #(Integer/parseInt %)
   strs))

(defn file->int-vec [file]
  (->> file
       lines
       strs->ints))

(defn parse-delim-rows [lines delim]
  (->> lines
       (map #(string/split % delim))
       (map #(filter (partial not= "") %))
       (map #(map read-string %))
       (map vec)
       vec))

(defn parse-space-delim-rows [file]
  (parse-delim-rows (lines file) #"\s+"))

(defn parse-comma-delim-rows [file]
  (parse-delim-rows (lines file) #","))

(defn parse-matrix [file]
  (->> file
       lines
       (mapv #(string/split % #""))
       (mapv strs->ints)))

(defn parse-str-matrix [file]
  (->> file
      lines
      (mapv #(string/split % #""))))
