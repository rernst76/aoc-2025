(ns aoc.day3.core
  (:require [clojure.string :as str]
            [aoc.util.core :refer [spy]]))

(defn get-input [path]
  (slurp path))

(defn get-valid-mul-operations [s]
  (re-seq #"mul\(\d+,\d+\)" s))

(defn prepare-mul-operation [s]
  (str/replace s #"mul\(" "(* "))

(defn solve-p1 [path]
  (->> (get-input path)
       (get-valid-mul-operations)
       (map prepare-mul-operation)
       (map load-string)
       (reduce +)))

(defn get-valid-operations [s]
  (re-seq #"mul\(\d+,\d+\)|do\(\)|don't\(\)" s))

(solve-p1 "resources/day3/input")

(defn do? [s] (= s "do()"))
(defn don't? [s] (= s "don't()"))
(defn do-don't? [s] (or (do? s) (don't? s)))
(defn update-do-state
  "Checks if `op` is a do/don't op and upldates `cur-do-state` accordingly."
  [cur-do-state op]
  (if (do-don't? op)
    op
    cur-do-state))

(defn filter-dos
  "Removes operations that should not be included based on
  do/don't operations within the sequence. Operations before any do/don't
  are included."
  [xs]
  (first
   (reduce
    (fn [[acc do-state] op]
      (if (do-don't? op)
        [acc (update-do-state do-state op)]
        (if (do? do-state)
          [(conj acc op) do-state]
          [acc do-state])))
    [[] "do()"]
    xs)))

(defn solve-p2 [path]
  (->> (get-input path)
       (get-valid-operations) ;; Use regex to filter out valid ops
       (map prepare-mul-operation) ;; Convert mul ops to clojure expressions
       (filter-dos) ;; Use filter-dos to remove all ops that should not be evaluated
       (map load-string) ;; Evaluate remaining expressions
       (reduce +))) ;; Get sum

(solve-p2 "resources/day3/input")



