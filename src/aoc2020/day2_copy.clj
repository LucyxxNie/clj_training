(ns aoc2020.day2_copy
  (:require [aoc2020.util :refer [file->seq]])
  (:require [clojure.string :as str]))

(defn parsing-policy-pwd
  [policy-pwd-str]
  (let [clean-pwd    (-> policy-pwd-str
                       (str/replace #"[^a-zA-Z0-9]" " ")
                       (str/split #"\s+"))
        [limit1-str limit2-str letter pwd] clean-pwd
        limit1       (parse-long limit1-str)
        limit2       (parse-long limit2-str)
        letter->char (first (seq letter))]
    {:pwd-policy {:limit1       limit1
                  :limit2       limit2
                  :limit-letter letter->char}
     :pwd        pwd}))

(defn sled-rental-pwd-valid?
  [{:keys                                [pwd]
    {:keys [limit1 limit2 limit-letter]} :pwd-policy}]
  (let [pwd-letter-cnt (frequencies (seq pwd))
        {letter-freq limit-letter :or {letter-freq 0}} pwd-letter-cnt]
    (<= limit1 letter-freq limit2)))

(defn toboggan-pwd-valid?
  [{:keys                                [pwd]
    {:keys [limit1 limit2 limit-letter]} :pwd-policy}]
  (let [fir-letter (get pwd (- limit1 1))
        sec-letter (get pwd (- limit2 1))
        fir-eql?   (= fir-letter limit-letter)
        sec-eql?   (= sec-letter limit-letter)]
    (or (and fir-eql? (not sec-eql?))
      (and sec-eql? (not fir-eql?)))))

(defn valid-pwd-cnt
  [pwd-db-s validity-check?]
  (->> pwd-db-s
    (mapv parsing-policy-pwd)
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
  ;Q: Old-sled shop password policy

  ;;generate a map by categorizing password info to policy and password
  ;;parse the map to a password validity check function, get a boolean value
  ;;remove all the invalid (false) passwords, count the number of valid passwords


  (parsing-policy-pwd "1-2@! a: #$@%*()   dsdsas")
  #_=> {:pwd-policy {:limit1 1, :limit2 2, :limit-letter \a}, :pwd "dsdsas"}

  (mapv parsing-policy-pwd sample-pwd-s)
  #_=> [{:pwd-policy {:limit1 1, :limit2 3, :limit-letter (\a)}, :pwd "abcde"}
        {:pwd-policy {:limit1 1, :limit2 3, :limit-letter (\b)}, :pwd "cdefg"}
        {:pwd-policy {:limit1 2, :limit2 9, :limit-letter (\c)}, :pwd "ccccccccc"}]


  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter \a},
                           :pwd        "dsdsas"})
  #_=> true

  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter \a},
                           :pwd        "dsdaas"})
  #_=> true

  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter \a},
                           :pwd        "aaas"})
  #_=> false

  (sled-rental-pwd-valid? {:pwd-policy {:limit1       1,
                                        :limit2       2,
                                        :limit-letter \a},
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


  (toboggan-pwd-valid? {:pwd-policy {:limit1       1,
                                     :limit2       3,
                                     :limit-letter \a},
                        :pwd        "dsaaas"})
  #_=> true

  (toboggan-pwd-valid? {:pwd-policy {:limit1       1,
                                     :limit2       2,
                                     :limit-letter \a},
                        :pwd        "dsdaas"})
  #_=> false

  (toboggan-pwd-valid? {:pwd-policy {:limit1       1,
                                     :limit2       2,
                                     :limit-letter \a},
                        :pwd        "aaddds"})
  #_=> false

  (valid-pwd-cnt ["1-3 a: abcde"
                  "1-3 b: cdefg"
                  "2-9 c: ccccccccc"] toboggan-pwd-valid?)
  #_=> 1

  (valid-pwd-cnt pwd-db-s toboggan-pwd-valid?)
  #_=> 690

  )