(ns aoc2020.day3
  (:require [aoc2020.util :refer [file->seq]]))

(defn partial-grid->grid
  [partial-grid-s]
  (->> partial-grid-s
       (mapv (fn [row] (cycle row)))))

(defn position-s
  [down-slope right-slope]
  (let [row-s (take-nth down-slope (range))
        col-s (take-nth right-slope (range))]
    (pmap vector row-s col-s)))

(defn cur-pos-object
  [grid [row col]]
  (-> grid
      (nth row)
      (nth col)))

(defn total-tree-cnt
  [partial-grid-s & {:keys [right-slp down-slp]}]
  (let [row-size   (count partial-grid-s)
        grid       (partial-grid->grid partial-grid-s)
        coordinate (position-s down-slp right-slp)]
    (->> coordinate
         (take-while (fn [pos] (< (first pos) row-size)))
         (mapv (fn [pos]
                 (cur-pos-object grid pos)))
         (filter (fn [object] (= \# object)))
         (remove false?)
         (count))))

(defn tree-encounter-multiply
  [partial-grid-s]
  (->> (vector
         (total-tree-cnt partial-grid-s :right-slp 1 :down-slp 1)
         (total-tree-cnt partial-grid-s :right-slp 3 :down-slp 1)
         (total-tree-cnt partial-grid-s :right-slp 5 :down-slp 1)
         (total-tree-cnt partial-grid-s :right-slp 7 :down-slp 1)
         (total-tree-cnt partial-grid-s :right-slp 1 :down-slp 2))
       (reduce *)))




(comment
  ;;------------------------------data------------------------------------------
  (do (def sample-partial-grid-s (file->seq "aoc2020/day3/input-sample.txt"))
      sample-partial-grid-s)
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


  (do (def partial-grid-s (file->seq "aoc2020/day3/input.txt"))
      partial-grid-s)
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

  ;;create infinite map by cycling each row of the parsed grid template
  ;;create infinite lazy seq for row and column coordinates
  ;;Based on the coordinates, find the object of the position
  ;;compare the object with tree character (\#) filter out the true value
  ;;count the true value

  (take 5 (position-s 2 5))
  #_=> ([0 0] [2 5] [4 10] [6 15] [8 20])


  (cur-pos-object ["..#.#.#"
                   ".#.##.#"
                   "...###."] [1 3])
  #_=> \#
  (cur-pos-object ["..#.#.#"
                   ".#.##.#"
                   "...###."] [2 6])
  #_=> \.

  (mapv (fn [coordinates]
          (cur-pos-object (partial-grid->grid sample-partial-grid-s) coordinates))
        [[0 0] [2 5] [4 10] [6 11] [8 20]])
  #_=> [\. \. \. \. \.]

  (total-tree-cnt ["..#.#.#"
                   ".#.##.#"
                   "...###."], :right-slp 3, :down-slp 1)
  #_=> 1


  (total-tree-cnt sample-partial-grid-s, :right-slp 3, :down-slp 1)
  #_=> 7

  (total-tree-cnt partial-grid-s, :right-slp 3, :down-slp 1)
  #_=> 220

  ;;-----------------------function eval part 2----------------------------------
  ;;calculate the product by multiplying the tree encounter using different slp

  ;;Parse different right and down slopes as args into tree-cnt function
  ;;Multiply the results

  (tree-encounter-multiply ["###.#.#"
                            ".####.#"
                            "######."])
  #_=> 48

  (tree-encounter-multiply sample-grid-s)
  #_=> 336

  (tree-encounter-multiply grid-s)
  #_=> 2138320800
  )
