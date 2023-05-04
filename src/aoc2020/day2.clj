(ns aoc2020.day2
  (:require [aoc2020.util :refer [file->seq]])
  (:require [clojure.string :as str]))

(defn password-cleaner
  [password]
  (let [remove-char    (str/replace password
                         #"[^a-zA-Z0-9]" " ")
        sep-by-space   (str/split remove-char #"\s+")
        clean-password sep-by-space]
    clean-password)

  )




(comment
  ;--------------------------data---------------------------------

  (do (def sample-entries (file->seq "aoc2020/day2/input-sample.txt"))
    sample-entries)
  #_=> ["1-3 a: abcde"
        "1-3 b: cdefg"
        "2-9 c: ccccccccc"]

  (do (def entries (file->seq "aoc2020/day2/input.txt"))
    entries)
  #_=> ["15-16 f: ffffffffffffffhf"
        "6-8 b: bbbnvbbb"
        "6-10 z: zhzzzzfzzzzzzzzzpzz"
        "9-13 s: dmssskssqsssssf"
        "5-6 v: hvvgvrm"
        "1-8 s: sssssssssss",,,]

  ;----------------------------function evaluation-----------------
  (password-cleaner "1-2@!a:#$@%*()dsdsas")
  #_=> ["1" "2" "a" "dsdsas"]
  (mapv password-cleaner sample-entries)
  #_=> [["1" "3" "a" "abcde"]
        ["1" "3" "b" "cdefg"]
        ["2" "9" "c" "ccccccccc"]]


  (parse-long (example 0))





  (-> ["1-3 a: abcde"]
    (str/split #"\s+")
    (str/split #"-")
    (str/split #":")



    (do (def sample-entry-s (file->seq "aoc2020/day1/input-sample.txt"))
      sample-entry-s))
  )
