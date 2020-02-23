(ns smugglers-cantina.subs
  (:require
   [re-frame.core :as re-frame]
   [smugglers-cantina.rules.skills :as skills]
   [smugglers-cantina.rules.characteristics :as characteristics]
   [smugglers-cantina.rules.species.eote :as species]
   [smugglers-cantina.rules.careers.eote :as careers]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 ::skills
 :<- [::characteristics-map]
 (fn [characteristics-map _]
   (map
    (fn [skill]
      (update skill :characteristic (fn [char-key] (characteristics-map char-key))))
    skills/skills)))

(re-frame/reg-sub
 ::skill-map
 :<- [::skills]
 (fn [skills _]
   (into {} (map (juxt :key identity) skills))))

(re-frame/reg-sub
 ::skill
 :<- [::skill-map]
 (fn [skill-map [_ skill-key]]
   (skill-map skill-key)))

(re-frame/reg-sub
 ::combat-skills
 :<- [::skills]
 (fn [skills _]
   (filter
    (fn [{:keys [type]}]
      (= type :combat))
    skills)))

(re-frame/reg-sub
 ::knowledge-skills
 :<- [::skills]
 (fn [skills _]
   (filter
    (fn [{:keys [type]}]
      (= type :knowledge))
    skills)))

(re-frame/reg-sub
 ::general-skills
 :<- [::skills]
 (fn [skills _]
   (filter
    (fn [{:keys [type]}]
      (nil? type))
    skills)))

(re-frame/reg-sub
 ::characteristics
 (fn [db _]
   characteristics/characteristics))

(re-frame/reg-sub
 ::characteristics-map
 :<- [::characteristics]
 (fn [characteristics _]
   (into {} (map (juxt :key identity) characteristics))))

(re-frame/reg-sub
 ::species
 (fn [db _]
   species/species))

(re-frame/reg-sub
 ::species-map
 :<- [::species]
 (fn [species _]
   (into {} (map (juxt :key identity) species))))

(re-frame/reg-sub
 ::careers
 (fn [db _]
   careers/careers))

(re-frame/reg-sub
 ::career-map
 :<- [::careers]
 (fn [careers _]
   (into {} (map (juxt :key identity) careers))))

(re-frame/reg-sub
 ::specializations
 :<- [::career-map]
 :<- [:character/career]
 (fn [[career-map career]]
   (get-in career-map [career :specializations])))

(re-frame/reg-sub
 ::all-specializations
 :<- [::careers]
 :<- [:character/career]
 (fn [[career-map]]
   (set
    (mapcat
     (fn [career]
       (:specializations career))
     career-map))))

(re-frame/reg-sub
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

(re-frame/reg-sub
 ::characteristic-value
 :<- [::characteristic-values]
 (fn [characteristic-values [_ characteristic-key]]
   (characteristic-values characteristic-key)))

(re-frame/reg-sub
 :character/name
 (fn [db _]
   (get-in db [:character :name])))

(re-frame/reg-sub
 :character/species
 (fn [db _]
   (get-in db [:character :species])))

(re-frame/reg-sub
 :character/career
 (fn [db _]
   (get-in db [:character :career])))

(re-frame/reg-sub
 :character/specialization
 (fn [db _]
   (get-in db [:character :specialization])))

(re-frame/reg-sub
 :character/additional-specializations
 (fn [db _]
   (get-in db [:character :additional-specializations])))

(re-frame/reg-sub
 :character/soak-value
 (fn [db _]
   (get-in db [:character :soak-value])))

(re-frame/reg-sub
 :character/character
 (fn [db _]
   (get db :character)))

(re-frame/reg-sub
 :character/wound-threshold
 :<- [::species-map]
 :<- [:character/species]
 :<- [::characteristic-value :brawn]
 (fn [[species-map species brawn] _]
   (let [species (get species-map species)
         species-wound-threshold (get species :wound-threshold 10)]
     (+ brawn species-wound-threshold))))

(re-frame/reg-sub
 :character/strain-threshold
 :<- [::species-map]
 :<- [:character/species]
 :<- [::characteristic-value :willpower]
 (fn [[species-map species willpower] _]
   (let [species (get species-map species)
         species-strain-threshold (get species :strain-threshold 10)]
     (+ willpower species-strain-threshold))))

(re-frame/reg-sub
 :character/skill-rank
 (fn [db [_ skill-key]]
   (get-in db [:character :skills skill-key] 0)))

(re-frame/reg-sub
 :character/career-skills-set
 :<- [::career-map]
 :<- [:character/career]
 (fn [[career-map career] _]
   (set (get-in career-map [career :skills]))))

(re-frame/reg-sub
 :character/career-skill?
 :<- [:character/career-skills-set]
 (fn [career-skills-set [_ skill-key]]
   
   (some? (get career-skills-set skill-key))))

(re-frame/reg-sub
 :character/skill-characteristic-value
 :<- [::skill-map]
 :<- [::characteristic-values]
 (fn [[skill-map char-values] [_ skill-key]]
   (let [skill (skill-map skill-key)
         skill-char-key (get-in skill [:characteristic :key])
         char-value (char-values skill-char-key)]
     char-value)))

(re-frame/reg-sub
 :character/skill-dice
 (fn [[_ skill-key]]
   [(re-frame/subscribe [:character/skill-rank skill-key])
    (re-frame/subscribe [:character/skill-characteristic-value skill-key])])
 (fn [[skill-rank skill-char-value] _]
   (let [num-dice (max skill-rank skill-char-value)]
     {:ability (- num-dice skill-rank)
      :proficiency skill-rank})))



