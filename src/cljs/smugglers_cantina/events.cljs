(ns smugglers-cantina.events
  (:require
   [re-frame.core :as re-frame]
   [smugglers-cantina.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 ::set-name
 (fn-traced [db [_ character-name]]
            (assoc-in db [:character :name] character-name)))

(re-frame/reg-event-db
 ::set-species
 (fn-traced [db [_ species-key]]
            (assoc-in db [:character :species] species-key)))

(re-frame/reg-event-db
 ::set-career
 (fn-traced [db [_ career-key]]
            (update db
                    :character
                    (fn [character]
                      (-> character
                          (assoc :career career-key)
                          (dissoc :specialization))))))

(re-frame/reg-event-db
 ::set-specialization
 (fn-traced [db [_ specialization-key]]
            (assoc-in db [:character :specialization] specialization-key)))

(re-frame/reg-event-db
 ::set-skill-rank
 (fn-traced [db [_ skill-key skill-value]]
            (assoc-in db
                      [:character :skills skill-key]
                      (max 0 (min skill-value 6)))))

(re-frame/reg-event-db
 ::add-additional-specialization
 (fn-traced [db _]
            (update-in db
                       [:character :additional-specializations]
                       (fn [specs]
                         (let [specs (or specs [])]
                           (conj specs :none))))))

(defn remove-item [v index]
  (vec
   (keep-indexed
    (fn [i item]
      (when (not= i index)
        item))
    v)))

(re-frame/reg-event-db
 ::remove-additional-specialization
 (fn-traced [db [_ index]]
            (update-in db
                       [:character :additional-specializations]
                       remove-item
                       index)))

(re-frame/reg-event-db
 ::set-additional-specialization
 (fn-traced [db [_ index specialization-key]]
            (assoc-in db
                      [:character :additional-specializations index]
                      specialization-key)))
