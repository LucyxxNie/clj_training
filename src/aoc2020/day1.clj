(ns aoc2020.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn file->seq
  [filename]
  (-> filename
      (io/resource)
      (slurp)
      (str/split-lines)))





(comment

  (do (def sample-entries (file->seq "aoc2020/day1/sample-input.txt"))
      sample-entries)
  #_=> ["1721" "979" "366" "299" "675" "1456"]





  )
