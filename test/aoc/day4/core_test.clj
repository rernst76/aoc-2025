(ns aoc.day4.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc.day4.core :refer [pattern?
                                   backward-pattern?
                                   forward-pattern?
                                   x-mas-pattern?
                                   N E S W NE NW SE SW NOP]]))

(deftest pattern-test
  (let [test-grid [[\M \M \M \S]
                   [\M \A \X \A]
                   [\S \A \S \A]
                   [\S \A \M \A]]]

    (testing "basic pattern matches"
      (is (pattern? test-grid 1 1 "MAA" [N NOP S]))
      (is not (pattern? test-grid 1 1 "MAS" [N NOP S])))
    (testing "edge cases"
      (is not (pattern? test-grid 0 0 "SAM" [N]))
      (is not (pattern? test-grid 0 0 "SAM" [N NOP S]))
      (is (pattern? test-grid 0 0 "M" [NOP]))
      (is not (pattern? test-grid 0 0 "M" [NOP])))
    (testing "other shapes"
      (is (pattern? test-grid 1 1 "MMMXSASM" [NW N NE E SE S SW W]))))) ;; Box

(deftest diagonal-pattern-tests
  (let [test-grid [[\M \A \M \.]
                   [\A \M \A \M]
                   [\M \A \A \A]
                   [\. \S \A \S]]]

    (testing "backward-pattern?"
      (is (backward-pattern? test-grid 1 1 "MMA"))
      (is (not (backward-pattern? test-grid 1 1 "AXM")))
      (is (not (backward-pattern? test-grid 0 0 "MAS"))))

    (testing "forward-pattern?"
      (is (forward-pattern? test-grid 1 1 "MMM"))
      (is (not (forward-pattern? test-grid 1 1 "SAM")))
      (is (not (forward-pattern? test-grid 3 3 "MAS"))))

    (testing "x-mas-pattern?"
      (is (x-mas-pattern? test-grid 2 2))
      (is (not (x-mas-pattern? test-grid 1 1)))
      (is (not (x-mas-pattern? test-grid 0 0))))))
