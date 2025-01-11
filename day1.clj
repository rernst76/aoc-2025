(require '[clojure.string :as str])

(defn get-input [path] 
  ;; Returns the innput as a list of two lists where each
  ;; list correspondes to one column of the input
  (map (fn [pair] (map #(Integer/parseInt %) pair))
  (map #(str/split % #"\s+") (str/split-lines (slurp path))))
)

(let [input (get-input "day1part1.input")]
  (apply + (map abs (apply map - (map sort (apply map list input)) )) )
) ; 2430334


;; TODO: Clean up above and use threading macros to improve readability
;; TODO: Add some defs to define day and part to automatically build
;;      the right path.
;;
;; When to use threading macro: https://stuartsierra.com/2018/07/06/threading-with-style
