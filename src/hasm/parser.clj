(ns hasm.parser)

(def not-nil? (complement nil?))

(defn is-c-calc? [s]
  "A C-instruction for calculaion can be one of the following:
   dest=x+y dest=x-y dest=x dest=0 dest=1 dest=-1

   With x={A,D,M} y={A,D,M,1} dest={A,D,M,MD,A,AM,AD,AMD,null}"
  (not-nil? 
   (re-matches #"(A|D|M|MD|A|AM|AD|AMD|null)=([ADM][\+-][ADM1])|(0|1|-1)" s)))
 
(defn is-a-symbol? [s]
  (re-matches #"@[a-zA-Z\$_\.][0-9a-zA-Z\$_\.]*" s))

(defn is-a-constant? [s]
  (re-matches #"@\d+" s))
