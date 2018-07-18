(ns gen-class-fxml.core
  (:require [gen-class-fxml.controller])
  (:import [javafx.application Application]
           [javafx.fxml FXMLLoader]
           [javafx.scene Scene]
           [javafx.stage Stage]
           [gen-class-fxml.controller Controller])
  (:gen-class
   :extends javafx.application.Application))


(defn -main
  [& args]
  (Application/launch gen-class-fxml.core args))

(defn -start [this ^Stage stage]
  (let [loader (FXMLLoader.)
        res (clojure.java.io/resource "fxmain.fxml")
        _ (.setLocation loader res)
        _ (.setController loader (Controller.))
        root (.load loader)]
    (.setScene stage (Scene. root))
    (.show stage)))