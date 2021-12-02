(ns advent-2021.utils.input
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- lines [file]
  (let [f (->> file
               (str "input/")
               io/resource)]
    (with-open [reader (io/reader f)]
      (vec (line-seq reader)))))

(defn file->int-vec [file]
  (->> file
       lines
       (map #(Integer/parseInt %))))

(defn parse-space-delim-rows [file]
  (->> file
       lines
       (map #(string/split % #" "))
       (map #(map read-string %))))
