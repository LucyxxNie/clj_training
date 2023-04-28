(ns clj-training.Practice1-data-manipulation)

;Practice1- sample person info(see Rich comment block for data and evaluation)
(defn awards
  "Pass a  person info and return his/her awards."
  [person]
  (:awards person))

(defn person->awards
  "pass a person info and return his/her awards"
  [person]
  (:awards person))

(defn person->us-awards
  "pass a person info and return his/her US awards"
  [person]
  (-> person
    (get :awards)
    (get "US")))

(defn person->world-awards
  "pass a person info and return his/her world awards"
  [person]
  (-> person
      (get  :awards)
      (get "World")))


(defn awards-by-name
  "pass a person's name and return his/her awards"
  [name]
  (if (= (:name sample-person) name)
    (:awards sample-person)
    "not found"))

(defn birth
  "Pass a person info and return his/her birth year"
  [person]
  (:birth person))
(defn death
  "Pass a person info and return his/her death year"
  [person]
  (:death person))

;;Practice 2--sample recipe (see Rich comment block for data and evaluation)
(defn recipe-name
  [recipe]
  (-> recipe
    (get :title)))

(defn recipe-indredients
  [recipe]
  (-> recipe
      (get :ingredients)))

(defn eggs-num
  [recipe]
  (-> recipe
      ;here, I inserted another function I defined previously
      (recipe-indredients)
      (get "eggs" "no eggs")))

(defn ingredients-list
  "pass a recipe and return the list of ingredients w/o measurement."
  [recipe]
  (->> recipe
      (recipe-indredients)
      (map key)))


;; Rich Comment Block
(comment
  (defn plus [x]
    (+ x 1)
    (do 2))
  ;Below are sample data used for this practice

;;Practice 1 data
  (def sample-person
    {:name   "Amelia Earhart"
     :birth  1897
     :death  1939
     :awards {"US"    #{"Distinguished Flying Cross" "National Women's Hall of Fame"}
              "World" #{"Altitude record for Autogyro" "First to cross Atlantic twice"}}})

;;Practice 2 data
  (def sample-recipe
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

;;Practice 3 data
  (def sample-gini-coefficient
    {"Afghanistan" {2008 27.8}
     "Indonesia"   {2008 34.1 2010 35.6 2011 38.1}
     "Uruguay"     {2008 46.3 2009 46.3 2010 45.3}})

  ;--------------------------------------------------------------------------
  ;Below are practice 1 evaluation
  (awards sample-person)
  #_=> {"US" #{"National Women's Hall of Fame" "Distinguished Flying Cross"},
   "World" #{"Altitude record for Autogyro" "First to cross Atlantic twice"}}

  (person->awards sample-person)
  #_=> {"US" #{"National Women's Hall of Fame" "Distinguished Flying Cross"},
   "World" #{"Altitude record for Autogyro" "First to cross Atlantic twice"}}

  (person->us-awards sample-person)
  #_=> #{"National Women's Hall of Fame" "Distinguished Flying Cross"}

  (person->world-awards sample-person)
  #_=> #{"Altitude record for Autogyro" "First to cross Atlantic twice"}

  (awards-by-name "no")
  #_=> "not found"

  (awards-by-name "Amelia Earhart")
  #_=> {"US" #{"National Women's Hall of Fame" "Distinguished Flying Cross"},
   "World" #{"Altitude record for Autogyro" "First to cross Atlantic twice"}}

  (birth sample-person)
  #_=> 1897

  (death sample-person)
  (-> sample-person
      (death))
  #_=> 1939

  (-> sample-person
      (person->awards))

;Practice 1 evaluation ended
;-----------------------------------------------------------------------------
;Below are practice 2 evaluation

  (recipe-name sample-recipe)
  (-> sample-recipe
      (recipe-name))
  #_=>"Chocolate chip cookies"

  (-> sample-recipe
      (recipe-indredients))

  (-> sample-recipe
      (eggs-num))

  (ingredients-list sample-recipe)



;;-----------------------------Below are the notes from Lukcasz--------------------
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

  (get (get (get recipe :ingredients) "chocolate chips") 0 "not found")

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
