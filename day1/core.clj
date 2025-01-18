(ns day1.core
  (require '[clojure.string :as str])

;; References used:
;; When to use threading macro: https://stuartsierra.com/2018/07/06/threading-with-style

  (defn get-input [path]
  ;; Returns the innput as a list of two lists where each
  ;; list correspondes to one column of the input
    (->> (slurp path) ;; Use threading macro to chain the operations
         str/split-lines ;; Start by splitting on lines in slurped file
         (map #(str/split % #"\s+")) ;; map split function and split on whitespace
         (map (fn [pair] (map #(Integer/parseInt %) pair))))) ;; Parse as int, use anonymous function to handle pairs 

  (defn solve-p1 [path]
    (->> (get-input path) ;; Get input as list of lists (tuples)
         (apply map list) ;; Use apply map to build ((list a) (list b)) form
         (map sort) ;; Map sort onto both lists to get list of two sorted lists
         (apply map -) ;; Calculate distances by apply mapping '-' operator onto the lists
         (map abs) ;; Take abs of the calculated distances
         (reduce +))) ;; Reduce with '+' operator to get answer

  (solve-p1 "day1/day1part1.input") ; 2430334

  (defn seq-count
    "Counts occurences in a sequence"
  ;; could have used 'frequencies' but made my own for learning
    [xs]
    (reduce
     (fn [counts, y] (assoc counts y (inc (get counts y 0))))
     {}   ; initial value, just an empty map
     xs)) ; sequence to count

  (defn solve-p2 [path]
    (let [[left right] ;; bind left, right, input using destructuring see: https://clojuredocs.org/clojure.core/let#example-542692c7c026201cdc3269c4
          (->> (get-input path)
               (apply map list))
          counts (seq-count right)] ;; bind map of counts from right list to counts using seq-count 
      ;; Map an anonymous function that calculates similarity onto left list, then reduce with '+'
      ;; to get answer
      (reduce + (map
                 (fn [lval] (* lval (get counts lval 0)))
                 left))))

  (solve-p2 "day1/day1part1.input")) ; 28786472

