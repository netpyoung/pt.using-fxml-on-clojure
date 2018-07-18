(ns gen-class-fxml.controller
  (:import [javafx.fxml FXML]
           [javafx.event.ActionEvent]))

(gen-class
  :name gen-class-fxml.controller.Controller
  :prefix "-"
  :main false

  ;; wrong!!
  ;;  :exposes {^{FXML []} lbl_label {:get lbl_label :set lbl_label}}
  :methods [[^{FXML []} onBtnUp [javafx.event.ActionEvent] void]
            [^{FXML []} onBtnDown [javafx.event.ActionEvent] void]])

(def count (atom 0))

;; wrong!!
;; (def ^{FXML []} -lbl_label nil)

;; wrong!!
;;(defn -initialize ^{FXML []} [this]
;;  (println "initialize"))

(defn -onBtnUp [this evt]
  ;;(.setText lbl_label (str @count))
  (println evt))

(defn -onBtnDown [this evt]
  ;;(.setText lbl_label (str @count))
  (println evt))