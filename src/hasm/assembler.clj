(ns hasm.assembler
  (:require [hasm.symtable :refer :all]
            [hasm.parser :refer :all]
            [hasm.codegen :refer :all]
            [clojure.java.io :as io]))

(defn generate-bytecode [token-list]
  "Returns a symbol table with the predefined symbols
   and the symbols which are in the token stream"
  (loop [tokens (remove label-instruction? token-list)
         address 16
         syms (fill-labels token-list)
         lst []]
    (let [[token & spare-tokens] tokens]
      (cond 
       (empty? tokens) lst
       (symbol-instruction? token) 
       (let [sym (split-symbol-instruction token)
             adr (get syms sym)]
         (if (nil? adr)
           (recur spare-tokens (inc address) (assoc syms sym address)
                  (conj lst (get-code (format "@%d" address ))))
           (recur spare-tokens address syms 
                  (conj lst (get-code (format "@%d" adr ))))))
       :else (recur spare-tokens address syms 
                    (conj lst (get-code token)))))))
       
(let [s (slurp (-> "rect/Rect.asm" io/resource io/file))
      tokens (get-tokens s)]
  (println (generate-bytecode tokens)))
