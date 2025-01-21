(ns aoc.day2.core
  (:require [clojure.string :as str]
            [clojure.math :refer [signum]]))

(defn get-input [path]
  (->> (slurp path)
       str/split-lines
       (map #(str/split % #"\s+"))
       (map (fn [pair] (map #(Integer/parseInt %) pair)))))

(defn seq-diff [xs] (map - xs (rest xs)))

(defn safe-mag-step?
  "Checks if a single magnitude step is safe"
  [n]
  (let [abs-step (abs n)]
    (and (> abs-step 0) (<= abs-step 3))))

(defn safe-mag?
  "Checks if a sequence has safe magnitude steps by
  calculating the deltas in the sequence and checking
  that each step is safe."
  [xs]
  (if (seq xs)
    (let [deltas (seq-diff xs)]
      (every? true? (map safe-mag-step? deltas)))
    false))

(defn safe-direction?
  "Calculates the deltas of a sequence, the change
  between each element in the series, then analyzes
  the deltas and returns `true` if they are all positve
  or all negative. Returns `false` otherwise."
  [xs]
  (if (seq xs)
    (let [signs (map #(signum %) (seq-diff xs))]
      (or (every? pos? signs)
          (every? neg? signs)))
    false))

(defn safe?
  "Checks if a sequence is safe using
  safe-mag? and safe-direction?"
  [xs]
  (and (safe-mag? xs)
       (safe-direction? xs)))

(defn solve-p1
  "Map safe? onto input, filter just the true responses and count them."
  [path]
  (count (filter true? (map safe? (get-input path)))))

(defn with-dampened
  "Returns the original seq, along with all potential dampened variations."
  [xs]
  (cons xs
        (map-indexed
         (fn [i _]
           (keep-indexed
            (fn [j x] (when (not= i j) x))
            xs))
         xs)))

(defn safe-with-dampener?
  "Returns true if the sequence is safe or can be made safe by removing one element."
  [xs]
  (some safe? (with-dampened xs)))

(defn solve-p2 [path]
  (count (filter true? (map safe-with-dampener? (get-input path)))))

(solve-p1 "resources/day2/sample.input")
(solve-p1 "resources/day2/input")

(solve-p2 "resources/day2/sample.input")
(solve-p2 "resources/day2/input")

