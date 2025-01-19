(ns aoc.day2.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc.day2.core :refer [safe-direction? safe-mag?]]))

(deftest test-safe-direction?
  (testing "all positive changes"
    (is (true? (safe-direction? [3 4 5 10 13])))
    (is (true? (safe-direction? [1]))))

  (testing "all negative changes"
    (is (true? (safe-direction? [13 12 11 10 -100])))
    (is (true? (safe-direction? [-1]))))

  (testing "mixed changes return false"
    (is (false? (safe-direction? [5 10 5])))
    (is (false? (safe-direction? [0 -10 -5]))))

  (testing "zero changes return false"
    (is (false? (safe-direction? [1 1 1])))
    (is (false? (safe-direction? [])))))

(deftest test-safe-mag?
  (testing "all safe deltas return true"
    (is (true? (safe-mag? [1 2 5 8])))
    (is (true? (safe-mag? [100 98 96 94])))
    (is (true? (safe-mag? [-1 0 2 5])))
    (is (true? (safe-mag? [-1 2 3 4])))
    (is (true? (safe-mag? [1 2 3 4]))))
  (testing "all unsafe deltas return false"
    (is (false? (safe-mag? [1 10 15 20])))
    (is (false? (safe-mag? [-10 -5 0 100])))
    (is (false? (safe-mag? [0 0 1 10]))))
  (testing "mixed safe deltas return false"
    (is (false? (safe-mag? [1 2 10 12])))
    (is (false? (safe-mag? [-10 -9 0 1])))
    (is (false? (safe-mag? [0 0 1 10]))))
  (testing "empty seq returns false"
    (is (false? (safe-mag? [])))))
