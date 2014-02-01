(ns hasm.assembler
  (:require [hasm.symtable :refer :all]
            [hasm.parser :refer :all]
            [clojure.java.io :as io]))

(defn map-then-filter [map-fun filter-fun x]
  "First maps, then filteres x"
  (->> x (map map-fun) (filter filter-fun)))

(defn get-tokens [s]
  (let [tokens (clojure.string/split s #"\n")]
    (map-then-filter 
     #(-> % remove-comments clojure.string/trim) 
     #(and (not-nil? %) (not-empty? %))
     tokens)))

(let [s (slurp (-> "max/Max.asm" io/resource io/file))
      tokens (get-tokens s)]
  (println (fill-symtable tokens)))
