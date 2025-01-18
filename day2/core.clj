(ns day2.core

  (defn get-input [path]
  ;; Returns the innput as a list of two lists where each
  ;; list correspondes to one column of the input
    (->> (slurp path) ;; Use threading macro to chain the operations
         str/split-lines ;; Start by splitting on lines in slurped file
         (map #(str/split % #"\s+")) ;; map split function and split on whitespace
         (map (fn [pair] (map #(Integer/parseInt %) pair))))) ;; Parse as int, use anonymous function to handle pairs 

  (defn seq-diff [x] (map - x (rest x)))

  (defn safe-magnitude? [x] (and (> x 0) (<= x 3)))

  (defn safe-direction? [x] (= (count x)
                               (->> (map #(/ % %))
                                    (map abs)
                                    (reduce +))))

  (defn solve-p1 [path]
    (->> (get-input path)
         (map seq-diff)
         (spy)
         (map (partial map safe-magnitude?))))

  (solve-p1 "day2/day2sample.input"))
