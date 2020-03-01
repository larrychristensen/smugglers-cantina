(ns smugglers-cantina.core
  (:require
   [reagent.core :as reagent]
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
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  #_(routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))

