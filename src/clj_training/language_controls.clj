(ns clj-training.language-controls)

;let bingding

;everything defined in let are local
(let [car 5] (str "I have " car " cars"))
(let [+ -] (+ 2 3))
(let [person "joseph"
      age 34
      num-cats 100]
  (str person " has " num-cats " at age of " age))
(let [num 239
      num-multi2 (* 2 num)]
  (str "2 times " num " is " num-multi2))

;function

((fn [x] (+ x 1)) 2)
(#(+ % 1) 2)

;vars

; symbol -> var -> function

(def cat 5)
(def dog 10)
(def dogName ["Oreo", "Liz"])

;!!redefine vars in clj is dangerous! because changing name everywhere, including other functions running with it!!!
(def var 5)
(def var 6)                                                 ;!!!DO NOT redefine THIS!

;defining function

;below 2 are the same, defn is short for def + fn
(def half (fn [num] (/ num 2)))
(defn half2 [num] (/ num 2))

(defn catStr [] "I have cat")                               ;an argument is not necessary for function

(defn twoTimes
  ([] 2)
  ([x] (* x 2)))                                            ;handle multiple arity, like overload function

(defn add
  [x y]
  (+ x y))                                                  ;pass multiple arguments

(defn varges
  [x y & more-agrs]
  {:x x :y y :more more-agrs})                              ;pass any number of arguments (x and y are mandatory)

(defn func
  "This is an example of docstring"
  [lang]
  (str "We can add parameter " lang))                       ;you can insert doc string to explain the function

;doc tells the full name of the function, the arguments it accepts, and its docstring

