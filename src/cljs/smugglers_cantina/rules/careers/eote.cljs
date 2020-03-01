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
  [[[:grit :d]
    [:lethal-blows :d]
    [:stalker :d]
    [:dodge :d]]
   [[:precise-aim :rd]
    [:jump-up :lrd]
    [:quick-strike :lrd]
    [:quick-draw :ld]]
   [[:targeted-blow :d]
    [:stalker :rd]
    [:lethal-blows :ld]
    [:anatomy-lessons :d]]
   [[:stalker :rd]
    [:sniper-shot :ld]
    [:dodge :d]
    [:lethal-blows :d]]
   [[:precise-aim]
    [:deadly-accuracy]
    [:dedication]
    [:master-of-shadows]]])

(def gadgeteer-tree
  [[[:brace]
    [:toughened :d]
    [:intimidating]
    [:defensive-stance :d]]
   [[:spare-clip :r]
    [:jury-rigged :lrd]
    [:point-blank :l]
    [:disorient :d]]
   [[:toughened :r]
    [:armor-master :lrd]
    [:natural-enforcer :l]
    [:stunning-blow :d]]
   [[:jury-rigged :r]
    [:tinkerer :lrd]
    [:deadly-accuracy :l]
    [:improved-stunning-blow :d]]
   [[:intimidating :r]
    [:dedication :lr]
    [:improved-armor-master :l]
    [:crippling-blow]]])

(def survivalist-tree
  [[[:forager :d]
    [:stalker :d]
    [:outdoorsman :d]
    [:expert-tracker]]
   [[:outdoorsman :rd]
    [:swift :lrd]
    [:hunter :lrd]
    [:soft-spot :ld]]
   [[:toughened :d]
    [:expert-tracker :d]
    [:stalker :rd]
    [:natural-outdoorsman :ld]]
   [[:toughened :d]
    [:hunter]
    [:expert-tracker :d]
    [:blooded :d]]
   [[:enduring :r]
    [:dedication :lr]
    [:grit :l]
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
