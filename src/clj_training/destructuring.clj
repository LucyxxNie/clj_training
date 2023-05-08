(ns clj-training.destructuring)

(def client {:info {:name        "abcsdsd"
                    :location    [1]
                    :description "The worldwide leader in plastic tableware."}
             :age  28})

(let [{info :info
       age  :age} client
      {name       :name
       [location] :location} info]
  (print location))
(true)

)

(defn do-something
  [[k v]]
  {:key   k
   :value v})

(mapv do-something
  {:a 1
   :b 2
   :c 3})


(let [{:keys [a b c] :as huge-map} {:a 1
                                    :b 2
                                    :c "x"}]
  [a b huge-map])

(defn apply-arithmetic
  [num-s {:keys [op init-val]
          :or   {op + init-val 0}
          :as   _opts-m}]
  (reduce op init-val num-s))

(apply-arithmetic [1 2 3 4]
  {})
