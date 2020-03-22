(ns smugglers-cantina.css
  (:require [garden.def :refer [defstyles]]))

(defn box-prop [class-prefix prop-prefix]
  (apply
   conj
   (mapcat
    (fn [i]
      (mapv
       (fn [[position-n position-v]]
         [(keyword (str "." class-prefix position-n i))
          {(keyword (str prop-prefix "-" position-v)) (str i "px")}])
       {"l" "left"
        "r" "right"
        "b" "bottom"
        "t" "top"}))
    (range 1 100))
   (mapv
    (fn [i]
      [(keyword (str "." class-prefix i))
       {(keyword prop-prefix) (str i "px")}])
    (range 1 100))))

(def margins
  (box-prop "m" "margin"))

(def paddings
  (box-prop "p" "padding"))

(def widths
  (mapv
   (fn [v]
     [(keyword (str ".w" v))
      {:width (str v "px")}])
   (range 0 500)))

(def heights
  (conj
   (mapv
    (fn [v]
      [(keyword (str ".h" v))
       {:height (str v "px")}])
    (range 0 500))
   [:.h100p {:height "100%"}]))

(def borders
  (mapv
   (fn [v]
     [(keyword (str ".b" v))
      {:border-width (str v "px")
       :border-style :solid}])
   (range 0 10)))

(def border-colors
  [[:.bgray4
    {:border-color "rgba(0,0,0,0.4)"}]])

(def flexbox
  [[:.flex {:display :flex}]
   [:.jce {:justify-content :flex-end}]
   [:.jcsa {:justify-content :space-around}]
   [:.jcsb {:justify-content :space-between}]
   [:.aic {:align-items :center}]
   [:.ais {:align-items :stretch}]
   [:.flex-grow-1 {:flex-grow 1}]
   [:.flex-column {:flex-direction :column}]
   [:.flex-wrap {:flex-wrap :wrap}]])

(def font-sizes
  (mapv
   (fn [v]
     [(keyword (str ".fs" v))
      {:font-size (str v "px")}])
   (range 10 40)))

(def red "#BC2C1A")
(def yellow "#e8c627")
(def blue "#235789")
(def green "#286324")

(def grays
  (map
   (fn [v]
     [(keyword (str ".gray" v))
      {:color (str "rgba(0,0,0,0." v ")")}])
   (range 0 10)))

(def colors
  (concat
   [[:.red {:color red}]
    [:.blue {:color blue}]
    [:.white {:color :white}]]
   grays))

(def bg-grays
  (map
   (fn [v]
     [(keyword (str ".bg-gray" v))
      {:background-color (str "rgba(0,0,0,0." v ")")}])
   (range 0 10)))

(def bg-colors
  (concat
   [[:.bg-red {:background-color red}]
    [:.bg-blue {:background-color blue}]]
   bg-grays))

(def screen
  (concat
   [[:button {:cursor :pointer
              :color :black}]
    [:.bold {:font-weight :bold}]
    [:.italic {:font-style :italic}]
    [:.underline {:text-decoration :underline}]
    [:.white {:color :white}]
    [:input:focus
     {:outline "1px solid red"
      :outline-radius "3px"}]
    [:select:focus
     {:outline "1px solid red"
      :outline-radius "3px"}]
    [:body {:padding "0"
            :margin "0"
            :font-family "Roboto;sans-serif"
            :background-color "#eee"}]
    [:.header-logo {:height "60px"}]
    [:.header-sublogo {:height "43px"}]
    [:.header
     {:display :flex
      :align-items :center
      :background-color :black
      :padding "5px 20px"}]
    [:.page
     {:padding "0 20px"
      :width "800px"}]
    [:.page-header
     {:color blue
      ;;:-webkit-text-stroke "1px black"
      :font-weight "900"
      }]
    [:.text-field
     {:outline :none
      :width "100%"
      :border-radius "3px"
      :height "35px"
      :border "1px solid #aaa"
      :font-weight :bold
      :font-size "16px"
      :padding-left "10px"}]
    [:.dropdown-field
     {:width "100%"
      :background-color :transparent
      :border "1px solid #aaa"
      :border-radius "3px"
      :height "35px"
      :font-weight :bold
      :font-size "16px"
      :padding-left "10px"}]
    [:.character-sheet
     {:display :flex
      :flex-wrap :wrap}]
    [:.character-sheet-column
     {:width "350px"
      :padding "10px"}]
    [:.characteristics-panel-row
     {:display :flex}]
    [:.characteristic
     {:width "100px"
      :flex-grow 1
      :display :flex
      :justify-content :space-around
      :margin "5px"}]
    [:.characteristic-inner
     {:display :flex
      :flex-direction :column
      :align-items :center
      :width "80px"
      :background-color "rgba(0,0,0,0.1)"
      :border-radius "10px"}]
    [:.characteristic-value
     {:font-size "30px"
      :margin-top "15px"
      :font-weight :bold
      :background-color :white
      :width "40px"
      :text-align :center
      :border-radius "20px"}]
    [:.derived-attributes
     {:background-color "rgba(0,0,0,0.05)"
      :padding "5px"
      :border-radius "10px"}]
    [:.derived-attributes-row
     {:display :flex
      :width "100%"
      :justify-content :space-around}]
    [:.derived-attribute
     {:border "1px solid rgba(0,0,0,0.4)"
      :margin "5px"
      :flex-grow 1}]
    [:.derived-attribute-header
     {:font-size "16px"}]
    [:.subpanel-header.derived-attribute-header
     {:font-size "16px"}]
    [:.derived-attribute-inner
     {:height "80px"
      :width "140px"
      :text-align :center
      :background-color :white
      :border-radius "10px"
      :border "1px solid #aaa"
      :display :flex
      :flex-direction :column}]
    [:.derived-attribute-content
     {:flex-grow 1}]
    [:.derived-attribute-title
     {:background-color "rgba(0,0,0,0.2)"
      :border-radius "10px"
      :padding "2px"}]
    [:.wounds-panel
     {:height "100%"
      :display :flex
      :align-items :stretch}]
    [:.wounds-panel-column
     {:width "70px"
      :display :flex
      :flex-direction :column
      :flex-grow 1
      :align-items :center}]
    [:.wounds-panel-column-title
     {:font-size "14px"}]
    [:.wounds-panel-column-right
     {:border-left "1px solid #aaa"}]
    [:.wounds-panel-column-content
     {:flex-grow 1}]
    [:.wound-threshold
     {:font-size "30px"
      :font-weight :bold}]
    [:.skills-panel
     {:margin-top "20px"
      :background-color "rgba(0,0,0,0.05)"
      :border-radius "10px"}]
    [:.skills-panel-title
     {:font-weight :bold
      :text-align :center
      :font-size "20px"
      :border-top-left-radius "10px"
      :border-top-right-radius "10px"
      :background-color "rgba(0,0,0,0.2)"}]
    [:.skills-subtitle
     {:font-weight :bold
      :margin-top "10px"
      :background-color "rgba(0,0,0,0.2)"}]
    [:.skills-items
     {:padding "10px"}]
    [:.skill-item
     {:padding "5px"
      :border-bottom "1px solid #aaa"
      :display :flex
      :align-items :center}]
    [:.skill-title
     {:flex-grow 1
      :font-weight 400
      :font-size "20px"}]
    [:.career-skill
     {:font-weight 900}]
    [:.skill-rank
     {}]
    [:.skill-rank-input
     {:width "35px"
      :height "30px"
      :border "2px solid rgba(0,0,0,0.2)"
      :border-radius "5px"
      :padding "5px"}]
    [:.skill-dice
     {:width "135px"
      :display :flex
      :justify-content :flex-end}]
    [:.die-image
     {:height "100%"}]
    [:.ability-die-image
     {:height "20px"}]
    [:.skill-ability-die-image
     {:height "10px"}]
    [:.proficiency-die-image
     {:height "15px"}]
    [:.skill-proficiency-die-image
     {:height "8px"}]
    [:.header-button
     {:height "50px"
      :background-color yellow
      :border :none
      :font-weight :bold
      :border-radius "3px"
      :box-shadow "2px 2px 2px rgba(0,0,0,0.3)"}]
    [:.header-button-red
     {:background-color red
      :color :white}]
    [:.sc-tab
     {:background-color blue
      :color :white
      :border-radius "10px"
      :font-size "14px"
      :text-transform :lowercase
      ;;:text-decoration :underline
      :cursor :pointer
      :opacity 0.5
      :box-sizing :border-box
      :border "2px solid rgba(0,0,0,0.0)"
      :box-shadow "1px 1px 1px rgba(0,0,0,0.3)"}]
    [:.sc-tab.sc-current-tab
     {:opacity 1.0
      :border "2px solid rgba(0,0,0,0.3)"}]
    [:.talent-tree-node
     {:width "140px"
      :background-color "rgba(0,0,0,0.1)"
      :min-height "100px"
      :border-radius "10px"
      :box-shadow "1px 1px rgba(0,0,0,0.1)"
      :box-sizing :border-box
      :text-align :center}]
    [:.talent-tree-node-selected
     {:border (str "5px solid " red)
      :cursor :pointer}]
    [:.talent-tree-node-available
     {:background-color :white
      :cursor :pointer}]
    [:.talent-edge-right
     {:border-bottom "5px solid rgba(0,0,0,0.6)"
      :width "100%"
      :height "50%"}]
    [:.talent-edge-down
     {:border-right "5px solid rgba(0,0,0,0.6)"
      :width "70px"
      :height "100%"}]
    [:.subpanel
     {:background-color :white
      :border-radius "10px"}]
    [:.subpanel-header
     {:background-color "rgba(0,0,0,0.2)"
      :font-weight :bold
      :font-size "20px"
      :display :flex
      :justify-content :space-around
      :border-top-left-radius "10px"
      :border-top-right-radius "10px"}]
    [:.bubble-selector-item
     {:background-color yellow
      :color :black
      :padding "5px 10px"
      :margin "5px"
      :border (str "3px solid " yellow)
      :border-radius "10px"
      :font-size "14px"
      :text-transform :lowercase
      :cursor :pointer
      :box-sizing :border-box
      :box-shadow "1px 1px 1px rgba(0,0,0,0.3)"}]
    [:.bubble-selector-item-selected
     {:border "3px solid rgba(0,0,0,0.7)"}]
    [:.link
     {:text-decoration :underline
      :cursor :pointer
      :color blue}]

    ;; grid
    [:.page-grid
     {:display :grid
      :grid-template-columns "auto 300px 800px 300px auto"
      :grid-template-rows "80px auto 80px"}]
    [:.page-header
     {:grid-column-start 1
      :grid-column-end 6
      :grid-row-start 1
      :grid-row-end 1}]
    [:.left-panel
     {:grid-column-start 2
      :grid-column-end 2
      :grid-row-start 2
      :grid-row-end 2}]
    [:.main-content
     {:grid-column-start 3
      :grid-column-end 3
      :grid-row-start 2
      :grow-row-end 2}]
    [:.right-panel
     {:grid-column-start 4
      :grid-column-end 4
      :grid-row-start 2
      :grid-row-end 2}]
    [:.character-details-panel
     {:margin-top "100px"
      :margin-left "20px"}]
    [:.experience-button
     {:background-color blue
      :cursor :pointer
      :font-weight :bold
      :color :white
      :box-shadow "1px 1px 1px rgba(0,0,0,0.2)"
      :width "30px"
      :height "20px"
      :border-radius "5px"
      :margin "1px"
      :display :flex
      :align-items :center
      :justify-content :space-around}]

    [:.skill-rank-button
     {:background-color blue
      :cursor :pointer
      :font-weight :bold
      :color :white
      :box-shadow "1px 1px 1px rgba(0,0,0,0.2)"
      :border-radius "5px"
      :height "24px"
      :width "24px"
      :margin "1px"
      :display :flex
      :align-items :center
      :justify-content :space-around}]
    
    [:.disabled-button
     {:opacity 0.5
      :cursor :not-allowed}]

    [:.text-input
     {:font-size "20px"
      :border-radius "10px"
      :padding "10px"
      :border "1px solid rgba(0,0,0,0.1)"}]

    [:.navigation-item-selected
     {:background-color "rgba(0,0,0,0.7)"
      :color :white
      :border-radius "5px"}]

    [:.list
     {:padding-bottom "10px"
      :border-bottom-left-radius "5px"
      :border-bottom-right-radius "5px"
      :background-color :white}]
    [:.list-header
     {:background-color "rgba(0,0,0,0.7)"
      :color :white
      :border-top-left-radius "5px"
      :border-top-right-radius "5px"}]
    [:.character-list-name
     {:width "250px"}]
    [:.character-list-species
     {:width "150px"}]
    [:.character-list-career
     {:width "150px"}]
    [:.list-item
     {:padding "20px"
      :font-weight 500
      :cursor :pointer
      :border-bottom "1px solid rgba(0,0,0,0.2)"}]
    [:.list-item:hover
     {:background-color "rgba(0,0,0,0.1)"}]]
   margins
   paddings
   flexbox
   font-sizes
   colors
   bg-colors
   widths
   heights
   borders
   border-colors))
