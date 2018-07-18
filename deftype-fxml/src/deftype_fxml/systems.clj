(ns deftype-fxml.systems
  (:require
   [clojure.java.io :as io]
   [system.core :refer [defsystem]]
   [com.stuartsierra.component :as component]
   [deftype-fxml.controller :refer [new-controller]])
  (:import
   [javafx.fxml FXMLLoader FXML]
   [javafx.stage Stage StageBuilder Modality]
   [javafx.scene Scene]
   [javafx.application Platform]
   [javafx.event ActionEvent EventHandler]
   ))

(defonce +ONCE+ (javafx.embed.swing.JFXPanel.))

(defrecord MainScene [is-dev _stage]
  component/Lifecycle
  (start [this]
    (Platform/setImplicitExit false)
    (Platform/runLater
     #(do
        (let [controller (new-controller)
              res (io/resource "fxmain.fxml")
              loader (FXMLLoader.)
              _ (.setLocation loader res)
              _ (.setController loader controller)
              root (.load loader)
              scene (doto (Scene. root)
                      (.. getStylesheets (add "global.css")))
              stage (doto (.build (StageBuilder/create))
                      (.setScene scene)
                      (.show)
                      (.setOnCloseRequest
                       (proxy [EventHandler] []
                         (handle [^ActionEvent event]
                           (when-not is-dev
                             (System/exit 0))))))]
          (reset! _stage stage))))
    this)

  (stop [this]
    (Platform/runLater
     #(do
        (.close @_stage)))
    this))

(defsystem dev-system
  [:ui (map->MainScene {:is-dev true :_stage (atom nil)})])

(defsystem prod-system
  [:ui (map->MainScene {:is-dev false :_stage (atom nil)})])
