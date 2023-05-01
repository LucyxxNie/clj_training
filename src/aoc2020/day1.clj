(ns aoc2020.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn file->seq
  [filename]
  (-> filename
      (io/resource)
      (slurp)
      (str/split-lines)))
;;Q1: find 2 numbers in the file that would sum up to be 2020, and find the product of these 2 numbers.

;Approach 1: brute force
;loop through the entries and use 2020 to minus each number, and loop through the rest of the vectors to find the
;if the (2020 - num) exist. Time: O(n^2) space: O(1)


;Approach 2: binary search
;sort the vector in ascending order, and use 2 pointers pointing to head and end of the vector, and move pointers
;based on the sum of 2 numbers. Time: O(log n) space: O(1)

(defn binary-search
  [data-set]
  (->> map (fn [left-pointer
                right-pointer]
             ((if (== 2020 (+ (get data-set left-pointer)
                              (get data-set right-pointer)))

                )))
       (range 0 (-> data-set count))
       (range (-> data-set count) 0 -1))
  )


(comment
  (def entries (file->seq "aoc2020/day1/input.txt"))
  #_=>
  ["1348"
   "1621"
   "1500"
   "1818"
   "1266"
   ...]

  (def sorted-entries (->> entries
                           (map (fn [string] (Integer/parseInt string)))
                           (sort)))
  #_=>
  (183
    455
    470
    651
    667
    695
    934
    974
    988
    ...)


  )




