(ns clj-training.Practice3-problem-decomposition
  (:require [aoc2020.util :refer [file->seq]]))

(defn parse-int
  [entry-str]
  (Integer/parseInt entry-str))


;;Q1 part 1: find 2 numbers in the file that would sum up to be 2020,
;; and find the product of these 2 numbers.

(defn complement
  [x sum]
  (- x sum))

(def complement-2020
  (partial complement 2020))

;Approach 1: brute force
;sort the vector in ascending order, and use 2 pointers pointing to the head and the end of the vector,
;and move pointers based on the sum of 2 numbers.
; Time: O(n^2) space: O(1)

(defn two-sum
  [data-set]
  (first (for [left-pointer  (range 0 (-> data-set count))
               right-pointer (range (- (-> data-set count) 1) 0 -1)
               :let [num1 (data-set left-pointer)
                     num2 (data-set right-pointer)]
               :when (= 2020 (+ num1 num2))]
           #_[num1 num2]
           (* num1 num2)
           )))

#_(defn pointer-adjustment
    [target-sum cur-sum
     left-pointer right-pointer]
    (cond
      (< target-sum cur-sum) (- right-pointer 1)
      (> target-sum cur-sum) (+ left-pointer 1)))


;Approach 2: set
;use a set to store the numbers, and loop through the numbers and find if (2020 - nums) exists in
;set or not. Return the product if they exist.
;Time:O(n) Space:O(n)

(defn two-sum?
  [sum n1 n2]
  (= sum
     (+ n1 n2)))

(def two-sum?-2020 (partial two-sum? 2020))

;Using for-loop
(defn two-sum-set
  [data-set]
  (let [set (into #{} data-set)]
    (first (for [pointer (range 0 (-> data-set count))
                 :let [num (data-set pointer)]
                 :when (->> num
                            (- 2020)
                            (contains? set))]
             (* num (- 2020 num))
             )))
  )

;not-using for loop
(defn entries-set
  [data-set]
  (into #{} data-set))

(defn two-sum-set
  [num data]
  (let [compel      (complement-2020 num)
        two-sum-s   (contains? (entries-set data) compel)
        compel-num  (filter two-sum-s (entries-set data))]
    (* num compel-num)))


;;Q1 part 2: find 3 numbers sum up to 2020, and find product of them.

;;Approach 1: brute force
;;Time: O(n^3) space: O(1)
(defn three-sum
  [data-set]
  (first (for [pointer1 (range 0 (- (-> data-set count) 2))
               pointer2 (range (+ 1 pointer1) (- (-> data-set count) 1))
               pointer3 (range (+ 1 pointer2) (-> data-set count))
               :when (-> 2020
                         (- (data-set pointer1))
                         (- (data-set pointer2))
                         (- (data-set pointer3))
                         (zero?))]
           (* (data-set pointer1) (data-set pointer2) (data-set pointer3))
           )))

;;Approach 2: map
;; use map to store the value, and after fixing 2 pointers, use the set to find (2020 - num1 - num2)
;exist
;;Time: O(n^2) Space: O(n)
(defn three-sum-map
  [data-set]
  (let [map (frequencies data-set)]
    (first (for [pointer1 (range 0 (- (-> data-set count)
                                      2))
                 pointer2 (range (+ pointer1 1) (- (-> data-set count) 1))
                 :let [num1 (data-set pointer1)
                       num2 (data-set pointer2)]
                 :when (->> num2
                            (+ num1)
                            (- 2020)
                            (contains? map))]
             (* num1 num2 (- 2020 num1 num2))))))


;Rich comment block
(comment

  (do (def sample-entry-s (->> (file->seq "aoc2020/day1/input-sample.txt")
                               (mapv parse-int)))
      sample-entry-s)
  #_=> [1721 979 366 299 675 1456]

  (do (def entries (file->seq "aoc2020/day1/input.txt"))
      entries)
  #_=> ["1348"
        "1621"
        "1500"
        "1818"
        "1266",,,]


  (do (def entries-s (->> entries
                          (map (fn [string] (Integer/parseInt string)))
                          #_(sort)
                          (into []))) entries-s)
  #_=> [1348
        1621
        1500
        1818
        1266
        1449,,,]

  (two-sum entries-s)
  #_=> 712075

  (two-sum-set entries-s)
  #_=> 712075

  (three-sum entries-s)
  #_=> 145245270

  (three-sum-map entries-s)
  #_=> 145245270




  ;; decode/parse
  ;; Create complements
  ;; Filter against complements

  (mapv parse-int ["2020" "0"])
  #_=> 2020

  (mapv complement-2020 [1 2000 2030])
  #_=> [2019 20 -10]
  (contains? (entries-set entries-s) 2000)

  (-> (->> 20
           (complement-2020)
           (contains? (entries-set entries-s)))
      (filter (entries-set entries-s)))

  (def sample #{2000 20 1000 100})


  (filter (fn [x] (contains? sample (- 2020 x))) sample)
  (func3 100)



  (let [compl      (complements 2020 sample-entry-s)
        two-sums-s (filterv sample-entry-s compl)]
    two-sum-s
    )

  (mapv
    (two-sum-set x entries-s)
    entries-s)
  )







