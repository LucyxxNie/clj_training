(ns aoc2020.day2
  (:require [aoc2020.util :refer [file->seq]]))



(comment

  (do (def sample-entries (file->seq "aoc2020/day2/input-sample.txt"))
      sample-entries)
  #_=> ["1-3 a: abcde" "1-3 b: cdefg" "2-9 c: ccccccccc"]

  (do (def entries (file->seq "aoc2020/day2/input.txt"))
      entries)
  #_=> ["15-16 f: ffffffffffffffhf"
        "6-8 b: bbbnvbbb"
        "6-10 z: zhzzzzfzzzzzzzzzpzz"
        "9-13 s: dmssskssqsssssf"
        "5-6 v: hvvgvrm"
        "1-8 s: sssssssssss",,,]



  (do (def sample-entry-s (file->seq "aoc2020/day1/input-sample.txt"))
      sample-entry-s)
  )
