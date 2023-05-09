(ns aoc2020.day3
  (:require [aoc2020.util :refer [file->seq]]))


(defn toboggan-slope-down
  [down-slp]
  (take-nth down-slp (range)))

(defn toboggan-slope-right
  [right-slp]
  (take-nth right-slp (range)))

(defn tree-enctr?
  [grid-s row col]
  (let [col-size (count (get grid-s 1))
        cur-col  (mod col col-size)
        coord    (-> grid-s
                   (get row)
                   (get cur-col))]
    (if (= coord \#)
      true
      false)))

(defn total-tree-enctr-cnt
  [grid-s & {:keys [down-slp right-slp]}]
  (let [row-size (count grid-s)
        cur-row  (take row-size (toboggan-slope-down down-slp))
        cur-col  (take row-size (toboggan-slope-right right-slp))]
    (->> (mapv (fn [cur-row cur-col]
                 (tree-enctr? grid-s cur-row cur-col))
           cur-row
           cur-col)
      (remove false?)
      (count))))

(defn tree-enctr-multiply
  [grid-s]
  (->> (vector
         (total-tree-enctr-cnt grid-s :right-slp 1 :down-slp 1)
         (total-tree-enctr-cnt grid-s :right-slp 3 :down-slp 1)
         (total-tree-enctr-cnt grid-s :right-slp 5 :down-slp 1)
         (total-tree-enctr-cnt grid-s :right-slp 7 :down-slp 1)
         (total-tree-enctr-cnt grid-s :right-slp 1 :down-slp 2))
    (reduce *)))




(comment
  ;;------------------------------data------------------------------------------
  (do (def sample-grid-s (file->seq "aoc2020/day3/input-sample.txt"))
    sample-grid-s)
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


  (do (def grid-s (file->seq "aoc2020/day3/input.txt"))
    grid-s)
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

  ;;create infinite lazy seq for row and column coordination
  ;;Based on the coordination, find if the position has a tree or not, return boolean value
  ;;remove false value and count the true value

  (take 10 (toboggan-slope-right 2))
  #_=> (0 2 4 6 8 10 12 14 16 18)
  (take 10 (toboggan-slope-right 5))
  #_=> (0 5 10 15 20 25 30 35 40 45)

  (take 10 (toboggan-slope-down 3))
  #_=> (0 3 6 9 12 15 18 21 24 27)

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


  (total-tree-enctr-cnt sample-grid-s, :right-slp 3, :down-slp 1)
  #_=> 7

  (total-tree-enctr-cnt grid-s, :right-slp 3, :down-slp 1)
  #_=> 220



  ;;-----------------------function eval part 2----------------------------------
  ;;calculate the product by multiplying the tree encounter using different slp

  ;;Parse different right and down slopes as args into tree-cnt function
  ;;Multiply the results

  (tree-enctr-multiply ["###.#.#"
                        ".####.#"
                        "######."])
  #_=> 2

  (tree-enctr-multiply sample-grid-s)
  #_=> 336

  (tree-enctr-multiply grid-s)
  #_=> 2138320800

  )
