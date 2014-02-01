(ns hasm.test_parser
  (:require [clojure.test :refer :all]
            [hasm.parser :refer :all]))

(deftest split-calc-instructions
  "Checks whether calc-instructions are split the right way"
  (is (split-calc-instruction "D=M") ["D" "M"])
  (is (split-calc-instruction "null=null") ["null" "null"])
  (is (split-calc-instruction "M=!A") ["M" "!A"])
  (is (split-calc-instruction "D=D&A") ["D" "D&A"]))

(deftest split-jump-instructions
  "Checks whether jump-instructions are split the right way"
  (is (split-jump-instruction "null;JGT") ["null" "JGT"])
  (is (split-jump-instruction "AMD;JMP") ["AMD" "JMP"])
  (is (split-jump-instruction "MD;JGE") ["MD" "JGE"])
  (is (split-jump-instruction "M;JEQ") ["M" "JEQ"]))

(deftest split-a-instructions
  "Checks whether a-instructions are split the right way"
  (is (split-a-instruction "@FOO") "FOO")
  (is (split-a-instruction "@123") "123"))  

(deftest recognize-calc-instructions
  "Checks whether all possible c-calc-instructions are recognized."
  (doseq [lhs ["null" "M" "D" "MD" "A" "AM" "AD" "AMD"]]
    (doseq [rhs ["0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" 
                 "A+1" "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A"]]
      (is (calc-instruction? (str lhs "=" rhs))))))

(deftest recognize-jump-instructions
  "Checks whether all possible c-jump-instructions are recognized."
  (doseq [lhs ["null" "M" "D" "MD" "A" "AM" "AD" "AMD"]]
    (doseq [rhs  ["null" "JGT" "JEQ" "JGE" "JLT" "JNE" "JLE" "JMP"]]
      (is (jump-instruction? (str lhs ";" rhs))))))

(deftest recognize-const-instructions
  "Checks whether the classification of a-const instructions 
   into valid and invalid ones is correct"
  (is (const-instruction? "@0"))
  (is (const-instruction? "@1"))
  (is (const-instruction? "@42"))
  (is (const-instruction? "@360"))
  (is (const-instruction? "@1337"))
  (is (not (const-instruction? "@-1")))
  (is (not (const-instruction? "1")))
  (is (not (const-instruction? "@LOOP")))
  (is (not (const-instruction? "@"))))

(deftest recognize-symbol-instructions
  "Checks whether the classification of a-symbol 
   instructions into valid and invalid ones is correct"
  (is (symbol-instruction? "@LOOP"))
  (is (symbol-instruction? "@foo"))
  (is (symbol-instruction? "@R1"))
  (is (symbol-instruction? "@ball.setdestination$if_true0"))
  (is (symbol-instruction? "@END_EQ"))
  (is (symbol-instruction? "@:_$Xy4"))
  (is (symbol-instruction? "@_$Xy4:"))
  (is (symbol-instruction? "@$Xy4:_"))
  (is (symbol-instruction? "@Xy4:_$"))
  (is (symbol-instruction? "@y4:_$X"))
  (is (not (symbol-instruction? "@4:_$Xy")))
  (is (not (symbol-instruction? "@2CHAINZ"))))

(deftest recognize-label-instructions
  "Checks whether the classification of label
   instructions into valid and invalid ones is correct"
  (is (label-instruction? "(LOOP)"))
  (is (label-instruction? "(foo)"))
  (is (label-instruction? "(R1)"))
  (is (label-instruction? "(ball.setdestination$if_true)"))
  (is (label-instruction? "(END_EQ)"))
  (is (label-instruction? "(:_$Xy4)"))
  (is (label-instruction? "(_$Xy4:)"))
  (is (label-instruction? "($Xy4:_)"))
  (is (label-instruction? "(Xy4:_$)"))
  (is (label-instruction? "(y4:_$X)"))
  (is (not (label-instruction? "(4:_$Xy)"))
  (is (not (label-instruction? "(2CHAINZ)")))))

(run-tests)
