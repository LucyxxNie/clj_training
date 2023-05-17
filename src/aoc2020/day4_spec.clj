(ns aoc2020.day4-spec
  (:require [aoc2020.util :refer [file->seq2]])
  (:require [clojure.string :as str])
  (:require [clojure.spec.alpha :as s]))

(defn parse-passport-data
  [passport-data]
  (->> (-> passport-data
           (str/split #"\s+"))
       (mapv (fn [str] (str/split str #":")))
       (mapv (fn [[k v]] [(keyword k) v]))
       (into {})))

(s/def :unq/required-fields
  (s/keys :req-un [:field/byr :field/iyr :field/eyr :field/hgt
                   :field/hcl :field/ecl :field/pid]
          :opt-un [:field/cid]))


(s/def :unq/data-valid
  (s/keys :req-un [:data/byr :data/iyr :data/eyr :data/hgt
                   :data/hcl :data/ecl :data/pid]
          :opt-un [:data/cid]))

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

(s/def :data/byr #(<= 1920 (parse-long %) 2002))
(s/def :data/iyr #(<= 2010 (parse-long %) 2020))
(s/def :data/eyr #(<= 2020 (parse-long %) 2030))
(s/def :data/hgt #(hgt-valid? %))
(s/def :data/hcl #(hcl-valid? %))
(s/def :data/ecl #(contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} %))
(s/def :data/pid #(->> %
                       (re-matches #"\d+")
                       (count)
                       (= 9)))

(defn valid-passport-cnt
  [passport-data-s passport-valid?]
  (->> passport-data-s
       (mapv parse-passport-data)
       (mapv #(s/valid? passport-valid? %))
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
  ;; Part1: count the number of valid passports where all required fields are present except for optional field cid

  ;;Parse the passport data in string and convert it into a map, where key is the required field, and value is the data
  ;;Use spec entity map to check if all the required fields are present.
  ;;Count the number of true (valid passport) value


  (parse-passport-data "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n
                        byr:1937 iyr:2017 cid:147 hgt:183cm")
  #_=> #:field{:ecl "gry", :pid "860033327", :eyr "2020", :hcl "#fffffd",
               :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"}


  (s/valid? :unq/required-fields
            {:ecl "gry", :pid "860033327", :eyr "2020", :hcl "#fffffd",
             :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"})
  #_=> true

  (s/valid? :unq/required-fields
            {:ecl "gry", :pid "860033327", :eyr "2020", :hcl "#fffffd",
             :byr "1937", :iyr "2017", :cid "147"})
  #_=> false

  (s/explain :unq/required-fields
             {:ecl "gry", :pid "860033327", :hcl "#fffffd",
              :byr "1937", :iyr "2017", :cid "147"})
  ;;- failed: (contains? % :eyr) spec: :unq/required-fields
  ;;- failed: (contains? % :hgt) spec: :unq/required-fields
  #_=> nil

  (valid-passport-cnt ["ecl:gry hcl:#888785 eyr:2023 cid:63\niyr:2019 hgt:177cm\npid:656793259"
                       "pid:#5e832a\necl:dne hcl:#7d3b0c eyr:1928 hgt:61cm iyr:1936 cid:241"
                       "hcl:#888785 ecl:oth eyr:2025\npid:597580472\niyr:2017 hgt:187cm byr:1957 cid:247"]
                      required-fields-present?)
  #_=> 1

  (valid-passport-cnt sample-passport-data-s
                      :unq/required-fields)
  #_=> 2

  (valid-passport-cnt passport-data-s
                      :unq/required-fields)
  #_=> 247

  ;;---------------------------------part2---------------------------------------------
  ;;Part 2: validation for each required field: byr iyr eyr hgt hcl ecl pid

  ;;Built an entity map with all required fields as keys, each key is registered to validate the data in the field
  ;;Parse passport data to entity map, return true if passport is valid, false if it's invalid
  ;;Count the valid (true) passport


  (s/valid? :unq/data-valid {:eyr "2029", :ecl "blu", :cid "129", :byr "1989",
                             :iyr "2014", :pid "896056539", :hcl "#a97842", :hgt "165cm"})
  #_=> true

  (s/valid? :unq/data-valid {:ecl "gry", :pid "8600", :eyr "2020", :hcl "#fffffd",
                             :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"})
  #_=> false

  (s/explain :unq/data-valid {:ecl "gry", :pid "8600", :eyr "2020", :hcl "#fffffd",
                              :byr "1937", :iyr "2017", :cid "147", :hgt "183in"})
  ;; "8600" - failed: (->> % (re-matches #"\d+") (count) (= 9)) in: [:pid] at: [:pid] spec: :data/pid
  ;; "183in" - failed: (hgt-valid? %) in: [:hgt] at: [:hgt] spec: :data/hgt
  #_=> nil

  (valid-passport-cnt sample-passport-data-s
                      :unq/data-valid)
  #_=> 2

  (valid-passport-cnt passport-data-s
                      :unq/data-valid)
  #_=> 145

  )

