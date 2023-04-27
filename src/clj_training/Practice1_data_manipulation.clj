(ns clj-training.Practice1-data-manipulation)

(defn awards
  [person]
  (:awards person))

(defn person->awards
  [person]
  (:awards person))


(def person-info
  {:name   "Amelia Earhart"
   :birth  1897
   :death  1939
   :awards {"US"    #{"Distinguished Flying Cross" "National Women's Hall of Fame"}
            "World" #{"Altitude record for Autogyro" "First to cross Atlantic twice"}}})

(def recipe
  {:title       "Chocolate chip cookies"
   :ingredients {"flour"           [(+ 2 1/4) :cup]
                 "baking soda"     [1 :teaspoon]
                 "salt"            [1 :teaspoon]
                 "butter"          [1 :cup]
                 "sugar"           [3/4 :cup]
                 "brown sugar"     [3/4 :cup]
                 "vanilla"         [1 :teaspoon]
                 "eggs"            2
                 "chocolate chips" [12 :ounce]}})

(def gini-coefficient
  {"Afghanistan" {2008 27.8}
   "Indonesia"   {2008 34.1 2010 35.6 2011 38.1}
   "Uruguay"     {2008 46.3 2009 46.3 2010 45.3}})


;; Rich Comment Block
(comment

  (def sample-person
    {:name   "Amelia Earhart"
     :birth  1897
     :death  1939
     :awards {"US"    #{"Distinguished Flying Cross" "National Women's Hall of Fame"}
              "World" #{"Altitude record for Autogyro" "First to cross Atlantic twice"}}})

  (person->awards sample-person)

  (-> sample-person
      (person->awards)
      )


  (add2)
  #_=> 6

  (add2 5)
  #_=> 7





  (get (get (get recipe :ingredients) "chocolate chips") 0 "not found")

  clojure.lang.Keyword

  (let [my-map "a" #_{:a  1
                      :b  2
                      "c" 3}]
    (get my-map "c"))

  (let [my-map {:a 1
                :b {:c 2
                    :d {:e  4
                        :f  "c"
                        "g" 1}}}]
    #_(-> my-map :b :d :f)
    (get-in my-map [:b :d "g"]))


  (let [ingredients (get recipe :ingredients)
        ingredient  (get ingredients "chocolate chips")
        quantity    (get 0 "not found")]
    quantity)


  (-> recipe
      (get :ingredients)
      (get "chocolate chips")
      (get 0 "not found"))

  #_(+ 2
       (+ 4
          (- 5
             (/ 2
                3))))

  (#(+ 2 %)
   3)

  ((fn [n]
     (+ 2 n))
   3)

  ((partial + 2)
   3)

  (java.util.HashMap.)



  #_(macroexpand
      '(+ 1 (->> 3
                 (/ 2)
                 (- 5)
                 (+ 4)
                 (+ 2))))


  ;; Macro
  ;; Code
  ;; Data
  )