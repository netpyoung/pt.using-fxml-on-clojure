(ns asm-insn.core
  (:gen-class)
  (:import
   [java.io PrintStream])
  (:require
   [insn.core :as insn]))

(def class-data
  {:name 'example.HelloWorld
   :methods [{:flags #{:public :static}
              :name "hello"
              :desc [:void]
              :emit [[:getstatic System "out"]
                     [:ldc "HelloWorld"]
                     [:invokevirtual PrintStream "println" [String :void]]
                     [:return]]}]})

(def class-object (insn/define class-data))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (example.HelloWorld/hello)
  (println "Done"))
