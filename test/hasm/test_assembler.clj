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

(deftest generate-add
  ""
  (is (= (-> "add/Add.asm" get-tokens-from-resource generate-bytecode )
         (get-binary-from-resource "add/Add.hack"))))

(deftest generate-max-nosym
  ""
  (is (= (-> "max/MaxL.asm" get-tokens-from-resource generate-bytecode )
         (get-binary-from-resource "max/Max.hack"))))

(deftest generate-rect-nosym
  ""
  (is (= (-> "rect/RectL.asm" get-tokens-from-resource generate-bytecode )
         (get-binary-from-resource "rect/Rect.hack"))))

(deftest generate-pong-nosym
  ""
  (is (= (-> "pong/PongL.asm" get-tokens-from-resource generate-bytecode )
         (get-binary-from-resource "pong/Pong.hack"))))


(run-tests)


