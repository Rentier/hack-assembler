(ns hasm.parser)

(def c-instruction-dest #{"null" "M" "D" "MD" "A" "AM" "AD" "AMD"})
(def c-instruction-comp #{"0" "1" "-1" "D" "A" "!D" "!A" "-D" "-A" "D+1" "A+1"
                          "D-1" "A-1" "D+A" "D-A" "A-D" "D&A" "D|A" "M" "!M" 
                          "-M" "M+1" "M-1" "D+M" "D-M" "M-D" "D&M" "D|M"})

(def c-instruction-jump #{"null" "JGT" "JEQ" "JGE" "JLT" "JNE" "JLE" "JMP"})

(def not-nil? (complement nil?))
(def not-empty? (complement empty?))

(defn remove-comments [s]
  (clojure.string/replace s #"//.*" ""))

(defn map-then-filter [map-fun filter-fun x]
  "First maps, then filteres x"
  (->> x (map map-fun) (filter filter-fun)))

(defn get-tokens [s]
  (let [tokens (clojure.string/split-lines s)]
    (map-then-filter 
     #(-> % remove-comments clojure.string/trim) 
     #(and (not-nil? %) (not-empty? %))
     tokens)))

(defn split-calc-instruction [s]
  "Splits a c-instruction of form dest=comp into a its atoms"
  (clojure.string/split s #"=" 2))

(defn split-jump-instruction [s]
  "Splits a c-instruction of form comp;jump into its atoms"
  (clojure.string/split s #";" 2))

(defn split-symbol-instruction [s]
  "Returns the payload S of an a-instructions in the form of @S"
  (subs s 1))

(defn split-label-instruction [s]
  "Returns the payload S of an a-instructions in the form of (S)"
  (subs s 1 (dec (count s))))

(defn calc-instruction? [s]
  "A c-instruction for calculaion has the form of dest=comp"
  (let [[lhs rhs] (split-calc-instruction s)]
    (and (contains? c-instruction-dest lhs)
         (contains? c-instruction-comp rhs))))

(defn jump-instruction? [s]
  "A c-instruction for jumping has the form of comp;jump"
  (let [[lhs rhs] (split-jump-instruction s)]
    (and (contains? c-instruction-comp lhs)
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

(defn label-instruction? [s]
  "A label-instruction representing a label has the form of (S),
   where S is a symbol. For its definition, refer to 
   'symbol-instruction'"
  (not-nil? (re-matches #"\([a-zA-Z\$_\.:][0-9a-zA-Z\$_\.:]*\)" s)))
