(ns aoc.day4.core
  (:require [clojure.string :as str]
            [aoc.util.core :refer [spy]]))

(def input "resources/day4/input")
(def test-input "resources/day4/sample.input")

(defn get-input [path]
  (->>
   (slurp path)
   (str/split-lines)
   (map vec)

   vec))

;; Define compass transformations
(defn N [x y] [x (dec y)])
(defn S [x y] [x (inc y)])
(defn E [x y] [(inc x) y])
(defn W [x y] [(dec x) y])
(defn NW [x y] [(dec x) (dec y)])
(defn NE [x y] [(inc x) (dec y)])
(defn SW [x y] [(dec x) (inc y)])
(defn SE [x y] [(inc x) (inc y)])
(def directions {:N N, :S S, :E E, :W W, :NE NE, :SE SE, :SW SW, :NW NW})

(defn valid-pos?
  "Check if '[x y]' is a valid xy index within `mat`"
  [mat x y]
  (and (>= x 0)
       (>= y 0)
       (< y (count mat))
       (< x (count (get mat 0)))))

(defn at
  "Get the value within `mat` at `[x y]`, return nil if `[x y]` is not a valid
 position."
  [mat x y]
  (when (valid-pos? mat x y)
    (get (get mat y) x)))

(defn get-neighbor
  "Get a neighbor of `[x y]` within `mat` in `dir`direction."
  [mat direction x y]
  (let [[nx ny] (direction x y)] ;; use provided dir fn to get neighbor index
    (when (valid-pos? mat nx ny) ;; Confirm [nx ny] is a valid position
      {:val (at mat nx ny) ;; return map with neighbor details
       :pos [nx ny]})))

(defn all-neighbors
  "Get all neighbors of `[x y]` within `mat`."
  [mat x y]
  (->> directions ;; Get all directions
       (keep (fn [[_ transform]] ;; like map, but ignores nils
               (get-neighbor mat transform x y))) ;; map get-neighbor onto all dirs
       (into []))) ;; put results into vector

(defn make-pattern-map
  "Creates a map with character transitions within a string. Used for
  capturing the sequene of chars within a word to use for searching."
  [word]
  (->> (partition 2 1 word)
       (map vec)
       (into {})))

(def xmas-pattern (make-pattern-map "XMAS"))

(defn find-starting-positions
  "Find all [x y] indices of `target-char` in `mat`"
  [mat target-char]
  (for [y (range (count mat))
        x (range (count (first mat)))
        :when (= (at mat x y) target-char)]
    [x y]))

(defn find-matching-neighbors [mat [x y] target-char]
  (->> (all-neighbors mat x y)
       (filter #(= target-char (:val %)))))

(defn find-path
  [mat [x y] direction [current-char & remaining-chars]]
  (if (empty? remaining-chars)
    1;
    (let [next-char (get xmas-pattern current-char)
          [next-x next-y] (direction x y)]
      (if (= (at mat next-x next-y) next-char)
        (find-path mat [next-x next-y] direction remaining-chars)
        0))))

(defn count-xmas [mat]
  (->> (find-starting-positions mat \X)
       (mapcat (fn [pos]
                 (map #(find-path mat pos % "XMAS")
                      (vals directions))))
       (reduce + 0)))

(count-xmas (get-input test-input))
(count-xmas (get-input input))






