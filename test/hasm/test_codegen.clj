(ns hasm.test_codegen
  (:require [clojure.test :refer :all]
            [hasm.codegen :refer :all]))

(deftest test-constant-to-binary
  "Checks whether the conversions from constants, e.g. @1335
   to binary is correct"
  (is (= (get-code     "@0") "0000000000000000"))
  (is (= (get-code    "@42") "0000000000101010"))
  (is (= (get-code  "@1337") "0000010100111001"))
  (is (= (get-code "@16384") "0100000000000000")))

(deftest test-calc-to-binary
  "Checks whether the conversions from calc instructions
   to binary, e.g. D=M-1, is correct."
  (is (= (get-code "M=1") "1110111111001000"))
  (is (= (get-code "M=0") "1110101010001000"))
  (is (= (get-code "D=M") "1111110000010000"))
  (is (= (get-code "D=D-A") "1110010011010000"))
  (is (= (get-code "M=D+M") "1111000010001000")))

(deftest test-jump-to-binary
  "Checks whether the conversions from jump instructions
   to binary, e.g. D;JGT, is correct."
  (is (= (get-code "D;JGT") "1110001100000001"))
  (is (= (get-code "0;JMP") "1110101010000111")))
  
(run-tests)
