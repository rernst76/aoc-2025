(require '[clojure.string :as str])

(defn get-input [path] 
  ;; Returns the innput as a list of two lists where each
  ;; list correspondes to one column of the input
  (map (fn [pair] (map #(Integer/parseInt %) pair))
  (map #(str/split % #"\s+") (str/split-lines (slurp path))))
)

(let [input (get-input "day1part1sample.input")]
)
