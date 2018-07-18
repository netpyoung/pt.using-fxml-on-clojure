(ns deftype-fxml.core
  (:gen-class)
  (:require
   [system.repl :refer [set-init! start reset]]
   [deftype-fxml.systems :refer [prod-system]]))

(defn -main
  [& args]
  (set-init! #'prod-system)
  (start)
  @(promise))
