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

;Approach 1: brute force (optimized)
;sort the vector in ascending order, and use 2 pointers pointing to the head and the end of the vector,
;and move pointers based on the sum of 2 numbers.
; Time: O(n) space: O(1)

(defn search
  [data-set]
  (first (for [left-pointer  (range 0 (-> data-set count))
               right-pointer (range (- (-> data-set count) 1) 0 -1)
               :let   [num1 (data-set left-pointer)
                      num2 (data-set right-pointer)]
               :when (= 2020 (+ num1 num2))]
           #_[num1 num2]
           (* num1 num2)
           )))

;Approach 2: set
;use a set to store the numbers, and loop through the numbers and find if (2020 - nums) exists in
;set or not. Return the product if they exist.
;Time:O(n) Space:O(n)

(defn set-search
  [data-set]
  (let [set (into #{} data-set)]
    (first (for [pointer  (range 0 (-> data-set count))
                 :let     [num (data-set pointer)]
                 :when    (->> num
                              (- 2020)
                              (contains? set))]
             (* num (- 2020 num))
             )))
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
                           (sort)
                           (into [])))
  #_=>
    [183
     455
     470
     651
     667
     695
     934
     974
     988
     ...]

  (into #{} [1 2 3] )

  (search sorted-entries)
  #_=> 712075

  (set-search sorted-entries)
  #_=> 712075




  )




