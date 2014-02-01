(ns hasm.test_parser
  (:require [clojure.test :refer :all]
            [hasm.parser :refer :all]))

(deftest recognize-c-calc-instructions
  "Checks whether all possible c-calc-instructions are recognized."
  (doseq [lhs ["null" "M" "D" "MD" "A" "AM" "AD" "AMD"]]
    (doseq [rhs ["0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" 
                 "A+1" "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A"]]
      (is (is-c-calc-instruction? (str lhs "=" rhs))))))

(deftest recognize-c-jump-instructions
  "Checks whether all possible c-jump-instructions are recognized."
  (doseq [lhs ["null" "M" "D" "MD" "A" "AM" "AD" "AMD"]]
    (doseq [rhs  ["null" "JGT" "JEQ" "JGE" "JLT" "JNE" "JLE" "JMP"]]
      (is (is-c-jump-instruction? (str lhs ";" rhs))))))

(deftest recognize-a-const-instructions
  "Checks whether the classification of a-const instructions 
   into valid and invalid ones is correct"
  (is (is-a-const-instruction? "@0"))
  (is (is-a-const-instruction? "@1"))
  (is (is-a-const-instruction? "@42"))
  (is (is-a-const-instruction? "@360"))
  (is (is-a-const-instruction? "@1337"))
  (is (not (is-a-const-instruction? "@-1")))
  (is (not (is-a-const-instruction? "1")))
  (is (not (is-a-const-instruction? "@LOOP")))
  (is (not (is-a-const-instruction? "@"))))

(deftest recognize-a-symbol-instructions
  "Checks whether the classification of a-symbol 
   instructions into valid and invalid ones is correct"
  (is (is-a-symbol-instruction? "@LOOP"))
  (is (is-a-symbol-instruction? "@foo"))
  (is (is-a-symbol-instruction? "@R1"))
  (is (is-a-symbol-instruction? "@ball.setdestination$if_true0"))
  (is (is-a-symbol-instruction? "@END_EQ"))
  (is (is-a-symbol-instruction? "@:_$Xy4"))
  (is (is-a-symbol-instruction? "@_$Xy4:"))
  (is (is-a-symbol-instruction? "@$Xy4:_"))
  (is (is-a-symbol-instruction? "@Xy4:_$"))
  (is (is-a-symbol-instruction? "@y4:_$X"))
  (is (not (is-a-symbol-instruction? "@4:_$Xy")))
  (is (not (is-a-symbol-instruction? "@2CHAINZ"))))

(run-tests)
