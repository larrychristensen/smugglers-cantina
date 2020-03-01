(ns smugglers-cantina.subs
  (:require
   [re-frame.core :as re-frame :refer [reg-sub subscribe]]
   [smugglers-cantina.rules.skills :as skills]
   [smugglers-cantina.rules.characteristics :as characteristics]
   [smugglers-cantina.rules.talents.trees :as talent-trees]
   [smugglers-cantina.rules.species.eote :as species]
   [smugglers-cantina.rules.careers.eote :as careers]
   [smugglers-cantina.rules.talents.eote :as talents]))


(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(reg-sub
 ::skills
 :<- [::characteristics-map]
 (fn [characteristics-map _]
   (map
    (fn [skill]
      (update skill :characteristic (fn [char-key] (characteristics-map char-key))))
    skills/skills)))

(reg-sub
 ::skill-map
 :<- [::skills]
 (fn [skills _]
   (into {} (map (juxt :key identity) skills))))

(reg-sub
 ::skill
 :<- [::skill-map]
 (fn [skill-map [_ skill-key]]
   (skill-map skill-key)))

(reg-sub
 ::combat-skills
 :<- [::skills]
 (fn [skills _]
   (filter
    (fn [{:keys [type]}]
      (= type :combat))
    skills)))

(reg-sub
 ::knowledge-skills
 :<- [::skills]
 (fn [skills _]
   (filter
    (fn [{:keys [type]}]
      (= type :knowledge))
    skills)))

(reg-sub
 ::general-skills
 :<- [::skills]
 (fn [skills _]
   (filter
    (fn [{:keys [type]}]
      (nil? type))
    skills)))

(reg-sub
 ::characteristics
 (fn [db _]
   characteristics/characteristics))

(reg-sub
 ::characteristics-map
 :<- [::characteristics]
 (fn [characteristics _]
   (into {} (map (juxt :key identity) characteristics))))

(reg-sub
 ::species
 (fn [db _]
   species/species))

(reg-sub
 ::species-map
 :<- [::species]
 (fn [species _]
   (into {} (map (juxt :key identity) species))))

(reg-sub
 ::careers
 (fn [db _]
   careers/careers))

(reg-sub
 ::career-map
 :<- [::careers]
 (fn [careers _]
   (into {} (map (juxt :key identity) careers))))

(reg-sub
 ::specializations
 :<- [::career-map]
 :<- [:character/career]
 (fn [[career-map career]]
   (get-in career-map [career :specializations])))

(reg-sub
 ::talents
 (fn [db _]
   talents/talents))

(reg-sub
 ::talents-map
 :<- [::talents]
 (fn [talents]
   (into {} (map (juxt :key identity) talents))))

(reg-sub
 ::all-specializations
 :<- [::careers]
 :<- [:character/career]
 (fn [[career-map]]
   (set
    (mapcat
     (fn [career]
       (:specializations career))
     career-map))))

(reg-sub
 ::all-specializations-map
 :<- [::all-specializations]
 (fn [all-specializations _]
   (into {} (map (juxt :key identity) all-specializations))))

(reg-sub
 ::characteristic-values
 :<- [::characteristics]
 :<- [::species-map]
 :<- [:character/species]
 (fn [[characteristics species-map character-species] _]
   (let [species (species-map character-species)
         species-characteristics (:characteristics species)]
     (reduce
      (fn [m {:keys [key]}]
        (let [species-characteristic-value (get species-characteristics key 0)]
          (assoc m key species-characteristic-value)))
      {}
      characteristics))))

(reg-sub
 ::characteristic-value
 :<- [::characteristic-values]
 (fn [characteristic-values [_ characteristic-key]]
   (characteristic-values characteristic-key)))

(reg-sub
 :character/brawn
 :<- [::characteristic-value :brawn]
 identity)

(reg-sub
 :character/name
 (fn [db _]
   (get-in db [:character :name])))

(reg-sub
 :character/species
 (fn [db _]
   (get-in db [:character :species])))

(reg-sub
 :character/career
 (fn [db _]
   (get-in db [:character :career])))

(reg-sub
 :character/specialization
 (fn [db _]
   (get-in db [:character :specialization])))

(reg-sub
 :character/additional-specializations
 (fn [db _]
   (get-in db [:character :additional-specializations])))

(reg-sub
 :character/talents
 (fn [db [_ specialization-key]]
   (get-in db [:character :talents specialization-key])))

(reg-sub
 :character/all-specializations
 :<- [:character/specialization]
 :<- [:character/additional-specializations]
 (fn [[specialization additional-specializations] _]
   (conj additional-specializations
         specialization)))

(reg-sub
 :character/all-specialization-details
 :<- [:character/all-specializations]
 :<- [::all-specializations-map]
 (fn [[all-specializations specialization-map]]
   (map
    specialization-map
    all-specializations)))

(reg-sub
 ::specialization
 :<- [::all-specializations-map]
 (fn [specializations-map [_ specialization-key]]
   (specializations-map specialization-key)))

(def remove-nil-xform (remove nil?))

(reg-sub
 :character/talent-trees
 :<- [:character/all-specialization-details]
 (fn [all-specialization-details]
   (sequence
    (comp (map :talent-tree)
          remove-nil-xform)
    all-specialization-details)))

(reg-sub
 ::specialization-talent-tree
 (fn [[_ specialization-key]]
   (subscribe [::specialization specialization-key]))
 (fn [specialization]
   (:talent-tree specialization)))

(reg-sub
 :character/expanded-talent-trees
 :<- [:character/talent-trees]
 (fn [talent-trees]
   (map
    talent-trees/expand-talent-tree
    talent-trees)))

(reg-sub
 ::expanded-talent-tree
 (fn [[_ specialization-key]]
   (subscribe [::specialization-talent-tree specialization-key]))
 (fn [specialization-talent-tree]
   (talent-trees/expand-talent-tree specialization-talent-tree)))

(reg-sub
 ::talent-nodes
 (fn [[_ specialization-key]]
   (subscribe [::expanded-talent-tree specialization-key]))
 (fn [expanded-talent-tree]
   (flatten expanded-talent-tree)))

(reg-sub
 :character/talent-nodes
 :<- [:character/expanded-talent-trees]
 (fn [talent-trees _]
   (flatten talent-trees)))

(reg-sub
 :character/specialization-top-level-talent-nodes
 (fn [[_ specialization-key]]
   (subscribe [::talent-nodes specialization-key]))
 (fn [talent-nodes]
   (talent-trees/top-level-talent-nodes talent-nodes)))

(reg-sub
 :character/top-level-talent-nodes
 :<- [:character/talent-nodes]
 (fn [talent-nodes]
   (talent-trees/top-level-talent-nodes talent-nodes)))

(reg-sub
 :character/talent-tree-map
 :<- [:character/expanded-talent-trees]
 talent-trees/talent-tree-map)

(reg-sub
 ::specialization-talent-tree-map
 (fn [[_ specialization-key]]
   (subscribe [::talent-nodes specialization-key]))
 (fn [talent-nodes]
   (into {} (map (juxt :key identity) talent-nodes))))

(reg-sub
 :character/higher-level-talent-nodes
 :<- [:character/talent-tree-map]
 :<- [:character/talents]
 (fn [[talent-tree-map purchased-talents]]
   (talent-trees/higher-level-talent-nodes
    talent-tree-map
    purchased-talents)))

(reg-sub
 :character/specialization-higher-level-talent-nodes
 (fn [[_ specialization-key]]
   [(subscribe [::specialization-talent-tree-map specialization-key])
    (subscribe [:character/talents specialization-key])])
 (fn [[talent-tree-map purchased-talents]]
   (talent-trees/higher-level-talent-nodes
    talent-tree-map
    purchased-talents)))

(reg-sub
 :character/available-talent-nodes
 :<- [:character/top-level-talent-nodes]
 :<- [:character/higher-level-talent-nodes]
 (fn [[top-level-talent-nodes higher-level-talent-nodes]]
   (concat top-level-talent-nodes
           higher-level-talent-nodes)))

(reg-sub
 :character/specialization-available-talent-nodes
 (fn [[_ specialization-key]]
   [(subscribe [:character/specialization-top-level-talent-nodes specialization-key])
    (subscribe [:character/specialization-higher-level-talent-nodes specialization-key])
    (subscribe [::talents-map])])
 (fn [[top-level higher-level talents-map]]
   (map
    (fn [talent]
      (assoc talent :name (-> talent
                              :talent
                              talents-map
                              :name)))
    (concat top-level higher-level))))

(reg-sub
 :character/soak-value
 :<- [:character/brawn]
 identity)

(reg-sub
 :character/character
 (fn [db _]
   (get db :character)))

(reg-sub
 :character/wound-threshold
 :<- [::species-map]
 :<- [:character/species]
 :<- [::characteristic-value :brawn]
 (fn [[species-map species brawn] _]
   (let [species (get species-map species)
         species-wound-threshold (get species :wound-threshold 10)]
     (+ brawn species-wound-threshold))))

(reg-sub
 :character/strain-threshold
 :<- [::species-map]
 :<- [:character/species]
 :<- [::characteristic-value :willpower]
 (fn [[species-map species willpower] _]
   (let [species (get species-map species)
         species-strain-threshold (get species :strain-threshold 10)]
     (+ willpower species-strain-threshold))))

(reg-sub
 :character/skill-rank
 (fn [db [_ skill-key]]
   (get-in db [:character :skills skill-key] 0)))

(reg-sub
 :character/career-skills-set
 :<- [::career-map]
 :<- [::all-specializations-map]
 :<- [:character/career]
 :<- [:character/all-specializations]
 (fn [[career-map all-specializations-map career all-specializations] _]
   (set
    (mapcat
     :skills
     (conj (map all-specializations-map all-specializations)
           (career-map career))))))

(reg-sub
 :character/career-skill?
 :<- [:character/career-skills-set]
 (fn [career-skills-set [_ skill-key]]
   (some? (get career-skills-set skill-key))))

(reg-sub
 :character/skill-characteristic-value
 :<- [::skill-map]
 :<- [::characteristic-values]
 (fn [[skill-map char-values] [_ skill-key]]
   (let [skill (skill-map skill-key)
         skill-char-key (get-in skill [:characteristic :key])
         char-value (char-values skill-char-key)]
     char-value)))

(reg-sub
 :character/skill-dice
 (fn [[_ skill-key]]
   [(re-frame/subscribe [:character/skill-rank skill-key])
    (re-frame/subscribe [:character/skill-characteristic-value skill-key])])
 (fn [[skill-rank skill-char-value] _]
   (let [num-dice (max skill-rank skill-char-value)]
     {:ability (- num-dice skill-rank)
      :proficiency skill-rank})))

(reg-sub
 ::username
 (fn [db _]
   (get db :username)))
