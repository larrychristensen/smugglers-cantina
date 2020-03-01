(ns smugglers-cantina.auth
  (:require ["amazon-cognito-auth-js" :refer (CognitoAuth)]
            [smugglers-cantina.events :as events]
            [re-frame.core :refer [dispatch]]))

(def auth-data {"ClientId" "4v2541jq6ghgahplpm6hhu3r4m"
                "UserPoolId" "us-east-1_jCpRwHULy"
                "RedirectUriSignIn" "http://localhost:8280"
                "RedirectUriSignOut" "http://localhost:8280"
                "AppWebDomain" "auth.smugglers-cantina.com"
                "TokenScopesArray" ["email"]})

(defn sign-in []
  (let [auth (CognitoAuth. (clj->js auth-data))]
    (set! (. auth -userhandler)
          (clj->js {"onSuccess" (fn [result]
                                  (prn "SUCCESS" (js/Object.keys result))
                                  (prn "JUANCHo")
                                  (let [access-token (.-accessToken result)
                                        jwt-token (.getJwtToken access-token)
                                        username (.getUsername access-token)]
                                    (prn "JWT TOKEN" jwt-token)
                                    (prn "USERNAME" username)
                                    (dispatch [::events/set-username username])
                                    (dispatch [::events/set-jwt-token jwt-token]))
                                  (prn "AUTH TOKEN" ))
                    "onFailure" (fn [result]
                                 (prn "FAILURE" result))}))
    (.getSession auth)
    (.parseCognitoWebResponse auth js/window.location.href)
    (dispatch [::events/set-auth auth])))

