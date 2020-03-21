(ns smugglers-cantina.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [smugglers-cantina.events :as events]
   [smugglers-cantina.routes :as routes]
   [smugglers-cantina.views :as views]
   [smugglers-cantina.config :as config]
   [smugglers-cantina.auth :as auth]))

(prn "JUANCHO")

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (prn "MOUNT ROOT")
  (re-frame/clear-subscription-cache!)
  (prn "CLEARED")
  (rdom/render [views/main-panel]
               (.getElementById js/document "app")))

(defn init []
  #_(routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))

