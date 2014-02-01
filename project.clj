(defproject hasm "0.1.0-SNAPSHOT"
  :description "Hack ASM - Simple Hack assembler"
  :url "https://github.com/Rentier/hack-assembler"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot hasm.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
