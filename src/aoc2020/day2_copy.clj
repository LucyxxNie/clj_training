(ns aoc2020.day2_copy
  (:require [aoc2020.util :refer [file->seq]])
  (:require [clojure.string :as str]))

(defn policy-pwd-categorizer
  [policy-pwd-str]
  (let [clean-pwd    (-> policy-pwd-str
                       (str/replace #"[^a-zA-Z0-9]" " ")
                       (str/split #"\s+"))
        [limit1-str limit2-str letter pwd] clean-pwd
        limit1       (parse-long limit1-str)
        limit2       (parse-long limit2-str)
        letter->char (seq letter)]
    {:pwd-policy {:limit1       limit1
                  :limit2       limit2
                  :limit-letter letter->char}
     :pwd        pwd}))


(defn sled-rental-pwd-valid?
  [policy-pwd-map]
  (let [{pwd-policy :pwd-policy
         pwd        :pwd} policy-pwd-map
        {lo             :limit1
         hi             :limit2
         [limit-letter] :limit-letter} pwd-policy
        pwd-letter-cnt (frequencies (seq pwd))
        {letter-freq limit-letter :or {letter-freq 0}} pwd-letter-cnt]
    (<= lo letter-freq hi)))



(defn toboggan-pwd-policy-xor
  [& {:keys [fir-letter sec-letter limit-letter]}]
  (let [fir-eql? (= fir-letter limit-letter)
        sec-eql? (= sec-letter limit-letter)]
    (cond
      (and fir-eql? sec-eql?) false
      (or fir-eql? sec-eql?) true
      :else false)))

(defn toboggan-pwd-valid?
  [policy-pwd-map]
  (let [{pwd-policy :pwd-policy
         pwd        :pwd} policy-pwd-map
        {fir-position   :limit1
         sec-position   :limit2
         [limit-letter] :limit-letter} pwd-policy
        fir-letter (get pwd (- fir-position 1))
        sec-letter (get pwd (- sec-position 1))]
    (toboggan-pwd-policy-xor
      :fir-letter fir-letter
      :sec-letter sec-letter
      :limit-letter limit-letter)))

(defn valid-pwd-cnt
  [pwd-db-s validity-check?]
  (->> pwd-db-s
    (mapv policy-pwd-categorizer)
    (mapv validity-check?)
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

  (policy-pwd-categorizer "1-2@! a: #$@%*()   dsdsas")
  #_=> {:pwd-policy {:limit1       1,
                     :limit2       2,
                     :limit-letter [\a]},
        :pwd        "dsdsas"}

  (mapv policy-pwd-categorizer sample-pwd-s)
  #_=> [{:pwd-policy {:limit1 1, :limit2 3, :limit-letter [\a]},
         :pwd        "abcde"}
        {:pwd-policy {:limit1 1, :limit2 3, :limit-letter [\b]},
         :pwd        "cdefg"}
        {:pwd-policy {:limit1 2, :limit2 9, :limit-letter [\c]},
         :pwd        "ccccccccc"}]


  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter [\a]},
                           :pwd        "dsdsas"})
  #_=> true

  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter [\a]},
                           :pwd        "dsdaas"})
  #_=> true

  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter [\a]},
                           :pwd        "aaas"})
  #_=> true

  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter [\a]},
                           :pwd        "dsdsds"})
  #_=> false


  (valid-pwd-cnt ["1-3 a: abcde"
                  "1-3 b: cdefg"
                  "2-9 c: ccccccccc"]
    sled-rental-pwd-valid?)
  #_=> 2

  (valid-pwd-cnt pwd-db-s
    sled-rental-pwd-valid?)
  #_=> 393

  ;----------------------------function evaluation part 2 -----------------
  ;;Toboggan Corp Auth Sys password policy

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

  (toboggan-pwd-valid? {:pwd-policy {:limit1       1,
                                     :limit2       3,
                                     :limit-letter [\a]},
                        :pwd        "dsdaas"})
  #_=> true

  (toboggan-pwd-valid? {:pwd-policy {:limit1       1,
                                     :limit2       3,
                                     :limit-letter [\a]},
                        :pwd        "dsdaas"})
  #_=> false

  (toboggan-pwd-valid? {:pwd-policy {:limit1       1,
                                     :limit2       3,
                                     :limit-letter [\a]},
                        :pwd        "dsdaas"})
  #_=> false

  (valid-pwd-cnt ["1-3 a: abcde"
                  "1-3 b: cdefg"
                  "2-9 c: ccccccccc"] toboggan-pwd-valid?)
  #_=> 1

  (valid-pwd-cnt pwd-db-s toboggan-pwd-valid?)
  #_=> 690

  )