(ns aoc2020.day3
  (:require [aoc2020.util :refer [file->seq]]))


(defn tob-moving-slope-row
  [row-size]
  (range 1 row-size))

(defn tob-moving-slope-col
  [col-size row-size]
  (->> (iterate (fn [col]
                  (-> col
                    (+ 3)
                    (mod col-size))) 3)
    (take (- row-size 1))))

(defn tree-enctr?
  [cur-row cur-col map-s]
  (let [coord (-> map-s
                (get cur-row)
                (get cur-col))]
    (if (= coord \#)
      true
      false)))

(defn total-tree-enctr-cnt
  [map-s]
  (let [col-size (count (get map-s 1))
        row-size (count map-s)]
    (->> (mapv (fn [cur-row cur-col]
                 (tree-enctr? cur-row cur-col map-s))
           (tob-moving-slope-row row-size)
           (tob-moving-slope-col col-size row-size))
      (remove false?)
      (count))))





(comment
  ;;------------------------------data------------------------------------------
  (do (def sample-map-s (file->seq "aoc2020/day3/input-sample.txt"))
    sample-map-s)
  #_=> ["..##......."
        "#...#...#.."
        ".#....#..#."
        "..#.#...#.#"
        ".#...##..#."
        "..#.##....."
        ".#.#.#....#"
        ".#........#"
        "#.##...#..."
        "#...##....#"
        ".#..#...#.#"]


  (do (def entry-map-s (file->seq "aoc2020/day3/input.txt"))
    entry-map-s)
  #_=> ["...#.....#.......##......#....."
        "...#..................#........"
        "....##....#.......#............"
        ".........#.......#.......#....."
        "..#..............#.........#..#"
        ".....#.........#....#....#....#"
        "....##..........#.#.##........."
        "...#....##...#...#...#.#..#....",,,]

  ;;------------------------------function eval part 1------------------------------------------

  (def example ["..#.#.#"
                ".#.##.#"
                "...####"])

  (tob-moving-slope-row 5)
  #_=> (1 2 3 4)
  (tob-moving-slope-row 10)
  #_=> (1 2 3 4 5 6 7 8 9)

  (tob-moving-slope-col 5 5)
  #_=> (3 1 4 2)
  (tob-moving-slope-col 10 10)
  #_=> (3 6 9 2 5 8 1 4 7)
  (tob-moving-slope-col 15 7)
  #_=> (3 6 9 12 0 3)


  (tree-enctr? 1 3 ["..#.#.#"
                    ".#.##.#"
                    "...###."])
  #_=> true
  (tree-enctr? 2 6 ["..#.#.#"
                    ".#.##.#"
                    "...###."])
  #_=> false

  (total-tree-enctr-cnt ["..#.#.#"
                         ".#.##.#"
                         "...###."])
  #_=> 1


  (total-tree-enctr-cnt sample-map)
  #_=> 7

  (total-tree-enctr-cnt entry-map)
  #_=> 220

  )
