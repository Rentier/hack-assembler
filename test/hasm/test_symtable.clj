(ns hasm.test_symtable
  (:require [clojure.test :refer :all]
            [hasm.symtable :refer :all]
            [clojure.java.io :as io]))

(defn read-tokens-from-file [f]
  ( hasm.parser/get-tokens (slurp (-> f io/resource io/file))))

(deftest symtable-max
  "Checks whether the labels in symtable of the first pass
   for Max.asm is equal to the expected one."
  (let [syms (-> "max/Max.asm" read-tokens-from-file fill-labels)]
    (is (= (syms "OUTPUT_FIRST") 10))
    (is (= (syms "OUTPUT_D") 12))
    (is (= (syms "INFINITE_LOOP") 14))))

(deftest symtable-rect
  "Checks whether the labels in symtable of the first pass
   for Rect.asm is equal to the expected one."
  (let [syms (-> "rect/Rect.asm" read-tokens-from-file fill-labels)]
    (is (= (syms "INFINITE_LOOP") 23))
    (is (= (syms "LOOP") 10))))

(run-tests)
