(ns aoc2020.day2
  (:require [aoc2020.util :refer [file->seq]])
  (:require [clojure.string :as str]))

(defn null-check?
  [char-frequency]
  (if (nil? char-frequency)
    0
    char-frequency))

(defn policy-pwd-categorizer
  [policy-pwd-str]
  (let [remove-char  (str/replace policy-pwd-str
                       #"[^a-zA-Z0-9]" " ")
        [lower-str upper-str letter pwd] (str/split remove-char #"\s+")
        lower        (parse-long lower-str)
        upper        (parse-long upper-str)
        letter->char (seq letter)
        pwd->char    (seq pwd)]
    {:pwd-policy {:lower-limit  lower
                  :upper-limit  upper
                  :limit-letter letter->char}
     :pwd        pwd->char}))

(defn pwd-validity-check
  [pwd-policy+pwd-map]
  (let [{pwd-policy :pwd-policy
         pwd        :pwd} pwd-policy+pwd-map
        {lower-limit    :lower-limit
         upper-limit    :upper-limit
         [limit-letter] :limit-letter} pwd-policy
        pwd-letter-cnt        (frequencies pwd)
        pwd-appear-lim-letter (-> (get pwd-letter-cnt limit-letter)
                                (null-check?))
        above-lo-bound?       (>= pwd-appear-lim-letter
                                lower-limit)
        under-hi-bound?       (<= pwd-appear-lim-letter
                                upper-limit)]
    (and above-lo-bound? under-hi-bound?)))

(defn valid-password-cnt
  [password-s]
  (->> password-s
    (mapv policy-pwd-categorizer)
    (mapv pwd-validity-check)
    (remove false?)
    (count)))

(comment
  ;--------------------------data---------------------------------

  (do (def sample-entry-s (file->seq "aoc2020/day2/input-sample.txt"))
    sample-entries)
  #_=> ["1-3 a: abcde"
        "1-3 b: cdefg"
        "2-9 c: ccccccccc"]

  (do (def entry-s (file->seq "aoc2020/day2/input.txt"))
    entries)
  #_=> ["15-16 f: ffffffffffffffhf"
        "6-8 b: bbbnvbbb"
        "6-10 z: zhzzzzfzzzzzzzzzpzz"
        "9-13 s: dmssskssqsssssf"
        "5-6 v: hvvgvrm"
        "1-8 s: sssssssssss",,,]

  ;----------------------------function evaluation-----------------

  (policy-pwd-categorizer "1-2@!a:#$@%*()   dsdsas")
  #_=> {:pwd-policy {:lower-limit  1,
                     :upper-limit  2,
                     :limit-letter (\a)},
        :pwd        (\d \s \d \s \a \s)}

  (mapv policy-pwd-categorizer sample-entry-s)
  #_=> [{:pwd-policy {:lower-limit 1, :upper-limit 3, :limit-letter (\a)},
         :pwd        (\a \b \c \d \e)}
        {:pwd-policy {:lower-limit 1, :upper-limit 3, :limit-letter (\b)},
         :pwd        (\c \d \e \f \g)}
        {:pwd-policy {:lower-limit 2, :upper-limit 9, :limit-letter (\c)},
         :pwd        (\c \c \c \c \c \c \c \c \c)}]


  (pwd-validity-check {:pwd-policy {:lower-limit  1,
                                    :upper-limit  2,
                                    :limit-letter [\a]},
                       :pwd        [\d \s \d \s \a \s]})
  #_=> true

  (->> ["1-3 a: abcde"
        "1-3 b: cdefg"
        "2-9 c: ccccccccc"]
    (mapv parse-policy-pwd-str)
    (mapv pwd-validity-check))
  #_=> [true false true]

  (valid-password-cnt ["1-3 a: abcde"
                       "1-3 b: cdefg"
                       "2-9 c: ccccccccc"])
  #_=> 2

  (valid-password-cnt entry-s)





  ;------------------------notes please ignore for now!!---------------------------
  (count [[1] [2] [10]])


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

(frequencies (vec (seq "abcs")))


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


