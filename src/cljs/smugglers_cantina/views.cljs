(ns smugglers-cantina.views
  (:require
   [re-frame.core :as re-frame :refer [subscribe dispatch]]
   [smugglers-cantina.auth :as auth]
   [smugglers-cantina.subs :as subs]
   [smugglers-cantina.events :as events]
   [smugglers-cantina.rules.skills :as skills]
   [smugglers-cantina.rules.characteristics :as characteristics]
   [smugglers-cantina.rules.species.eote :as species]
   [smugglers-cantina.rules.careers.eote :as careers]))

(defn header-panel []
  (let [username @(subscribe [::subs/username])]
    [:div.header.flex.jcsb
     [:div.flex.aic
      [:img.header-logo {:src "images/smugglers-cantina-logo.png"}]
      [:img.header-sublogo.m-l-10 {:src "images/star-wars-rpg-logo.png"}]]
     [:div
      [:span.bold.white username]
      [:button.header-button.m-l-10
       {:on-click #(if (nil? username)
                    (dispatch [::events/login])
                    (dispatch [::events/logout]))}
       (if username
         "Logout"
         "Login")]]]))


;; home

#_(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:href "#/about"}
       "go to About Page"]]
     ]))


;; about

#_(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])

;; common

(defn dropdown-field [options value value-fn title-fn on-change]
  [:select.dropdown-field
   {:on-change on-change
    :value (or value :none)}
   [:option {:value :none} "<select>"]
   (doall
    (map-indexed
     (fn [i option]
       ^{:key i}
       [:option
        {:value (value-fn option)}
        (title-fn option)])
     options))])

(defn labeled-dropdown-field [label options value value-fn title-fn on-change]
  [:div
   [:div label]
   [dropdown-field options value value-fn title-fn on-change]])

(defn text-field [value on-change]
  [:input.text-field
   {:value value
    :on-change on-change}])

(defn labeled-text-field [label value on-change]
  [:div
   [:div label]
   [text-field value on-change]])


;; character sheet

(defn on-add-talent [specialization-key _]
  (re-frame/dispatch [::events/add-talent specialization-key]))

(defn on-talent-change [specialization-key i e]
  (re-frame/dispatch [::events/set-talent specialization-key i (keyword (.. e -target -value))]))

(defn on-remove-talent [specialization-key i _]
  (re-frame/dispatch [::events/remove-talent specialization-key i]))

(defn talent-description [{:keys [name level]}]
  (str name " (" (* 5 level) "xp)"))

(defn specialization-talent-selector [{:keys [key name]}]
  (let [talent-values @(re-frame/subscribe [:character/talents key])
        specialization-tree @(re-frame/subscribe [::subs/talent-nodes key])
        talents @(re-frame/subscribe [:character/specialization-available-talent-nodes key])]
    (prn "TALENTS" talents)
    ^{:key key}
    [:div.mt10.mb10
     [:div.bold.fs18 name]
     [:div
      (if (seq talent-values)
        [:div
         (doall
          (map-indexed
           (fn [i talent-value]
             ^{:key i}
             [:div
              {:style {:display :flex}}
              [dropdown-field
               talents
               talent-value
               :key
               talent-description
               (partial on-talent-change key i)]
              [:button
               {:on-click (partial on-remove-talent key i)}
               "Remove"]])
           talent-values))])
      [:button
       {:on-click (partial on-add-talent key)}
       "Add Talent"]]]))

(defn talents-selector []
  (let [specializations @(re-frame/subscribe [:character/all-specialization-details])]
    [:div
     [:div.bold.fs20.blue "Talent Trees"]
     (doall
      (map
       specialization-talent-selector
       (remove
        #(nil? %)
        specializations)))]))

(defn on-name-change [e]
  (re-frame/dispatch [::events/set-name (.. e -target -value)]))

(defn on-species-change [e]
  (re-frame/dispatch [::events/set-species (keyword (.. e -target -value))]))

(defn species-dropdown []
  (let [species @(re-frame/subscribe [::subs/species])
        species-value @(re-frame/subscribe [:character/species])]
    [labeled-dropdown-field
     "Species"
     species
     species-value
     :key
     :name
     on-species-change]))

(defn on-career-change [e]
  (re-frame/dispatch [::events/set-career (keyword (.. e -target -value))]))

(defn careers-dropdown []
  (let [careers @(re-frame/subscribe [::subs/careers])
        career-value @(re-frame/subscribe [:character/career])]
    [labeled-dropdown-field
     "Career"
     careers
     career-value
     :key
     :name
     on-career-change]))

(defn on-specialization-change [e]
  (re-frame/dispatch [::events/set-specialization (keyword (.. e -target -value))]))

(defn on-add-additional-specialization [_]
  (re-frame/dispatch [::events/add-additional-specialization]))

(defn on-additional-specialization-change [i e]
  (re-frame/dispatch [::events/set-additional-specialization i (keyword (.. e -target -value))]))

(defn on-remove-additional-specialization [i _]
  (re-frame/dispatch [::events/remove-additional-specialization i]))

(defn additional-specializations-selector []
  (let [specializations @(re-frame/subscribe [::subs/all-specializations])
        specialization-values @(re-frame/subscribe [:character/additional-specializations])]
    [:div
     [:div "Additional Specializations"]
     (if (seq specialization-values)
       [:div
        (doall
         (map-indexed
          (fn [i specialization-value]
            ^{:key i}
            [:div
             {:style {:display :flex}}
             [dropdown-field
              specializations
              specialization-value
              :key
              :name
              (partial on-additional-specialization-change i)]
             [:button
              {:on-click (partial on-remove-additional-specialization i)}
              "Remove"]])
          specialization-values))])
     [:button
      {:on-click on-add-additional-specialization}
      "Add Specialization"]]))

(defn specializations-dropdown []
  (let [specializations @(re-frame/subscribe [::subs/specializations])
        specialization-value @(re-frame/subscribe [:character/specialization])]
    [labeled-dropdown-field
     "Starting Specialization"
     specializations
     specialization-value
     :key
     :name
     on-specialization-change]))

(defn characteristics-panel []
  (let [characteristics @(re-frame/subscribe [::subs/characteristics])
        characteristic-values @(re-frame/subscribe [::subs/characteristic-values])]
    [:div.characteristics-panel
     [:div.characteristics-panel-title
      "Characteristics"]
     [:div.characteristics-panel-body
      (doall
       (map-indexed
        (fn [i chars]
          ^{:key i}
          [:div.characteristics-panel-row
           (doall
            (map
             (fn [{:keys [name key]}]
               ^{:key key}
               [:div.characteristic
                [:div.characteristic-inner
                 [:div.characteristic-value (get characteristic-values key)]
                 [:div name]]])
             chars))])
        (partition 3 characteristics)))]]))

(defn character-sheet-base-attributes []
  [:div.character-sheet-base-attributes.bold
   [:div.mb10
    [labeled-text-field
     "Character Name"
     @(re-frame/subscribe [:character/name])
     on-name-change]]
   [:div.mb10
    [species-dropdown]]
   [:div.mb10
    [careers-dropdown]]
   [:div.mb10
    [specializations-dropdown]]
   [:div.mb10
    [additional-specializations-selector]]])

(defn soak-value-panel []
  (let [soak-value @(subscribe [:character/soak-value])]
    [:div.soak-value-panel.bold.fs30
     soak-value]))

(defn wounds-panel []
  [:div.wounds-panel
   [:div.wounds-panel-column
    [:div.wounds-panel-column-content
     [:div.wound-threshold @(re-frame/subscribe [:character/wound-threshold])]]
    [:div.wounds-panel-column-title
     "Threshold"]]
   [:div.wounds-panel-column.wounds-panel-column-right
    [:div.wounds-panel-column-content
     [:div.wound-threshold 0]]
    [:div.wounds-panel-column-title
     "Current"]]])

(defn strain-panel []
  [:div.wounds-panel
   [:div.wounds-panel-column
    [:div.wounds-panel-column-content
     [:div.wound-threshold @(re-frame/subscribe [:character/strain-threshold])]]
    [:div.wounds-panel-column-title
     "Threshold"]]
   [:div.wounds-panel-column.wounds-panel-column-right
    [:div.wounds-panel-column-content
     [:div.wound-threshold 0]]
    [:div.wounds-panel-column-title
     "Current"]]])

(def ability-die [:img.ability-die-image.m-l-2 {:src "images/ability-die.svg"}])

(def proficiency-die [:img.proficiency-die-image.m-l-2 {:src "images/proficiency-die.svg"}])

(defn dice-pool-panel [{:keys [ability proficiency]}]
  [:div
   {:style {:display :flex}}
   [:div
    {:style {:display :flex
             :align-items :center}}
    (doall
     (map
      (fn [i]
        ^{:key i}
        [:span proficiency-die])
      (range proficiency)))]
   [:div
    {:style {:display :flex
             :align-items :center}}
    (doall
     (map
      (fn [i]
        ^{:key i}
        [:span ability-die])
      (range ability)))]])

(defn skill-rank-panel [skill-key]
  (let [skill-rank @(re-frame/subscribe [:character/skill-rank skill-key])]
    [:div.skill-rank
     [:input.skill-rank-input
      {:on-change (fn [e] (re-frame/dispatch [::events/set-skill-rank skill-key (int (.. e -target -value))]))
       :type :number
       :min 0
       :max 6
       :value skill-rank}]]))

(defn skills-subpanel [items]
  [:div.skills-items
   (doall
    (map
     (fn [{:keys [name key characteristic]}]
       (let [skill-dice @(re-frame/subscribe [:character/skill-dice key])
             career-skill? @(re-frame/subscribe [:character/career-skill? key])]
         ^{:key key}
         [:div.skill-item
          {:style {:width "100%"
                   :display :flex
                   :justify-content :space-between}}
          [:div.skill-title
           {:style {:font-weight (if career-skill? :bold :normal)}}
           [:span {:style {:width "100px"}} name]
           [:span.m-l-10 (str "(" (:abbr characteristic) ")")]]
          [skill-rank-panel key]
          [:div.skill-dice [dice-pool-panel skill-dice]]]))
     items))])

(defn skills-panel []
  [:div.skills-panel
   [:div.skills-panel-title
    "Skills"]
   [skills-subpanel @(re-frame/subscribe [::subs/general-skills])]
   [:div.skills-subtitle "Combat Skills"]
   [skills-subpanel @(re-frame/subscribe [::subs/combat-skills])]
   [:div.skills-subtitle "Knowledge Skills"]
   [skills-subpanel @(re-frame/subscribe [::subs/knowledge-skills])]])

(defn character-sheet-panel []
  (prn "CHARACTER" @(re-frame/subscribe [:character/character]))
  (prn "TALENT TREE MAP" @(re-frame/subscribe [:character/talent-tree-map]))
  (prn "HIGHER LEVEL TALENT NODES" @(re-frame/subscribe [:character/higher-level-talent-nodes]))
  (prn "AVAILABLE TALENT NODES" @(re-frame/subscribe [:character/available-talent-nodes]))
  (let [username @(subscribe [::subs/username])]
    [:div.page
     [:div.flex.jcsb.aic
      [:h1.page-header.fs39 "Character Sheet"]
      [:button.header-button
       {:on-click #(if (nil? username)
                     (dispatch [::events/login]))}
       (if username
         "Save"
         "Login To Save")]]
     [:div.character-sheet 
      [:div.character-sheet-column
       [character-sheet-base-attributes]
       [:div.m-b-10
        [characteristics-panel]]
       [skills-panel]]
      [:div.character-sheet-column
       [:div.derived-attributes.m-b-10
        [:div.derived-attributes-row
         [:div.derived-attribute
          [:div.derived-attribute-inner
           [:div.derived-attribute-title
            "Soak Value"]
           [:div.derived-attribute-content
            [soak-value-panel]]]]
         [:div.derived-attribute
          [:div.derived-attribute-inner
           [:div.derived-attribute-title
            "Wounds"]
           [:div.derived-attribute-content
            [wounds-panel]]]]]
        [:div.derived-attributes-row
         [:div.derived-attribute
          [:div.derived-attribute-inner
           [:div.derived-attribute-title
            "Strain"]
           [:div.derived-attribute-content
            [strain-panel]]]]
         [:div.derived-attribute
          [:div.derived-attribute-inner
           [:div.derived-attribute-title
            "Critical Injuries"]]]]]
       [talents-selector]]]]))

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [character-sheet-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  #_(let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div
     [header-panel]
     [show-panel @active-panel]])
  [:div
   [header-panel]
   [:div.flex.jcsa
    [character-sheet-panel]]])
