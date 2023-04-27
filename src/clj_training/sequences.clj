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

;Threading macros

;below 2 function are the same

(-
  (/
    (+ (* x 2) 7)
    5)
  8)

(-> x
    (* 2)
    (+ 7)
    (/ 5)
    (- 8))