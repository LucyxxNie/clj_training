(ns clj-training.clj-cheat-sheet
  (:require [clojure.string :as str])
  (:require [clojure.set :as set]))

(comment

  ;;---------------------------------random--------------------------------
  (compare "abc" "cba")
  (abs -1)
  (str/lower-case "ABC XYZ")
  (str/trim "    a ")
  (str/starts-with? "abc" "b")

  ;;---------------------------------list--------------------------------
  (.indexOf '(20 20 90) 20)
  #_=> 0
  (.lastIndexOf '(20 20 20) 20)
  #_=> 2
  (cons 1 '(2 3 4))
  #_=> (1 2 3 4)
  (conj '(1 2 3) 4)
  #_=> (4 1 2 3)
  (peek '(10 20 30))                                        ;for list, it returns first. For vectors, it returns last. Return nil if empty
  #_=> 10
  (peek '())
  #_=> nil
  (pop '(1 2 3))
  #_=> (2 3)

  ;;---------------------------------vector--------------------------------
  (conj [1 2 3] 4)
  #_=> [1 2 3 4]
  (peek [1 2 3 4])
  #_=> 4
  (pop [1 2 3 4])
  #_=> [1 2 3]

  (mapv + [1 2 3] [4 5 6])
  #_=> [5 7 9]
  (mapv inc [1 2 3 4])
  #_=> [2 3 4 5]

  (.indexOf [10 20 20 30] 20)
  #_=> 1
  (.lastIndexOf [10 20 30 40 20] 20)
  #_=> 4

  (assoc [1 2 3 4] 0 "1")
  #_=> ["1" 2 3 4]
  (assoc [1 2 3 4] 4 "1")
  #_=> [1 2 3 4 "1"]

  (def users [{:name "James" :age 33} {:name "John" :age 42}])
  (assoc-in users [1 :age] 22)
  #_=> [{:name "James", :age 33} {:name "John", :age 22}]

  (update-in users [1 :age] - 20)
  #_=> [{:name "James", :age 33} {:name "John", :age 22}]

  (update {:name "John", :age 22} :age + 5)
  #_=> {:name "John", :age 27}

  (subvec [1 2 3 4 5] 3)
  #_=> [4 5]
  (subvec [1 2 3 4 5 6] 2 5)
  #_=> [3 4 5]

  (replace {:2 3, :3 4} [:2 :4])
  #_=> [3 :4]

  (replace [1 2 3 4 5] [0 3])
  #_=> [3 4]

  ;;-------------------------Sets--------------------------------------
  (conj #{1 2 3} 2)

  (disj #{1 2 3} 2)

  (set/union #{1 2 3} #{2 3 4 5})
  #_=> #{1 4 3 2 5}

  ;;-------------------------Map--------------------------------------
  (array-map [:a 1] [:b 2])

  (zipmap [:a :b :c] [1 2 3])
  #_=> {:a 1, :b 2, :c 3}

  (group-by count ["abc" "csd" "abds"])
  #_=> {3 ["abc" "csd"], 4 ["abds"]}

  (group-by :user-id  [{:user-id 1 :uri "/"}
                         {:user-id 2 :uri "/foo"}
                         {:user-id 1 :uri "/account"}])
  #_=> {1 [{:user-id 1, :uri "/"} {:user-id 1, :uri "/account"}], 2 [{:user-id 2, :uri "/foo"}]}


  )