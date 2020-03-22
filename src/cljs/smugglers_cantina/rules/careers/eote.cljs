(ns smugglers-cantina.rules.careers.eote)

(def none #{})
(def l #{:left})
(def r #{:right})
(def d #{:down})
(def lr #{:left :right})
(def ld #{:left :down})
(def rd #{:right :down})
(def lrd #{:left :right :down})



(def assassin-tree
  [[[:grit [:d]]
    [:lethal-blows [:d]]
    [:stalker [:d]]
    [:dodge [:d]]]
   [[:precise-aim [:r :d]]
    [:jump-up [:l :r :d]]
    [:quick-strike [:l :r :d]]
    [:quick-draw [:l :d]]]
   [[:targeted-blow [:d]]
    [:stalker [:r :d]]
    [:lethal-blows [:l :d]]
    [:anatomy-lessons [:d]]]
   [[:stalker [:r :d]]
    [:sniper-shot [:l :d :u]]
    [:dodge [:d]]
    [:lethal-blows [:d]]]
   [[:precise-aim]
    [:deadly-accuracy]
    [:dedication]
    [:master-of-shadows]]])

(def gadgeteer-tree
  [[[:brace]
    [:toughened [:d]]
    [:intimidating]
    [:defensive-stance [:d]]]
   [[:spare-clip [:r]]
    [:jury-rigged [:l :r :d]]
    [:point-blank [:l]]
    [:disorient [:d]]]
   [[:toughened [:r]]
    [:armor-master [:l :r :d]]
    [:natural-enforcer [:l]]
    [:stunning-blow [:d]]]
   [[:jury-rigged [:r]]
    [:tinkerer [:l :r :d]]
    [:deadly-accuracy [:l]]
    [:improved-stunning-blow [:d]]]
   [[:intimidating [:r]]
    [:dedication [:l :r]]
    [:improved-armor-master [:l]]
    [:crippling-blow]]])

(def survivalist-tree
  [[[:forager [:d]]
    [:stalker [:d]]
    [:outdoorsman [:d]]
    [:expert-tracker]]
   [[:outdoorsman [:r :d :u]]
    [:swift [:l :r :d]]
    [:hunter [:l :r :d]]
    [:soft-spot [:l :d]]]
   [[:toughened [:d :u]]
    [:expert-tracker [:d]]
    [:stalker [:r :d :u]]
    [:natural-outdoorsman [:l :d]]]
   [[:toughened [:d :u]]
    [:hunter]
    [:expert-tracker [:d :u]]
    [:blooded [:d]]]
   [[:enduring [:r :u]]
    [:dedication [:l :r]]
    [:grit [:l :u]]
    [:heroic-fortitude]]])

(def careers
  [{:name "Bounty Hunter"
    :key :bounty-hunter
    :skills [:athletics
             :brawl
             :perception
             :piloting-planetary
             :piloting-space
             :ranged-heavy
             :streetwise
             :vigilance]
    :specializations [{:name "Assassin"
                       :key :assassin
                       :skills [:melee
                                :ranged-heavy
                                :skulduggery
                                :stealth]
                       :talent-tree assassin-tree}
                      {:name "Gadgeteer"
                       :key :gadgeteer
                       :skills [:brawl
                                :coercion
                                :mechanics
                                :ranged-light]
                       :talent-tree gadgeteer-tree}
                      {:name "Survivalist"
                       :key :survivalist
                       :skills [:xenology
                                :perception
                                :resilience
                                :survival]
                       :talent-tree survivalist-tree}]}
   {:name "Colonist"
    :key :colonist
    :skills [:charm
             :deception
             :core-worlds
             :education
             :lore
             :leadership
             :negotiation
             :streetwise]
    :specializations [{:name "Doctor"
                       :key :doctor
                       :skills [:cool
                                :education
                                :medicine
                                :resilience]}
                      {:name "Politico"
                       :key :politico
                       :skills [:charm
                                :coercion
                                :deception
                                :core-worlds]}
                      {:name "Scholar"
                       :key :scholar
                       :skills [:outer-rim
                                :underworld
                                :xenology
                                :perception]}]}
   {:name "Explorer"
    :key :explorer
    :skills [:astrogation
             :cool
             :lore
             :outer-rim
             :xenology
             :perception
             :piloting-space
             :survival]
    :specializations [{:name "Fringer"
                       :key :fringer
                       :skills [:astrogation
                                :coordination
                                :negotiation
                                :streetwise]}
                      {:name "Scout"
                       :key :scout
                       :skills [:athletics
                                :medicine
                                :piloting-planetary
                                :survival]}
                      {:name "Trader"
                       :key :trader
                       :skills [:deception
                                :core-worlds
                                :underworld
                                :negotiation]}]}
   {:name "Hired Gun"
    :key :hired-gun
    :skills [:athletics
             :brawl
             :discipline
             :melee
             :piloting-planetary
             :ranged-light
             :resilience
             :vigilance]
    :specializations [{:name "Bodyguard"
                       :key :bodyguard
                       :skills [:gunnery
                                :perception
                                :piloting-planetary
                                :ranged-heavy]}
                      {:name "Marauder"
                       :key :marauder
                       :skills [:coercion
                                :melee
                                :resilience
                                :survival]}
                      {:name "Mercenary Soldier"
                       :key :mercenary-soldier
                       :skills [:discipline
                                :gunnery
                                :leadership
                                :ranged-heavy]}]}
   {:name "Smuggler"
    :key :smuggler
    :skills [:coordination
             :deception
             :underworld
             :perception
             :piloting-space
             :skulduggery
             :streetwise
             :vigilance]
    :specializations [{:name "Pilot"
                       :key :pilot
                       :skills [:astrogation
                                :gunnery
                                :piloting-planetary
                                :piloting-space]}
                      {:name "Scoundrel"
                       :key :scoundrel
                       :skills [:charm
                                :cool
                                :deception
                                :ranged-light]}
                      {:name "Thief"
                       :key :thief
                       :skills [:computers
                                :skulduggery
                                :stealth
                                :vigilance]}]}
   {:name "Technician"
    :key :technician
    :skills [:astrogation
             :computers
             :coordination
             :discipline
             :outer-rim
             :mechanics
             :perception
             :piloting-planetary]
    :specializations [{:name "Mechanic"
                       :key :mechanic
                       :skills [:brawl
                                :mechanics
                                :piloting-space
                                :skulduggery]}
                      {:name "Outlaw Tech"
                       :key :outlaw-tech
                       :skills [:education
                                :underworld
                                :mechanics
                                :streetwise]}
                      {:name "Slicer"
                       :key :slicer
                       :skills [:computers
                                :education
                                :underworld
                                :stealth]}]}])
