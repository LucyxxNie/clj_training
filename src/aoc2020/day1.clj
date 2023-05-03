(ns aoc2020.day1
  (:require [aoc2020.util :refer [file->seq]]))

(defn parse-int
  [entry-str]
  (Integer/parseInt entry-str))

(defn complement
  [sum x]
  (- sum x))

(def complement-2020
  (partial complement 2020))

(defn two-sum
  [data]
  (let [set                 (into #{} data)
        compl-exist?        (fn [x] (contains? set (complement-2020 x)))
        result              (filterv compl-exist? set)]

    (* (result 0) (result 1))
    ))


(defn three-sum-step1
  [data num]
  (let [set                 (into #{} data)
        compl               (complement-2020 num)
        compl-exist?        (fn [x] (contains? set (complement compl x)))
        result              (filterv compl-exist? set)]

    (if (first result)
      (* num (result 0) (result 1))
      nil)
    ))

(defn three-sum-step2
  [data]
  (->> (mapv
         (fn [x] (three-sum-step1 data x))
         data)
       (filterv (fn [n] n))
       (first)))


(comment

  (do (def sample-entry-s (->> (file->seq "aoc2020/day1/input-sample.txt")
                               (mapv parse-int)))
      sample-entry-s)
  #_=> [1721 979 366 299 675 1456]

  (do (def entries (->> (file->seq "aoc2020/day1/input.txt")
                        (mapv parse-int)))
      entries)
  #_=> [1348
        1621
        1500
        1818
        1266
        1449,,,]

  (two-sum entries 1710)
  #_=> 712075

  (three-sum-step2 entries)
  #_=> 145245270







  )




