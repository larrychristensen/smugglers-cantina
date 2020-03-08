(ns smugglers-cantina.views
  (:require
   [clojure.string :as s]
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
      [:span.bold.white.mr20 username]
      [:button.header-button.w90.h40
       {:on-click #(if (nil? username)
                    (dispatch [::events/login])
                    (dispatch [::events/logout]))}
       (if username
         "Logout"
         "Login")]]]))


;; home

#_(defn home-panel []
  (let [name (subscribe [::subs/name])]
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
  [:div.flex
   [:input.text-field
    {:value value
     :on-change on-change}]])

(defn labeled-text-field [label value on-change]
  [:div
   [:div label]
   [text-field value on-change]])


(defn event-value [e]
  (.. e -target -value))

;; character sheet

(defn characteristics-panel []
  (let [characteristics @(subscribe [::subs/characteristics])
        characteristic-values @(subscribe [::subs/characteristic-values])]
    [:div.subpanel
     [:div.subpanel-header
      "Characteristics"]
     [:div.characteristics-panel-body.p5
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

(defn soak-value-panel []
  (let [soak-value @(subscribe [:character/soak-value])]
    [:div.soak-value-panel.bold.fs30.flex.jcsa
     soak-value]))

(defn wounds-panel []
  [:div.wounds-panel
   [:div.wounds-panel-column
    [:div.wounds-panel-column-content
     [:div.wound-threshold @(subscribe [:character/wound-threshold])]]
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
     [:div.wound-threshold @(subscribe [:character/strain-threshold])]]
    [:div.wounds-panel-column-title
     "Threshold"]]
   [:div.wounds-panel-column.wounds-panel-column-right
    [:div.wounds-panel-column-content
     [:div.wound-threshold 0]]
    [:div.wounds-panel-column-title
     "Current"]]])

(def ability-die [:img.m-l-2.die-image {:src "images/ability-die.svg"}])

(def proficiency-die [:img.m-l-2.die-image {:src "images/proficiency-die.svg"}])

(defn dice-pool-panel [{:keys [ability proficiency]} & [summary?]]
  [:div
   {:style {:display :flex}}
   [:div
    {:style {:display :flex
             :align-items :center}}
    (doall
     (map
      (fn [i]
        ^{:key i}
        [:span
         {:class (if summary?
                   "skill-proficiency-die-image"
                   "proficiency-die-image")}
         proficiency-die])
      (range proficiency)))]
   [:div
    {:style {:display :flex
             :align-items :center}}
    (doall
     (map
      (fn [i]
        ^{:key i}
        [:span
         {:class (if summary?
                   "skill-ability-die-image"
                   "ability-die-image")}
         ability-die])
      (range ability)))]])

(defn skill-rank-panel [skill-key]
  (let [skill-rank @(subscribe [:character/skill-rank skill-key])]
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
       (let [skill-dice @(subscribe [:character/skill-dice key])
             career-skill? @(subscribe [:character/career-skill? key])]
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
  [:div.skills-panel.subpanel
   [:div.subpanel-header
    "Skills"]
   [skills-subpanel @(subscribe [::subs/general-skills])]
   [:div.skills-subtitle "Combat Skills"]
   [skills-subpanel @(subscribe [::subs/combat-skills])]
   [:div.skills-subtitle "Knowledge Skills"]
   [skills-subpanel @(subscribe [::subs/knowledge-skills])]])

(defn derived-attributes-panel []
  [:div.derived-attributes.m-b-10.subpanel
   [:div.derived-attributes-row
    [:div.subpanel.derived-attribute
     [:div.subpanel-header.derived-attribute-header
      "Soak Value"]
     [:div.derived-attribute-content
      [soak-value-panel]]]
    [:div.subpanel.derived-attribute
     [:div.subpanel-header.derived-attribute-header
      "Wounds"]
     [:div.derived-attribute-content
      [wounds-panel]]]]
   [:div.derived-attributes-row
    [:div.subpanel.derived-attribute
     [:div.subpanel-header.derived-attribute-header
      "Strain"]
     [:div.derived-attribute-content
      [strain-panel]]]
    [:div.subpanel.derived-attribute
     [:div.subpanel-header.derived-attribute-header
      "Critical Injuries"]]]])

(defn experience-points-total-panel []
  (let [xp-total @(subscribe [:character/experience-points])]
    [:div.flex.jcsa
     [:input.h50.fs30.bold.flex-grow-1.w120
      {:type :number
       :value xp-total
       :on-change #(let [val (js/parseInt (event-value %))]
                     (dispatch [:character/set-experience-points (if (js/isNaN val)
                                                                   0
                                                                   val)]))}]]))

(defn experience-points-panel []
  [:div.subpanel
   [:div.subpanel-header
    "Experience Points"]
   [:div.flex.p5.h100
    [:div.subpanel.bgray4.b1.flex-grow-1.m5
     [:div.subpanel-header
      "Total"]
     [:div.p5
      [experience-points-total-panel]]]
    [:div.subpanel.bgray4.b1.flex-grow-1.m5
     [:div.subpanel-header
      "Remaining"]]]])

(defn talent-node [spec-key i j {:keys [key level talent dirs]}]
  (let [full-talent @(subscribe [::subs/talent talent])
        has-talent? @(subscribe [:character/has-talent? spec-key key])
        available? @(subscribe [:character/talent-available? spec-key key])
        selectable? (or has-talent? available?)]
    ^{:key j}
    [:div
     [:div.flex
      [:div.talent-tree-node.flex.jcsa.aic
       {:on-click #(if has-talent?
                     (dispatch [::events/remove-talent spec-key key])
                     (if available?
                       (dispatch [::events/add-talent spec-key key])))
        :class (cond-> ""
                 has-talent? (str "talent-tree-node-selected ")
                 selectable? (str "talent-tree-node-available "))}
       [:div (:name full-talent)]]
      (when (< j 3)
        [:div.w40.flex
         (when (dirs :right)
           [:div.talent-edge-right])])]
     (when (< i 5)
       [:div.h40
        (when (dirs :down)
          [:div.talent-edge-down])])]))

(defn talent-tree-panel [{spec-name :name
                          spec-key :key
                          talent-tree :talent-tree}]
  (let [expanded-tree @(subscribe [::subs/expanded-talent-tree spec-key])
        available-talent-nodes @(subscribe [:character/specialization-available-talent-nodes
                                            spec-key])]
    [:div.mb10
     [:div.bold.fs24.mt10 spec-name]
     [:div
      (doall
       (map-indexed
        (fn [i row]
          ^{:key i}
          [:div
           [:div.flex
            (doall
             (map-indexed
              (partial talent-node spec-key i)
              row))]])
        expanded-tree))]]))

(defn make-selection-panel [content]
  [:div.subpanel.bg-gray1.p10.mt20
   content])

(defn character-talent-tree-panel []
  (let [specializations @(subscribe [:character/all-specialization-details])]
    (prn "SPECIALIZATIONS" specializations)
    (if (seq specializations)
      [:div
       (doall
        (map
         (fn [specialization]
           ^{:key (:key specialization)}
           [talent-tree-panel specialization])
         specializations))]
      [make-selection-panel
       [:div
        [:span "Please select a starting"]
        [:span.ml5.link
         {:on-click #(dispatch [::events/set-character-sheet-tab :specializations])}
         "specialization"]]])))

(defn bubble-selector-panel [items
                             selected-item-keys
                             on-add
                             on-remove]
  (prn "ITEMS" items)
  (prn "SELECTED ITEMS KEYS" selected-item-keys)
  [:div.flex.flex-wrap
   (doall
    (map
     (fn [{:keys [key name]}]
       (let [has-item? (selected-item-keys key)]
         ^{:key key}
         [:div.sc-tab.bubble-selector-item
          {:class (when has-item? 
                    "sc-current-tab")
           :on-click (fn [_] (if (and has-item?
                                      on-remove)
                               (on-remove key)
                               (on-add key)))}
          name]))
     items))])

(defn species-panel []
  (let [selected-species @(subscribe [:character/species])
        species-map @(subscribe [::subs/species-map])]
    [:div.mt20
     [:div.fs24.flex.bold
      [:div "Species:"]
      [:div.ml20.gray5 (get-in species-map [selected-species :name])]]
     [bubble-selector-panel
      @(subscribe [::subs/species])
      (if selected-species
        #{selected-species}
        #{})
      #(dispatch [::events/set-species %])]]))

(defn career-panel []
  (let [selected-career @(subscribe [:character/career])
        career-map @(subscribe [::subs/career-map])]
    [:div.mt20
     [:div.fs24.flex.bold
      [:div "Career:"]
      [:div.ml20.gray5 (get-in career-map [selected-career :name])]]
     [bubble-selector-panel
      @(subscribe [::subs/careers])
      (if selected-career
        #{selected-career}
        #{})
      #(dispatch [::events/set-career %])]]))

(defn specializations-panel []
  (let [selected-specialization @(subscribe [:character/specialization])
        specialization-map @(subscribe [::subs/all-specializations-map])
        all-specializations @(subscribe [::subs/all-specializations-sorted])
        career-specializations @(subscribe [::subs/specializations])
        additional-specializations @(subscribe [::subs/additional-specializations])
        selected-additional-specializations @(subscribe [:character/additional-specializations])]
    (prn "SELECTED ADDITIONAL" selected-additional-specializations)
    (prn "ALL SPECS" additional-specializations)
    [:div.mt20
     [:div.fs24.flex.bold
      [:div "Starting Specialization:"]
      [:div.ml20.gray5 (get-in specialization-map [selected-specialization :name])]]
     (if (seq career-specializations)
       [bubble-selector-panel
        career-specializations
        (if selected-specialization
          #{selected-specialization}
          #{})
        #(dispatch [::events/set-specialization %])]
       [make-selection-panel
        [:div
         [:span "Please select a"]
         [:span.ml5.link
          {:on-click #(dispatch [::events/set-character-sheet-tab :career])}
          "career"]]])
     [:div.fs24.flex.bold.mt20
      [:div "Additional Specializations:"]]
     [bubble-selector-panel
      additional-specializations
      (set selected-additional-specializations)
      #(dispatch [::events/add-additional-specialization %])
      #(dispatch [::events/remove-additional-specialization %])]]))

(defn experience-panel []
  [:div.flex.p5
   [:div.subpanel.flex-grow-1.m5
    [:div.subpanel-header
     "Total Experience Points"]
    [:div.p10.flex.flex-column.aic
     [:div.fs35.bold
      @(subscribe [:character/experience-points])]
     [:div.flex
      (doall
       (map
        (fn [v]
          ^{:key v}
          [:div.experience-button
           {:on-click #(dispatch [:character/offset-experience-points v])}
           (if (pos? v)
             (str "+" v)
             v)])
        [-25 -10 -5 -1 1 5 10 25]))]]]
   [:div.subpanel.flex-grow-1.m5
    [:div.subpanel-header
     "Current Experience Points"]
    (let [current @(subscribe [:character/current-experience-points])]
      [:div.fs35.bold.flex.jcsa.mt10
       {:class (when (neg? current) :red)}
       current])]])

(defn name-and-description-panel []
  [:div.p5
   [:div.bold.fs20 "Character Name"]
   [:input.text-input
    {:value @(subscribe [:character/name])
     :on-change #(dispatch [::events/set-name (event-value %)])}]])

(def character-sheet-tabs
  {:species {:title "Species"
             :view species-panel}
   :career {:title "Career"
            :view career-panel}
   :specializations {:title "Specializations"
                     :view specializations-panel}
   :talent-tree {:title "Talent Trees"
                 :view character-talent-tree-panel}
   :skills {:title "Skills"
            :view skills-panel}
   :experience {:title "Experience"
                :view experience-panel}
   :description {:title "Name, etc."
                 :view name-and-description-panel}})

(defn character-sheet-panel []
  (prn "CHARACTER" @(subscribe [:character/character]))
  (let [username @(subscribe [::subs/username])
        current-tab @(subscribe [::subs/character-sheet-tab])
        tab-view (-> character-sheet-tabs current-tab :view)]
    [:div.flex
     [:div.flex-grow-1]
     [:div.page
      [:div.flex.jcsb.aic
       [:h1.page-header.fs39 "Character Sheet"]
       [:button.header-button.w80.h40
        {:on-click #(if (nil? username)
                      (dispatch [::events/login])
                      (dispatch [:character/save-character]))}
        (if username
          "Save"
          "Login To Save")]]
      [:div.flex
       [:div
        [:div.character-sheet-tabs.flex
         (doall
          (map
           (fn [[k {:keys [title]}]]
             ^{:key k}
             [:div.sc-tab.pl10.pr10.pt5.pb5.m5
              {:on-click #(dispatch [::events/set-character-sheet-tab k])
               :class (when (= k current-tab)
                        "sc-current-tab")}
              title])
           character-sheet-tabs))]
        [tab-view]]]]]))

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [character-sheet-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn skills-summary-section [title skills]
  [:div
   [:div.mt5
    [:span.bold title]
    [:span.ml5
     (doall
      (map
       (fn [{:keys [key name]}]
         (let [skill-dice @(subscribe [:character/skill-dice key])
               career-skill? @(subscribe [:character/career-skill? key])]
           ^{:key key}
           [:div.flex.jcsb
            [:div.mr5 name]
            [dice-pool-panel skill-dice true]]))
       skills))]]])

(defn skills-summary []
  [:div.flex
   [:div.flex-grow-1
    [skills-summary-section "General Skills" @(subscribe [::subs/general-skills])]]
   [:div.flex-grow-1.ml20
    [skills-summary-section "Combat Skills" @(subscribe [::subs/combat-skills])]
    [skills-summary-section "Knowledge Skills" @(subscribe [::subs/knowledge-skills])]]])

(defn character-name-summary []
  [:div
   [:span.bold "Character Name"]
   [:span.ml5 @(subscribe [:character/name])]])

(defn species-summary []
  [:div
   [:span.bold "Species"]
   [:span.ml5 @(subscribe [:character/species-name])]])

(defn career-summary []
  [:div
   [:span.bold "Career"]
   [:span.ml5 @(subscribe [:character/career-name])]])

(defn specializations-summary []
  [:div
   [:span.bold "Specializations"]
   [:span.ml5 (s/join ", " @(subscribe [:character/specialization-names]))]])

(defn soak-value-summary []
  [:div
   [:span.bold "Soak Value"]
   [:span.ml5 @(subscribe [:character/soak-value])]])

(defn characteristics-summary []
  [:div
   [:span.bold "Characteristics"]
   [:span.ml8
    (map
     (fn [{:keys [abbr value]}]
       ^{:key abbr}
       [:span.mr5
        [:span.italic abbr]
        [:span.ml2.bold value]])
     @(subscribe [:character/characteristics]))]])

(defn character-details-panel []
  [:div.character-details-panel
   [:div.subpanel.w300
    [:div.subpanel-header.fs14 "Details"]
    [:div.p10
     [:div.fs11
      [character-name-summary]
      [species-summary]
      [career-summary]
      [specializations-summary]
      [soak-value-summary]
      [characteristics-summary]
      [skills-summary]]]]])

(defn main-panel []
  #_(let [active-panel (subscribe [::subs/active-panel])]
      [:div
       [header-panel]
       [show-panel @active-panel]])
  [:div.page-grid
   [:div.page-header
    [header-panel]]
   [:div.main-content
    [character-sheet-panel]]
   [:div.right-panel
    [character-details-panel]]])
