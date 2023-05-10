(ns aoc2020.day4
  (:require [aoc2020.util :refer [file->seq2]])
  (:require [clojure.string :as str]))

(defn parse-passport-data
  [passport-data]
  (->> (-> passport-data
         (str/split #"\s+"))
    (mapv (fn [str] (str/split str #":")))
    (mapv (fn [[k v]] [(keyword k) v]))
    (into {})))

(defn required-field-present?
  [{:keys [byr iyr eyr hgt hcl ecl pid]
    :or   {byr false, iyr false, eyr false,
           hgt false, hcl false ecl false
           pid false}}]
  (and byr iyr eyr hgt hcl ecl pid))



(defn hgt-valid?
  [hgt]
  (let [[hgt-str unit] (str/split hgt #"(?=[a-z])" 2)
        hgt (parse-long hgt-str)]
    (case unit
      "cm" (<= 150 hgt 193)
      "in" (<= 59 hgt 76)
      false)))

(defn hcl-valid?
  [hcl]
  (let [chars       (rest hcl)
        filter-char (str/replace chars #"[^a-f0-9]" "")]
    (and (= (first hcl) \#)
      (= (count filter-char) 6))))

(defn passport-data-valid?
  [{:keys [byr iyr eyr hgt hcl ecl pid]
    :or   {byr "0", iyr "0", eyr "0",
           hgt "0cm", hcl "#ABCDEF" ecl "non"
           pid "0"}}]
  (let [ecl-set    #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}
        byr-valid? (<= 1920 (parse-long byr) 2002)
        iyr-valid? (<= 2010 (parse-long iyr) 2020)
        eyr-valid? (<= 2020 (parse-long eyr) 2030)
        hgt-valid? (hgt-valid? hgt)
        hcl-valid? (hcl-valid? hcl)
        ecl-valid? (contains? ecl-set ecl)
        pid-valid? (->> pid
                     (re-matches #"\d+")
                     (count)
                     (= 9))]
    (and
      byr-valid?
      iyr-valid?
      eyr-valid?
      hgt-valid?
      hcl-valid?
      ecl-valid?
      pid-valid?)))

(defn valid-passport-cnt
  [passport-data-s passport-valid?]
  (->> passport-data-s
    (mapv parse-passport-data)
    (mapv passport-valid?)
    (remove false?)
    (count)))



(comment
  ;;----------------------------data---------------------------------------------------
  (do (def sample-passport-data-s (file->seq2 "aoc2020/day4/input-sample.txt"))
    sample-passport-data-s)
  #_=> ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm"
        "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929"
        "hcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm"
        "hcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in"]

  (do (def passport-data-s (file->seq2 "aoc2020/day4/input.txt"))
    passport-data-s)
  #_=> ["byr:2024 iyr:2016\neyr:2034 ecl:zzz pid:985592671 hcl:033b48\nhgt:181 cid:166"
        "hgt:66cm\npid:152cm\nhcl:cfb18a eyr:1947\nbyr:2020 ecl:zzz iyr:2029"
        "ecl:gry hcl:#888785 eyr:2023 cid:63\niyr:2019 hgt:177cm\npid:656793259"
        "pid:#5e832a\necl:dne hcl:#7d3b0c byr:2018 eyr:1928 hgt:61cm iyr:1936 cid:241"
        "hcl:#888785 ecl:oth eyr:2025\npid:597580472\niyr:2017 hgt:187cm byr:1957 cid:247",,,]

  ;;---------------------------func part 1----------------------------------------
  ;; Part1: count the number of valid passports where all required fields are present
  ;; except for optional field cid

  ;;parse the passport data in string and convert it into a map, where key is the required
  ;;field, and value is the data
  ;;parse the map to a validity check function to check if the required fields are all present
  ;;count the number of true (valid passport) value

  (parse-passport-data "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n
                        byr:1937 iyr:2017 cid:147 hgt:183cm")
  #_=> {:ecl "gry", :pid "860033327", :eyr "2020", :hcl "#fffffd",
        :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"}

  (parse-passport-data "ecl:gry hcl:#888785 eyr:2023 cid:63
                        \niyr:2019 hgt:177cm\npid:656793259")
  #_=> {:ecl "gry", :hcl "#888785", :eyr "2023",
        :cid "63", :iyr "2019", :hgt "177cm", :pid "656793259"}


  (passport-valid? {:ecl "gry", :pid "8600", :eyr "2020", :hcl "#fffffd",
                    :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"})
  #_=> "8600"                                               ;true,valid passport

  (passport-valid? {:ecl "gry", :hcl "#888785", :eyr "2023",
                    :cid "63", :iyr "2019", :hgt "177cm", :pid "656793259"})
  #_=> false

  (valid-passport-cnt ["ecl:gry hcl:#888785 eyr:2023 cid:63\niyr:2019 hgt:177cm\npid:656793259"
                       "pid:#5e832a\necl:dne hcl:#7d3b0c eyr:1928 hgt:61cm iyr:1936 cid:241"
                       "hcl:#888785 ecl:oth eyr:2025\npid:597580472\niyr:2017 hgt:187cm byr:1957 cid:247"])
  #_=> 1

  (valid-passport-cnt sample-passport-data-s
    required-field-present?)
  #_=> 2

  (valid-passport-cnt passport-data-s
    required-field-present?)
  #_=> 247


  ;;---------------------------------part2---------------------------------------------

  ;;validation for each required field: byr iyr eyr hgt hcl ecl pid

  ;;Built two function to validate height and hair color data,
  ;;For height data, separate the input by number and unit, then check if the number is
  ;;in the range based on its unit
  ;;For hair color data, separated the first and the rest of input and check if the first
  ;;char is \# and filter out invalid chars and check if the valid input is 6.
  ;;Parse the map into a validation function to check if the overall passport is valid
  ;;Count the number of valid passports


  (hgt-valid? "183cm")
  #_=> true
  (hgt-valid? "183in")
  #_=> false

  (hcl-valid? "#12ab34")
  #_=> true
  (hcl-valid? "123456A")
  #_=> false

  (str/replace "123ABC" #"[^a-f0-9]" "")


  (passport-data-valid? {:eyr "2029", :ecl "blu", :cid "129", :byr "1989",
                         :iyr "2014", :pid "896056539", :hcl "#a97842", :hgt "165cm"})
  #_=> true
  (passport-data-valid? {:ecl "gry", :pid "8600", :eyr "2020", :hcl "#fffffd",
                         :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"})
  #_=> false

  (valid-passport-cnt sample-passport-data-s
    passport-data-valid?)
  #_=> 2

  (valid-passport-cnt passport-data-s
    passport-data-valid?)
  #_=> 152

  )
