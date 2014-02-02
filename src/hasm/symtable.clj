(ns hasm.symtable
  (:require [hasm.parser :refer :all]))

(def prefilled-symtable
  {"SP" 0 "LCL" 1 "ARG" 2 "THIS" 3 "THAT" 4 "SCREEN" 16384 "KBD" 24576 
   "R0" 0 "R1" 1 "R2" 2 "R3" 3 "R4" 4 "R5" 5 "R6" 6 "R7" 7 "R8" 8 "R9" 9
   "R10" 10 "R11" 11 "R12" 12 "R13" 13 "R14" 14 "R15" 15})

(defn add-label [syms token address]
  (assoc syms (split-label-instruction token) address))

(defn add-symbol [syms token address]
  (assoc syms (split-symbol-instruction token) address))
  
(defn fill-labels [ token-list]
  "Fills the symbol table with labels and their respective ROM address"
  (loop [syms prefilled-symtable
         counter 0
         tokens token-list]  
    (let [[token & spare-tokens] tokens]
      (cond 
       (empty? tokens) syms
       (contains? syms token) (Exception. "Label was defined more than once!")
       (label-instruction? token) 
         (recur (add-label syms token counter) counter spare-tokens)
       :else (recur syms (inc counter) spare-tokens)))))

