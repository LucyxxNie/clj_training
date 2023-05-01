(ns clj-training.sequences
  (:require [clojure.string :as str]))



;;Recursion
(defn fact [n]
  (if (== n 1)
    1
    (* n (fact (dec n)))))

(defn fact2
  ([n] (fact2 n 1))
  ([n f]
   (if (= n 1)
     f
     (fact2 (dec n) (* f n)))))

;below function would accept vector and return a vec with first element add 1
;but could be problematic if the vector is empty
(defn inc-first [nums]
  (cons (inc (first nums))
        (rest nums)))

;below function can handle if vector is empty
(defn inc-first-empty [nums]
  (if (first nums)
    (cons (inc (first nums))
          (rest nums))
    (list)))


(defn inc-more
  "This function accepts list and increment every element is the list."
  [nums]
  (if (first nums)
    (cons (inc (first nums))
          (inc-more (rest nums)))
    (list)))

(defn transform-all [f xs]
  "The function is same as inc-more, but take inc as a parameter as f."
  (if (first xs)
    (cons (f (first xs))
          (transform-all f (rest xs)))
    (list)))

;;To increment each element in vector, we can simply use map
(map inc '(1 2 3))
(map inc [1 2 3])

(take 10 (iterate inc 1))

(take 10 (repeat :a))
(repeat 5 :a)

(take 3 (repeatedly rand))
(repeatedly 3 rand)

(range 5)
(range 5 10)
(range 5 70 10)

(take 10 (cycle [1 2 3]))

(map + [1 2 3] [2 3 4])
(map-indexed (fn [index element] (str index element))
             [:a :b :c])
(map-indexed str [:a :b :c])

;;combining 2 seq
(concat [1 2 3] [:a :b])

(interleave [:a :b :c] [1 2 3])

(interpose :a [1 2 3 4])
(reverse [1 2 3])
(reverse "hello")
(apply str (reverse "hello"))
(shuffle ["a" "b" "c"])

;;subsequence
(take 3 (range 10))
(drop 3 (range 10))
(take-last 3 (range 10))
(drop-last 3 (range 10))
(take-while pos? [3 2 1 -10 -20 10])

(filter pos? [1 2 3 4 -6 -70 20 10])
(remove string? [:a :b :c "d" :e])
(partition 2 [:cats 5 :bats 27 :crocodiles 0])
(partition-by neg? [1 2 3 2 1 -1 -2 -3 -2 -1 1 2])

;;collapsing sequences
(frequencies [:a :a :a :b :b :c])

(pprint (group-by :first [{:first "Li" :last "Zhou"}
                          {:first "Sarah" :last "Lee"}
                          {:first "Sarah" :last "Dunn"}
                          {:first "Li" :last "O'Toole"}]))
(reduce + [1 2 3 4])
(reductions + [1 2 3 4])
(into {} [[:a 2] [:b 1]])
(into (list) [1 2 3 4])
(into #{} [1 2 2 3 3 4])











;Threading macros

;below 2 function are the same

#_(-
    (/
      (+ (* x 2) 7)
      5)
    8)

#_(-> x
      (* 2)
      (+ 7)
      (/ 5)
      (- 8))


;;Rich Comment Block
(comment

  (fact 2)
  #_=> 2

  (fact 3)
  #_=> 6

  (fact 5)
  #_=> 120

  (inc-first [1 2 3 4])
  #_=> (2 2 3 4)

  ;the function can handle empty vector
  (inc-first-empty [])
  #_=> ()

  (inc-more [1 2 3 4 5])
  #_=> (2 3 4 5 6)


  (doc inc-more [1 2])
  ;;Syntax error!!!

  (type inc-more)
  #_=> clj_training.sequences$inc_more

  (transform-all inc [1 2 3])
  #_=> (2 3 4)
  (transform-all keyword ["a" "b"])
  #_=> (:a :b)
  (transform-all list [:a :b "c"])
  #_=> ((:a) (:b) ("c"))


  ;;quiz

  (mapv (fn [x] (+ 2 x)) [1 2 3 4])
  #_=> [3 4 5 6]

  (repeat 3 [1 2 3])
  #_=> ([1 2 3] [1 2 3] [1 2 3])

  (map + [1 2 3] [4 5 6] [7 8 9])
  #_=> (12 15 18)

  (map-indexed (fn [index x] (str index ". " x)) [:a :b :c])
  #_=> ("0. :a" "1. :b" "2. :c")

  (into [] (concat [1 2 3] [:a :b :c] '(4 5 6)))
  #_=> [1 2 3 :a :b :c 4 5 6]

  (vec (take-while pos? [1 -1 2 -2]))
  #_=> [1]

  (split-with number? [1 2 3 "a" 4 5 6])
  #_=> [(1 2 3) ("a" 4 5 6)]

  (partition-all 2 [1 2 3 4 5])
  #_=> ((1 2) (3 4) (5))

  (group-by pos? [1 2 -1 -2])
  #_=> {true [1 2], false [-1 -2]}

  (apply str [1 2 3 4])
  #_=> "1234"




  )

