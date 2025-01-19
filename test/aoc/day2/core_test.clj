(ns aoc.day2.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc.day2.core :refer [safe-direction?]]))

(deftest test-safe-direction?
  (testing "all positive changes"
    (is (true? (safe-direction? [2 2 1 3])))
    (is (true? (safe-direction? [1]))))

  (testing "all negative changes"
    (is (true? (safe-direction? [-2 -1 -3])))
    (is (true? (safe-direction? [-1]))))

  (testing "mixed changes return false"
    (is (false? (safe-direction? [1 -1])))
    (is (false? (safe-direction? [-2 1 3]))))

  (testing "zero changes return false"
    (is (false? (safe-direction? [0 0])))
    (is (false? (safe-direction? [1 0 1])))))
