(ns clj-training.sequences)

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



  )

