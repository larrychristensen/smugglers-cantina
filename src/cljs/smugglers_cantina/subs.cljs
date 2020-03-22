(ns smugglers-cantina.subs
  (:require
   [clojure.set :as sets]
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
   (prn "ACTIVE PANEL" (:active-panel db))
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
 :character/career-details
 :<- [:character/career]
 :<- [::career-map]
 (fn [[career-key career-map]]
   (career-map career-key)))

(reg-sub
 :character/career-name
 :<- [:character/career-details]
 (fn [career-details]
   (:name career-details)))

(reg-sub
 ::specializations
 :<- [::career-map]
 :<- [:character/career]
 (fn [[career-map career]]
   (get-in career-map [career :specializations])))

(reg-sub
 :character/career-specialization-keys
 :<- [::specializations]
 (fn [specs]
   (set (map :key specs))))

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
 ::talent
 :<- [::talents-map]
 (fn [talent-map [_ talent-key]]
   (talent-map talent-key)))

(reg-sub
 ::all-specializations
 :<- [::careers]
 (fn [careers]
   (set
    (mapcat
     (fn [career]
       (:specializations career))
     careers))))

(reg-sub
 ::additional-specializations
 :<- [:character/specialization]
 :<- [::all-specializations]
 (fn [[spec all-specs]]
   (remove #(= spec (:key %)) all-specs)))

(reg-sub
 :character/additional-career-specializations
 :<- [:character/additional-specializations]
 :<- [:character/career-specialization-keys]
 (fn [[selected-keys career-spec-keys]]
   (prn "SELECTED CKS" selected-keys)
   (filter
    career-spec-keys
    selected-keys)))

(reg-sub
 :character/additional-non-career-specializations
 :<- [:character/additional-specializations]
 :<- [:character/career-specialization-keys]
 (fn [[selected-keys career-spec-keys]]
   (prn "SELECTED BLOB" selected-keys)
   (remove
    career-spec-keys
    selected-keys)))

(reg-sub
 ::all-specializations-sorted
 :<- [::all-specializations]
 (fn [specs]
   (sort-by :key specs)))

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
 :character/characteristics
 :<- [::characteristics]
 :<- [::characteristic-values]
 (fn [[characteristics characteristic-values]]
   (map
    (fn [{:keys [key] :as char}]
      (let [value (characteristic-values key)]
        (assoc char :value value)))
    characteristics)))

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
 :character/has-talent?
 (fn [[_ specialization-key]]
   (subscribe [:character/talents specialization-key]))
 (fn [talents [_ _ talent-key]]
   (get talents talent-key)))

(reg-sub
 :character/all-specializations
 :<- [:character/specialization]
 :<- [:character/additional-specializations]
 (fn [[specialization additional-specializations] _]
   (prn "ADDL" additional-specializations)
   (if specialization
     (conj additional-specializations
           specialization)
     additional-specializations)))

(reg-sub
 :character/all-specialization-details
 :<- [:character/all-specializations]
 :<- [::all-specializations-map]
 (fn [[all-specializations specialization-map]]
   (prn "ALL SPECIALIZATIONS" all-specializations)
   (map
    specialization-map
    all-specializations)))

(reg-sub
 :character/specialization-names
 :<- [:character/all-specialization-details]
 (fn [all-specs]
   (map
    :name
    all-specs)))

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
 :character/specialization-available-talent-node-keys
 (fn [[_ specialization-key]]
   (subscribe [:character/specialization-available-talent-nodes specialization-key]))
 (fn [available-nodes]
   (set (map :key available-nodes))))

(reg-sub
 :character/talent-available?
 (fn [[_ specialization-key]]
   (subscribe [:character/specialization-available-talent-node-keys specialization-key]))
 (fn [available-keys [_ _ talent-key]]
   (get available-keys talent-key)))

(reg-sub
 :character/soak-value
 :<- [:character/brawn]
 identity)

(reg-sub
 :character/character
 (fn [db _]
   (get db :character)))

(reg-sub
 :characters/characters
 (fn [db _]
   (get db :characters)))

(reg-sub
 :characters/character-map
 :<- [:characters/characters]
 (fn [characters _]
   (into {} (map (juxt :id identity) characters))))

(reg-sub
 :characters/character
 :<- [:characters/character-map]
 (fn [characters [_ id]]
   (get characters id)))

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
 :character/species-details
 :<- [::species-map]
 :<- [:character/species]
 (fn [[species-map species] _]
   (species-map species)))

(reg-sub
 :character/species-name
 :<- [:character/species-details]
 (fn [species-details]
   (:name species-details)))

(reg-sub
 :character/skills
 (fn [db]
   (get-in db [:character :skills])))

(reg-sub
 :character/skill-rank
 :<- [:character/skills]
 (fn [skills [_ skill-key]]
   (get skills skill-key 0)))

(reg-sub
 :character/increase-skill-rank-disabled?
 :<- [:character/skills]
 (fn [skills [_ skill-key]]
   (let [v (get skills skill-key 0)]
     (or (nil? v)
         (>= v 6)))))

(reg-sub
 :character/decrease-skill-rank-disabled?
 :<- [:character/skills]
 (fn [skills [_ skill-key]]
   (let [v (get skills skill-key 0)]
     (or (nil? v)
         (<= v 0)))))

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
 :character/career-skill-ranks
 :<- [:character/skills]
 :<- [:character/career-skills-set]
 (fn [[skill-ranks career-skills-set]]
   (into {}
    (filter
     #(career-skills-set (key %))
     skill-ranks))))

(reg-sub
 :character/non-career-skill-ranks
 :<- [:character/skills]
 :<- [:character/career-skills-set]
 (fn [[skill-ranks career-skills-set]]
   (into {}
    (remove
     #(career-skills-set (key %))
     skill-ranks))))

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

(reg-sub
 ::character-sheet-tab
 (fn [db _]
   (get db :character-sheet-tab :species)))

(reg-sub
 :character/experience-points
 (fn [db _]
   (get-in db [:character :experience-points] 0)))

(reg-sub
 :character/specialization-experience-points
 :<- [:character/additional-specializations]
 :<- [:character/career-specialization-keys]
 :<- [:character/specialization]
 (fn [[additional-specs career-spec-keys specialization]]
   (apply + 0.0
          (map-indexed
           (fn [i spec-key]
             (+ (* (+ i
                      (if specialization
                        2
                        1))
                   10)
                (if (career-spec-keys spec-key)
                  0
                  10)))
           additional-specs))))

(defn skill-rank-xps [additional rank]
  (apply + 0
         (map
          (fn [v]
            (+ (* (inc v) 5) additional))
          (range rank))))

(defn skill-ranks-xps [additional skill-ranks]
  (prn "RANKS" skill-ranks)
  (apply + 0
         (map
          (partial skill-rank-xps additional)
          skill-ranks)))

(reg-sub
 :character/skill-experience-points
 :<- [:character/career-skill-ranks]
 :<- [:character/non-career-skill-ranks]
 (fn [[career-skill-ranks non-career-skill-ranks]]
   (prn "C N" career-skill-ranks non-career-skill-ranks)
   (+ (skill-ranks-xps 0 (vals career-skill-ranks))
      (skill-ranks-xps 5 (vals non-career-skill-ranks)))))

(reg-sub
 :character/spent-experience-points
 :<- [:character/skill-experience-points]
 :<- [:character/specialization-experience-points]
 (fn [[skill-xps
       spec-xps]]
   (prn "SKILL XPS" skill-xps)
   (+ skill-xps spec-xps)))

(reg-sub
 :character/current-experience-points
 :<- [:character/experience-points]
 :<- [:character/spent-experience-points]
 (fn [[total-xps
       spent-xps]]
   (prn "TOTAL SPENT" total-xps spent-xps)
   (- total-xps spent-xps)))
