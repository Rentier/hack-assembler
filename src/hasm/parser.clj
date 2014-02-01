(ns hasm.parser)

(def not-nil? (complement nil?))

(def dest #{"null" "M" "D" "MD" "A" "AM" "AD" "AMD"})
(def comp #{"0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" 
           "A+1" "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A"})

(defn is-c-calc? [s]
  "A C-instruction for calculaion can be one of the following:
   dest=x+y dest=x-y dest=x dest=0 dest=1 dest=-1

   With x={A,D,M} y={A,D,M,1} dest={A,D,M,MD,A,AM,AD,AMD,null}"
  (let [[lhs rhs] (clojure.string/split s #"=" 2)]
    (and (contains? dest lhs)
         (contains? comp rhs))))
 
(defn is-a-symbol? [s]
  (re-matches #"@[a-zA-Z\$_\.][0-9a-zA-Z\$_\.]*" s))

(defn is-a-constant? [s]
  (re-matches #"@\d+" s))
