(ns hasm.t_parser
  (:require [clojure.test :refer :all]
            [hasm.parser :refer :all]))

(deftest c-calc-instructions
  (doseq [lhs ["null" "M" "D" "MD" "A" "AM" "AD" "AMD"]]
    (doseq [rhs ["0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" 
                 "A+1" "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A"]]
      (is (is-c-calc? (str lhs "=" rhs))))))

(run-tests)
