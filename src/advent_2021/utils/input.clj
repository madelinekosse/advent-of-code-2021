(ns advent-2021.utils.input
  (:require [clojure.java.io :as io]))

(defn file->int-vec [file]
  (let [f (->> file
               (str "input/")
               io/resource)
        lines (with-open [reader (io/reader f)]
                (vec (line-seq reader)))]
    (map #(Integer/parseInt %) lines)))
