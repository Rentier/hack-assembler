(ns hasm.symtable
  (:require [hasm.parser :refer :all]))

(def prefilled-symtable
  {"SP" 0 "LCL" 1 "ARG" 2 "THIS" 3 "THAT" 4 "SCREEN" 16384 "KBD" 24576 
   "R0" 0 "R1" 1 "R2" 2 "R3" 3 "R4" 4 "R5" 5 "R6" 6 "R7" 7 "R8" 8 "R9" 9
   "R10" 10 "R11" 11 "R12" 12 "R13" 13 "R14" 14 "R15" 15})
  
(defn fill-symtable [token-list]
  "Returns a symbol table with the predefined symbols
   and the symbols which are in the token stream"
  (loop [symtable prefilled-symtable
         counter 0
         tokens token-list]
    (let [[token & spare-tokens] tokens]
      (cond 
       (empty? tokens) symtable
       (label-instruction? token) 
       (recur (assoc symtable (split-label-instruction token) counter)
              counter spare-tokens)
       (symbol-instruction? token)
       (recur (assoc symtable (split-label-instruction token) counter)
              (inc counter) spare-tokens)
       :else (recur symtable (inc counter) spare-tokens)))))
      
