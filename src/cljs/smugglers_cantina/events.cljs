(ns smugglers-cantina.events
  (:require
   [re-frame.core :refer [reg-event-db
                          reg-event-fx
                          reg-fx
                          reg-cofx
                          inject-cofx
                          dispatch]]
   [smugglers-cantina.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ["amazon-cognito-auth-js" :refer (CognitoAuth)]))

(reg-fx
 ::save-character
 (fn [value]
   ))

(reg-cofx
 :username
 (fn [coeffects _]
   (let [v (.getItem js/localStorage "username")]
     (prn "USERNAME" v)
     (assoc coeffects
            :username
            v))))

(reg-cofx
 :jwt-token
 (fn [coeffects _]
   (let [v (.getItem js/localStorage "jwt-token")]
     (prn "JWT OTKEN" v)
     (assoc coeffects
            :jwt-token
            v))))

(reg-cofx
 :local-store
 (fn [coeffects local-store-key]
   (assoc coeffects
          :local-store
          (js->clj (.getItem js/localStorage local-store-key)))))

(reg-cofx
  :auth
  (fn [cofx _]
    (let [auth-data {"ClientId" "4v2541jq6ghgahplpm6hhu3r4m"
                     "UserPoolId" "us-east-1_jCpRwHULy"
                     "RedirectUriSignIn" "http://localhost:8280"
                     "RedirectUriSignOut" "http://localhost:8280"
                     "AppWebDomain" "auth.smugglers-cantina.com"
                     "TokenScopesArray" ["email"]}

          auth (CognitoAuth. (clj->js auth-data))]
      (set! (. auth -userhandler)
            (set! (. auth -userhandler)
                  (clj->js {"onSuccess" (fn [result]
                                          (prn "ON SUCCESS" result)
                                          (dispatch [::login-success auth result]))
                            "onFailure" (fn [result]
                                          (dispatch [::login-failure result]))})))
      (assoc cofx :auth auth))))

(reg-cofx
 :load-auth
 (fn [cofx [_ a]]
   (prn "LOAD AUTH")
   (let [auth (:auth cofx)]
     #_(.getSession auth)
     (.parseCognitoWebResponse auth js/window.location.href)
     {})))

(reg-event-fx
 ::initialize-db
 [(inject-cofx :username)
  (inject-cofx :jwt-token)
  (inject-cofx :auth)]
 (fn-traced [cofx _]
            (.parseCognitoWebResponse (:auth cofx) js/window.location.href)
            (prn "COFX" cofx)
            {:db (assoc db/default-db
                        :username (:username cofx)
                        :jwt-token (:jwt-token cofx))}))

(reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(reg-event-db
 ::set-name
 (fn-traced [db [_ character-name]]
            (assoc-in db [:character :name] character-name)))

(reg-event-db
 ::set-species
 (fn-traced [db [_ species-key]]
            (assoc-in db [:character :species] species-key)))

(reg-event-db
 ::set-career
 (fn-traced [db [_ career-key]]
            (update db
                    :character
                    (fn [character]
                      (-> character
                          (assoc :career career-key)
                          (dissoc :specialization))))))

(reg-event-db
 ::set-specialization
 (fn-traced [db [_ specialization-key]]
            (assoc-in db [:character :specialization] specialization-key)))

(reg-event-db
 ::set-skill-rank
 (fn-traced [db [_ skill-key skill-value]]
            (assoc-in db
                      [:character :skills skill-key]
                      (max 0 (min skill-value 6)))))

(defn remove-item [v index]
  (vec
   (keep-indexed
    (fn [i item]
      (when (not= i index)
        item))
    v)))

(reg-event-db
 ::add-additional-specialization
 (fn-traced [db _]
            (update-in db
                       [:character :additional-specializations]
                       (fn [specs]
                         (let [specs (or specs [])]
                           (conj specs :none))))))

(reg-event-db
 ::remove-additional-specialization
 (fn-traced [db [_ index]]
            (update-in db
                       [:character :additional-specializations]
                       remove-item
                       index)))

(reg-event-db
 ::set-additional-specialization
 (fn-traced [db [_ index specialization-key]]
            (assoc-in db
                      [:character :additional-specializations index]
                      specialization-key)))

(reg-event-db
 ::add-talent
 (fn-traced [db [_ specialization-key]]
            (update-in db
                       [:character :talents specialization-key]
                       (fn [specs]
                         (let [specs (or specs [])]
                           (conj specs :none))))))

(reg-event-db
 ::remove-talent
 (fn-traced [db [_ specialization-key index]]
            (update-in db
                       [:character :talents specialization-key]
                       remove-item
                       index)))

(reg-event-db
 ::set-talent
 (fn-traced [db [_ specialization-key index talent-key]]
            (assoc-in db
                      [:character :talents specialization-key index]
                      talent-key)))

(reg-event-fx
 ::set-localstore
 (fn-traced [{:keys [db]} [_ key value]]
            (.setItem js/localStorage
                      key
                      (clj->js value))))

(reg-event-fx
 ::set-username
 (fn-traced [{:keys [db]} [_ username]]
            {:dispatch [::set-localstore "username" username]
             :db (assoc db :username username)}))

(reg-event-fx
 ::set-jwt-token
 (fn-traced [{:keys [db]} [_ jwt-token]]
            {:dispatch [::set-localstore "jwt-token" jwt-token]
             :db (assoc db :jwt-token jwt-token)}))

(reg-event-db
 ::set-auth
 (fn-traced [db [_ auth]]
            (assoc db :auth auth)))

(reg-event-db
 ::login-success
 (fn [db [_ auth result]]
   (let [access-token (.-accessToken result)
         jwt-token (.getJwtToken access-token)
         username (.getUsername access-token)]
     (dispatch [::set-username username])
     (dispatch [::set-jwt-token jwt-token]))))

(reg-event-fx
 ::login
 [(inject-cofx :auth)]
 (fn [cofx [_ a]]
   (let [auth (:auth cofx)]
     (.getSession auth)
     #_(.parseCognitoWebResponse auth js/window.location.href)
     {})))

(reg-event-fx
 ::logout
 [(inject-cofx :auth)]
 (fn-traced [cofx _]
            (.signOut (:auth cofx))
            (.removeItem js/localStorage "username")
            (.removeItem js/localStorage "jwt-token")
            {:db (dissoc (:db cofx) :username :jwt-token :auth)}))
