(ns hasm.assembler
  (:require [hasm.symtable :refer :all]
            [hasm.parser :refer :all]
            [hasm.codegen :refer :all]
            [clojure.java.io :as io]))

(defn generate-bytecode [token-list]
  "Returns a symbol table with the predefined symbols
   and the symbols which are in the token stream"
  (loop [tokens token-list
         address 16
         syms (fill-labels token-list)
         lst []]
    (let [[token & spare-tokens] tokens]
      (cond 
       (empty? tokens) lst
       (symbol-instruction? token) (println token)
       :else (recur spare-tokens address syms 
                    (conj lst (get-code token)))))))
       
(let [s (slurp (-> "max/MaxL.asm" io/resource io/file))
      tokens (get-tokens s)]
  (println tokens)
  (println (generate-bytecode tokens)))
