(ns deftype-fxml.controller
  (:import
   [java.net URL]
   [java.util ResourceBundle]
   [javafx.fxml FXMLLoader FXML]
   [javafx.event ActionEvent EventHandler]))


(defn helloworld []
  (println "hihihi"))


;; works.. but Are we need this interface for just for `one` controller?
(definterface IController
  (^{FXML [] :tag void} initialize [])
  (^{FXML [] :tag void} onBtnUp [^javafx.event.ActionEvent event])
  (^{FXML [] :tag void} onBtnDown [^javafx.event.ActionEvent event]))


(deftype Controller
    [state
     ^{FXML [] :unsynchronized-mutable true} lbl_count]

  IController
  (^{FXML [] :tag void} initialize [this]
   (.setText lbl_count (str (:count @state))))

  (^{FXML [] :tag void} onBtnUp [this ^javafx.event.ActionEvent event]
   (swap! state update-in [:count] inc)
   (.setText lbl_count (str (:count @state))))

  (^{FXML [] :tag void} onBtnDown [this ^javafx.event.ActionEvent event]
   (swap! state update-in [:count] dec)
   (helloworld)
   (.setText lbl_count (str (:count @state)))))


(defn new-controller []
  (->Controller
   (atom {:count 0})
   nil ;; lbl_count
   ))
