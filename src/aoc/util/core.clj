(ns aoc.util.core)

(def spy
  "Utility function for printing intermediate values inside a threading pipeline"
  #(do (println "DEBUG:" %) %))
