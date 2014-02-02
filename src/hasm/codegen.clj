(ns hasm.codegen
  (:require [hasm.parser :refer :all]))

(def comp-to-binary
  {"0" "0101010"
   "1" "0111111"
   "-1" "0111010"
   "D" "0001100"
   "A" "0110000"
   "!D" "0001101"
   "!A" "0110001"
   "-D" "0001111"
   "-A" "0110011"
   "D+1" "0011111"
   "A+1" "0110111"
   "D-1" "0001110"
   "A-1" "0110010"
   "D+A" "0000010"
   "D-A" "0010011"
   "A-D" "0000111"
   "D&A" "0000000"
   "D|A" "0010101"
   "M" "1110000"
   "!M" "1110001"
   "-M" "1110011"
   "M+1" "1110111"
   "M-1" "1110010"
   "D+M" "1000010"
   "D-M" "1010011"
   "M-D" "1000111"
   "D&M" "1000000"
   "D|M" "1010101"})

(def dest-to-binary
  {"null" "000"
   "M" "001"
   "D" "010"
   "MD" "011"
   "A" "100"
   "AM" "101"
   "AD" "110"
   "AMD" "111"})

(def jump-to-binary
  {"null" "000"
   "JGT" "001"
   "JEQ" "010"
   "JGE" "011"
   "JLT" "100"
   "JNE" "101"
   "JLE" "110"
   "JMP" "111"})

(defn generate-from-c [d c j]
  "Turns a c-instruction into its binary representation.
   It has the form of 111accccccdddjjj"
  (let [d-bin (get dest-to-binary d "000")
        c-bin (get comp-to-binary c "0000000")
        j-bin (get jump-to-binary j "000")]
    (str "111" c-bin d-bin j-bin)))

(defn gen-from-jump [token]
  (let [[c j] (split-jump-instruction token)]
    (generate-from-c :d c j)))

(defn gen-from-calc [token]
  (let [[d c] (split-calc-instruction token)]
    (generate-from-c d c :j)))

(defn gen-from-const [token]
  (let [n (-> token split-symbol-instruction Integer/parseInt)
        s (Integer/toBinaryString n)
        c (- 16 (count s))]
    (str (apply str (repeat c "0")) s)))

(defn get-code [token]
  (cond
    (const-instruction? token) (gen-from-const token)
    (calc-instruction? token) (gen-from-calc token)
    (jump-instruction? token) (gen-from-jump token)
    :else (throw (Exception. (str "Invalid token: " token)))))
