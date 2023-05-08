(ns aoc2020.day2_copy
  (:require [aoc2020.util :refer [file->seq]])
  (:require [clojure.string :as str]))

(defn sled-rental-policy-pwd-categorizer
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


(defn sled-rental-pwd-validity-check
  [policy-pwd-map]
  (let [{pwd-policy :pwd-policy
         pwd        :pwd} policy-pwd-map
        {lower-limit    :lower-limit
         upper-limit    :upper-limit
         [limit-letter] :limit-letter} pwd-policy
        pwd-letter-cnt (frequencies pwd)
        {letter-freq limit-letter :or {letter-freq 0}} pwd-letter-cnt
        abv-low-limit? (>= letter-freq
                         lower-limit)
        und-hi-limit?  (<= letter-freq
                         upper-limit)]
    (and abv-low-limit? und-hi-limit?)))

(defn sled-rental-valid-pwd-cnt
  [pwd-db-s]
  (->> pwd-db-s
    (mapv sled-rental-policy-pwd-categorizer)
    (mapv sled-rental-pwd-validity-check)
    (remove false?)
    (count)))



(defn toboggan-policy-pwd-categorizer
  [policy-pwd-str]
  (let [remove-char  (str/replace policy-pwd-str
                       #"[^a-zA-Z0-9]" " ")
        [fir-pos-str sec-pos-str letter pwd] (str/split remove-char #"\s+")
        fir-pos      (parse-long fir-pos-str)
        sec-pos      (parse-long sec-pos-str)
        letter->char (seq letter)]
    {:pwd-policy {:first-position  fir-pos
                  :second-position sec-pos
                  :limit-letter    letter->char}
     :pwd        pwd}))

(defn toboggan-pwd-policy-xor
  [& {:keys [fir-letter sec-letter limit-letter]}]
  (let [fir-eql? (= fir-letter limit-letter)
        sec-eql? (= sec-letter limit-letter)]
    (cond
      (and fir-eql? sec-eql?) false
      (or fir-eql? sec-eql?) true
      :else false)))

(defn toboggan-pwd-validity-check
  [policy-pwd-map]
  (let [{pwd-policy :pwd-policy
         pwd        :pwd} policy-pwd-map
        {fir-position   :first-position
         sec-position   :second-position
         [limit-letter] :limit-letter} pwd-policy
        fir-letter (get pwd (- fir-position 1))
        sec-letter (get pwd (- sec-position 1))]
    (toboggan-pwd-policy-xor
      :fir-letter fir-letter
      :sec-letter sec-letter
      :limit-letter limit-letter)))


(defn toboggan-valid-pwd-cnt
  [pwd-db-s]
  (->> pwd-db-s
    (mapv toboggan-policy-pwd-categorizer)
    (mapv toboggan-pwd-validity-check)
    (remove false?)
    (count)))



(comment
  ;--------------------------data---------------------------------

  (do (def sample-pwd-s (file->seq "aoc2020/day2/input-sample.txt"))
    sample-pwd-s)
  #_=> ["1-3 a: abcde"
        "1-3 b: cdefg"
        "2-9 c: ccccccccc"]

  (do (def pwd-db-s (file->seq "aoc2020/day2/input.txt"))
    pwd-db-s)
  #_=> ["15-16 f: ffffffffffffffhf"
        "6-8 b: bbbnvbbb"
        "6-10 z: zhzzzzfzzzzzzzzzpzz"
        "9-13 s: dmssskssqsssssf"
        "5-6 v: hvvgvrm"
        "1-8 s: sssssssssss",,,]

  ;----------------------------function evaluation part 1-----------------
  ;Old-sled shop password policy

  (sled-rental-policy-pwd-categorizer "1-2@! a: #$@%*()   dsdsas")
  #_=> {:pwd-policy {:lower-limit  1,
                     :upper-limit  2,
                     :limit-letter (\a)},
        :pwd        (\d \s \d \s \a \s)}

  (mapv sled-rental-policy-pwd-categorizer sample-pwd-s)
  #_=> [{:pwd-policy {:lower-limit 1, :upper-limit 3, :limit-letter (\a)},
         :pwd        (\a \b \c \d \e)}
        {:pwd-policy {:lower-limit 1, :upper-limit 3, :limit-letter (\b)},
         :pwd        (\c \d \e \f \g)}
        {:pwd-policy {:lower-limit 2, :upper-limit 9, :limit-letter (\c)},
         :pwd        (\c \c \c \c \c \c \c \c \c)}]


  (sled-rental-pwd-validity-check {:pwd-policy {:lower-limit  1,
                                                :upper-limit  2,
                                                :limit-letter [\a]},
                                   :pwd        [\d \s \d \s \a \s]})
  #_=> true

  (sled-rental-pwd-validity-check {:pwd-policy {:lower-limit  1,
                                                :upper-limit  2,
                                                :limit-letter [\a]},
                                   :pwd        [\d \s \d \s \a \a]})
  #_=> true

  (sled-rental-pwd-validity-check {:pwd-policy {:lower-limit  1,
                                                :upper-limit  3,
                                                :limit-letter [\b]},
                                   :pwd        [\c \d \e \f \g]})
  #_=> false

  (sled-rental-pwd-validity-check {:pwd-policy {:lower-limit  1,
                                                :upper-limit  3,
                                                :limit-letter [\b]},
                                   :pwd        [\b \b \b \b \g]})
  #_=> false


  (sled-rental-valid-pwd-cnt ["1-3 a: abcde"
                              "1-3 b: cdefg"
                              "2-9 c: ccccccccc"])
  #_=> 2

  (sled-rental-valid-pwd-cnt pwd-db-s)
  #_=> 393

  ;----------------------------function evaluation part 2 -----------------
  ;;Toboggan Corp Auth Sys password policy

  (toboggan-policy-pwd-categorizer "1-2@! a: #$@%*()   dsdsas")
  #_=> {:pwd-policy {:first-pos    1,
                     :second-pos   2,
                     :limit-letter [\a],
                     :pwd} "dsdsas"}

  (toboggan-pwd-policy-xor
    :fir-letter \a
    :sec-letter \b
    :limit-letter \a)
  #_=> true
  (toboggan-pwd-policy-xor
    :fir-letter \a
    :sec-letter \a
    :limit-letter \a)
  #_=> false
  (toboggan-pwd-policy-xor
    :fir-letter \c
    :sec-letter \d
    :limit-letter \a)
  #_=> false

  (toboggan-pwd-validity-check {:pwd-policy {:first-position  1,
                                             :second-position 3,
                                             :limit-letter    [\a]},
                                :pwd        "abcde"})
  #_=> true

  (toboggan-pwd-validity-check {:pwd-policy {:first-position  1,
                                             :second-position 3,
                                             :limit-letter    [\a]},
                                :pwd        "cbcde"})
  #_=> false

  (toboggan-pwd-validity-check {:pwd-policy {:first-position  1,
                                             :second-position 3,
                                             :limit-letter    [\a]},
                                :pwd        "abade"})
  #_=> false

  (toboggan-valid-pwd-cnt ["1-3 a: abcde"
                           "1-3 b: cdefg"
                           "2-9 c: ccccccccc"])
  #_=> 1

  (toboggan-valid-pwd-cnt pwd-db-s)
  #_=> 690

  )