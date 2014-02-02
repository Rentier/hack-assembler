(ns hasm.test_assembler
  (:require [clojure.test :refer :all]
            [hasm.assembler :refer :all]
            [hasm.parser :refer :all]
            [clojure.java.io :as io]))

(defn get-tokens-from-resource [f]
  (get-tokens (slurp (-> f io/resource io/file))))

(defn get-binary-from-resource [f]
  (with-open [rdr (-> f io/resource io/file io/reader)]
    (doall (line-seq rdr))))

(defn code-identical? [asm hack]
  (= (-> asm get-tokens-from-resource generate-bytecode )
     (get-binary-from-resource hack)))


(deftest generate-binary-from-source-nosym
  "Given an *.asm file with symbols already replaced, check
   whether the generated binary code is identical to a known
   to be correct version"
  (is (code-identical? "add/Add.asm" "add/Add.hack"))
  (is (code-identical? "max/MaxL.asm" "max/Max.hack"))
  (is (code-identical? "rect/RectL.asm" "rect/Rect.hack"))
  (is (code-identical? "pong/PongL.asm" "pong/Pong.hack")))

(deftest generate-binary-from-source
  "Given an *.asm file, check whether the generated binary 
   code is identical to a known to be correct version"
  (is (code-identical? "max/Max.asm" "max/Max.hack")))
  


(run-tests)


