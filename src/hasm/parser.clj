(ns hasm.parser)

(def not-nil? (complement nil?))

(def c-instruction-dest #{"null" "M" "D" "MD" "A" "AM" "AD" "AMD"})
(def c-instruction-comp #{"0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" 
                          "A+1" "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A"})
(def c-instruction-jump #{"null" "JGT" "JEQ" "JGE" "JLT" "JNE" "JLE" "JMP"})

(defn split-calc-instruction [s]
  "Splits a c-instruction of form dest=comp into a its atoms"
  (clojure.string/split s #"=" 2))

(defn split-jump-instruction [s]
  "Splits a c-instruction of form dest;jump into its atoms"
  (clojure.string/split s #";" 2))

(defn split-a-instruction [s]
  "Returns the payload S of an a-instructions in the form of @S"
  (subs s 1))

(defn calc-instruction? [s]
  "A c-instruction for calculaion has the form of dest=comp"
  (let [[lhs rhs] (split-calc-instruction s)]
    (and (contains? c-instruction-dest lhs)
         (contains? c-instruction-comp rhs))))

(defn jump-instruction? [s]
  "A c-instruction for jumping has the form of dest;jump"
  (let [[lhs rhs] (split-jump-instruction s)]
    (and (contains? c-instruction-dest lhs)
         (contains? c-instruction-jump rhs))))

(defn const-instruction? [s]
  "A a-instruction representing a constant has the form of @N,
   where N is a non-negative integer"
  (not-nil? (re-matches #"@\d+" s)))

(defn symbol-instruction? [s]
  "A a-instruction representing a symbol has the form of @S,
   where S can be any sequence of letters, digits, underscores,
   dot, dollar sign and colon that does not start with a digit"
   (re-matches #"@[a-zA-Z\$_\.:][0-9a-zA-Z\$_\.:]*" s))
