(ns hasm.parser)

(def not-nil? (complement nil?))

(def c-instruction-dest #{"null" "M" "D" "MD" "A" "AM" "AD" "AMD"})
(def c-instruction-comp #{"0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" 
                          "A+1" "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A"})
(def c-instruction-jump #{"null" "JGT" "JEQ" "JGE" "JLT" "JNE" "JLE" "JMP"})

(defn is-c-calc-instruction? [s]
  "A c-instruction for calculaion has the form of dest=comp"
  (let [[lhs rhs] (clojure.string/split s #"=" 2)]
    (and (contains? c-instruction-dest lhs)
         (contains? c-instruction-comp rhs))))

(defn is-c-jump-instruction? [s]
  "A c-instruction for jumping has the form of dest;jump"
  (let [[lhs rhs] (clojure.string/split s #";")]
    (and (contains? c-instruction-dest lhs)
         (contains? c-instruction-jump rhs))))

 
(defn is-a-symbol? [s]
  (re-matches #"@[a-zA-Z\$_\.][0-9a-zA-Z\$_\.]*" s))

(defn is-a-constant? [s]
  (re-matches #"@\d+" s))
