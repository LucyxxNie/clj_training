(ns aoc2020.day3
  (:require [aoc2020.util :refer [file->seq]]))

(defn coordination
  [down-slope right-slope]
  (let [row-s (take-nth down-slope (range))
        col-s (take-nth right-slope (range))]
    (map (fn [row-s col-s] [row-s col-s])
      row-s col-s)))

(defn tree-enctr?
  [grid-s [row col]]
  (let [col-size (count (get grid-s 1))
        cur-col  (mod col col-size)
        pos      (-> grid-s
                   (get row)
                   (get cur-col))]
    (if (= pos \#)
      true
      false)))

(defn total-tree-enctr-cnt
  [grid-s & {:keys [right-slp down-slp]}]
  (let [row-size (count grid-s)
        coord    (coordination down-slp right-slp)]
    (->> coord
      (take row-size)
      (mapv (fn [pos]
              (tree-enctr? grid-s pos)))
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

  (take 5 (coordination 1 3))
  #_=> ([0 0] [1 3] [2 6] [3 9] [4 12])

  (take 5 (coordination 2 5))
  #_=> ([0 0] [2 5] [4 10] [6 15] [8 20])

  (tree-enctr? ["..#.#.#"
                ".#.##.#"
                "...###."] [1 3])
  #_=> true
  (tree-enctr? ["..#.#.#"
                ".#.##.#"
                "...###."] [2 6])
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
  #_=> 48

  (tree-enctr-multiply sample-grid-s)
  #_=> 336

  (tree-enctr-multiply grid-s)
  #_=> 2138320800

  )
