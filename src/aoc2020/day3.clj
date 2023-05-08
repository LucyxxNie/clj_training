(ns aoc2020.day3
  (:require [aoc2020.util :refer [file->seq]]))


(defn tob-slope-down
  [row-size down-slp]
  (rest (range 0 row-size down-slp)))

(defn tob-slope-right
  [col-size right-slp row-size down-slp]
  (let [num-of-stops (- row-size down-slp)]
    (->> (iterate (fn [col]
                    (-> col
                      (+ right-slp)
                      (mod col-size))) right-slp)
      (take num-of-stops))))

(defn tree-enctr?
  [cur-row cur-col map-s]
  (let [coord (-> map-s
                (get cur-row)
                (get cur-col))]
    (if (= coord \#)
      true
      false)))

(defn total-tree-enctr-cnt
  [map-s & {:keys [right-slp down-slp]}]
  (let [col-size (count (get map-s 1))
        row-size (count map-s)]
    (->> (mapv (fn [cur-row cur-col]
                 (tree-enctr? cur-row cur-col map-s))
           (tob-slope-down row-size down-slp)
           (tob-slope-right col-size right-slp row-size down-slp))
      (remove false?)
      (count))))

(defn tree-enctr-multiply
  [map-s]
  (->> (vector
         (total-tree-enctr-cnt map-s :right-slp 1 :down-slp 1)
         (total-tree-enctr-cnt map-s :right-slp 3 :down-slp 1)
         (total-tree-enctr-cnt map-s :right-slp 5 :down-slp 1)
         (total-tree-enctr-cnt map-s :right-slp 7 :down-slp 1)
         (total-tree-enctr-cnt map-s :right-slp 1 :down-slp 2))
    (reduce *)))




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
  ;;tobbogan moving at slope right 3 down 1, calculate total tree encounter

  (tob-slope-down 5 1)
  #_=> (1 2 3 4)
  (tob-slope-down 5 2)
  #_=> (2 4)

  (tob-slope-right 10 3 5 1)
  #_=> (3 6 9 2)
  (tob-slope-right 15 7 5 2)
  #_=> (7 14 6)

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
                         "...###."], :right-slp 3, :down-slp 1)
  #_=> 1


  (total-tree-enctr-cnt sample-map, :right-slp 3, :down-slp 1)
  #_=> 7

  (total-tree-enctr-cnt entry-map, :right-slp 3, :down-slp 1)
  #_=> 220


  ;;-----------------------function eval part 2----------------------------------
  ;;calculate the product by multiplying the tree encounter using different slp

  (tree-enctr-multiply ["###.#.#"
                        ".####.#"
                        "######."])
  #_=> 2

  (tree-enctr-multiply sample-map-s)
  #_=> 336

  (tree-enctr-multiply entry-map-s)
  #_=> 2138320800

  )
