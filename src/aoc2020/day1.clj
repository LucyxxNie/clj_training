(ns aoc2020.day1
  (:require [aoc2020.util :refer [file->seq]]))

(defn find-complement
  [sum x]
  (- sum x))

(defn compl-exist?
  [num sum data-set]
  (contains? data-set (find-complement sum num)))

(defn filter-compl-in-dataset
  [sum data-set]
  (let [compl-exist? (fn [x] (compl-exist? x sum data-set))
        result       (filterv compl-exist? data-set)
        found?       (first result)]

    (when found?
      result)))

(defn two-sum-product
  [sum data-s]
  (let [data-set (into #{} data-s)
        result   (filter-compl-in-dataset sum data-set)
        num1     (get result 0)
        num2     (get result 1)]
    (* num1 num2)))


(defn compl-product
  [num sum data-s]
  (let [data-set   (into #{} data-s)
        compl      (find-complement sum num)
        result     (filter-compl-in-dataset compl data-set)
        found?     (first result)
        compl-data (take 2 result)]

    (when found?
      (reduce * num compl-data))))


(defn three-sum-product
  [sum data-s]
  (->> (mapv
         (fn [x] (compl-product x sum data-s))
         data-s)
    (filterv int?)
    (first)))


(comment
  ;------------------------------data----------------------------------------

  (do (def sample-entry-s (->> (file->seq "aoc2020/day1/input-sample.txt")
                            (mapv parse-long)))
    sample-entry-s)
  #_=> [1721 979 366 299 675 1456]

  (do (def entry-s (->> (file->seq "aoc2020/day1/input.txt")
                     (mapv parse-long)))
    entry-s)
  #_=> [1348
        1621
        1500
        1818
        1266
        1449,,,]

  ;;------------------------function evaluation---------------------------

  (mapv (partial find-complement 20) [1 6 5 23 90 3 -1])
  #_=> [19 14 15 -3 -70 17 21]


  (compl-exist? 2 10 #{8 20 30 100})
  #_=> true
  (compl-exist? 2 10 #{10 20 30 100})
  #_=> false


  (filter-compl-in-dataset 29 #{9 10 20 30 40})
  #_=> [20 9]
  (filter-compl-in-dataset 29 #{9 17 20 12 40})
  #_=> [20 9 17 12]
  (filter-compl-in-dataset 2020 #{10 20 30 100})
  #_=> nil


  (two-sum-product 29 [9 10 20 30 40])
  #_=> 180
  (two-sum-product 89 [1 6 5 23 90 3 -1])
  #_=> -90


  (two-sum-product 2020 sample-entry-s)
  #_=> 514579
  (two-sum-product 2020 entry-s)
  #_=> 712075


  (three-sum-product 118 [1 6 5 23 90 3])
  #_=> 10350
  (three-sum-product 39 [9 10 20 30 40])
  #_=> 1800


  (three-sum-product 2020 sample-entry-s)
  #_=> 241861950
  (three-sum-product 2020 entry-s)
  #_=> 145245270

  )




