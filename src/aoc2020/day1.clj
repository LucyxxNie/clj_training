(ns aoc2020.day1
  (:require [aoc2020.util :refer [file->seq]]))

(defn complement
  [sum x]
  (- sum x))


(defn find-complement
  [data-set sum]
  (let [compl-exist?  (fn [num]
                        (contains? data-set (complement sum num)))
        result        (filterv compl-exist? data-set)]

    (when (first result)
      result)))

(defn two-sum-product
  [data-s sum]
  (let [data-set     (into #{} data-s)
        result       (find-complement data-set sum)
        num1         (result 0)
        num2         (result 1)]
    (* num1 num2)))


(defn compl-product
  [data-s sum num]
  (let [data-set     (into #{} data-s)
        compl        (complement sum num)
        result       (find-complement data-set compl)
        num2         (get result 0)
        num3         (get result 1)]

    (when (and num2 num3)
      (* num num2 num3))))


(defn three-sum-product
  [data-s sum]
  (->> (mapv
         (fn [x] (compl-product data-s sum x))
         data-s)
       (filterv int?)
       (first)))


(comment
  ;;------------------------function evaluation---------------------------

  (complement 2020 20)
  #_=> 2000
  (mapv (partial complement 2020) [1 6 5 23 90 3])
  #_=> [2019 2014 2015 1997 1930 2017]
  (mapv (partial complement 2020) sample-entry-s)
  #_=> [299 1041 1654 1721 1345 564]


  (find-complement #{1 6 5 23 90 3} 29)
  #_=> [6 23]
  (find-complement (into #{} sample-entry-s) 2020)
  #_=> [299 1721]
  (find-complement (into #{} entries) 2020)
  #_=> [1565 455]


  (two-sum-product [1 6 5 23 90 3] 29)
  #_=> 138
  (two-sum-product sample-entry-s 2020)
  #_=> 514579
  (two-sum-product entries 2020)
  #_=> 712075


  (three-sum-product [1 6 5 23 90 3] 118)
  #_=> 10350
  (three-sum-product sample-entry-s 2020)
  #_=> 241861950
  (three-sum-product entries 2020)
  #_=> 145245270


;------------------------------data----------------------------------------

  (do (def sample-entry-s (->> (file->seq "aoc2020/day1/input-sample.txt")
                               (mapv parse-long)))
      sample-entry-s)
  #_=> [1721 979 366 299 675 1456]

  (do (def entries (->> (file->seq "aoc2020/day1/input.txt")
                        (mapv parse-long)))
      entries)
  #_=> [1348
        1621
        1500
        1818
        1266
        1449,,,]



  )




