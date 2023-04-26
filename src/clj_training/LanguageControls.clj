(ns clj-training.LanguageControls)

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
