(ns aoc.day2.core
  (:require [clojure.string :as str]
            [clojure.math :refer [signum]]))

(defn get-input [path]
  ;; Returns the innput as a list of two lists where each
  ;; list correspondes to one column of the input
  (->> (slurp path) ;; Use threading macro to chain the operations
       str/split-lines ;; Start by splitting on lines in slurped file
       (map #(str/split % #"\s+")) ;; map split function and split on whitespace
       (map (fn [pair] (map #(Integer/parseInt %) pair))))) ;; Parse as int, use anonymous function to handle pairs 

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

(defn solve-p1 [path]
  (map safe? (get-input path)))

(solve-p1 "resources/day2/day2sample.input")

