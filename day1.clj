(require '[clojure.string :as str])

(defn get-input [path] 
  ;; Returns the innput as a list of two lists where each
  ;; list correspondes to one column of the input
  (->> (slurp path) ;; Use threading macro to chain the operations
       str/split-lines ;; Start by splitting on lines in slurped file
       (map #(str/split % #"\s+")) ;; map split function and split on whitespace
       (map (fn [pair] (map #(Integer/parseInt %) pair))))) ;; Parse as int, use anonymous function to handle pairs 

( defn solve-p1 [path] 
  (->> (get-input path)
       (apply map list)
       (map sort)
       (apply map -)
       (map abs)
       (reduce +)))

(solve-p1 "day1part1.input") ; 2430334


;; TODO: Clean up above and use threading macros to improve readability
;; TODO: Add some defs to define day and part to automatically build
;;      the right path.
;;
;; When to use threading macro: https://stuartsierra.com/2018/07/06/threading-with-style
