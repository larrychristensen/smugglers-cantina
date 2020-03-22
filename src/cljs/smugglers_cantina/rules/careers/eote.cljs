(ns smugglers-cantina.rules.careers.eote)

(def d [:d])
(def rd [:r :d])
(def lrd [:l :r :d])
(def ld [:l :d])
(def lr [:l :r])
(def ldu [:l :d :u])
(def lrdu [:l :r :d :u])
(def rdu [:r :d :u])
(def lu [:l :u])
(def ru [:r :u])
(def l [:l])
(def du [:d :u])
(def u [:u])
(def r [:r])
(def lru [:l :r :u])

(def assassin-tree
  [[[:grit d]
    [:lethal-blows d]
    [:stalker d]
    [:dodge d]]
   [[:precise-aim rd]
    [:jump-up lrd]
    [:quick-strike lrd]
    [:quick-draw ld]]
   [[:targeted-blow d]
    [:stalker rd]
    [:lethal-blows ld]
    [:anatomy-lessons d]]
   [[:stalker rd]
    [:sniper-shot ldu]
    [:dodge d]
    [:lethal-blows d]]
   [[:precise-aim]
    [:deadly-accuracy]
    [:dedication]
    [:master-of-shadows]]])

(def gadgeteer-tree
  [[[:brace]
    [:toughened d]
    [:intimidating]
    [:defensive-stance d]]
   [[:spare-clip r]
    [:jury-rigged lrd]
    [:point-blank l]
    [:disorient d]]
   [[:toughened r]
    [:armor-master lrd]
    [:natural-enforcer l]
    [:stunning-blow d]]
   [[:jury-rigged r]
    [:tinkerer lrd]
    [:deadly-accuracy l]
    [:improved-stunning-blow d]]
   [[:intimidating r]
    [:dedication lr]
    [:improved-armor-master l]
    [:crippling-blow]]])

(def survivalist-tree
  [[[:forager d]
    [:stalker d]
    [:outdoorsman d]
    [:expert-tracker]]
   [[:outdoorsman rdu]
    [:swift lrd]
    [:hunter lrd]
    [:soft-spot ld]]
   [[:toughened du]
    [:expert-tracker d]
    [:stalker rdu]
    [:natural-outdoorsman ld]]
   [[:toughened du]
    [:hunter]
    [:expert-tracker du]
    [:blooded d]]
   [[:enduring ru]
    [:dedication lr]
    [:grit lu]
    [:heroic-fortitude]]])

(def doctor-tree
  [[[:surgeon d]
    [:bacta-specialist]
    [:grit d]
    [:resolve]]
   [[:stim-application rdu]
    [:grit lr]
    [:surgeon lrdu]
    [:resolve ld]]
   [[:surgeon rdu]
    [:grit lrd]
    [:bacta-specialist ldu]
    [:pressure-point du]]
   [[:improved-stim-application du]
    [:natural-doctor du]
    [:toughened du]
    [:anatomy-lessons du]]
   [[:supreme-stim-application ru]
    [:master-doctor [:l :r :u]]
    [:dedication lu]
    [:dodge u]]])

(def politico-tree
  [[[:kill-with-kindness d]
    [:grit d]
    [:plausible-deniability d]
    [:toughened d]]
   [[:inspiring-rhetoric rdu]
    [:kill-with-kindness lu]
    [:scathing-tirade ru]
    [:plausible-deniability ldu]]
   [[:dodge rdu]
    [:improved-inspiring-rhetoric ld]
    [:improved-scathing-tirade rd]
    [:well-rounded ldu]]
   [[:grit du]
    [:supreme-inspiring-rhetoric u]
    [:supreme-scathing-tirade u]
    [:nobodys-fool du]]
   [[:steely-nerves ru]
    [:dedication lr]
    [:natural-charmer lr]
    [:intense-presence lu]]])

(def scholar-tree
  [[[:respected-scholar d]
    [:speaks-binary d]
    [:grit d]
    [:brace d]]
   [[:researcher rdu]
    [:respected-scholar lu]
    [:resolve ru]
    [:researcher ldu]]
   [[:codebreaker ru]
    [:knowledge-specialization ld]
    [:natural-scholar rd]
    [:well-rounded lu]]
   [[:knowledge-specialization rd]
    [:intense-focus lu]
    [:confidence ru]
    [:resolve ld]]
   [[:stroke-of-genius ru]
    [:mental-fortress lr]
    [:dedication lr]
    [:toughened lu]]])

(def fringer-tree
  [[[:galaxy-mapper d]
    [:street-smarts]
    [:rapid-recovery d]
    [:street-smarts d]]
   [[:skilled-jockey rdu]
    [:galaxy-mapper ld]
    [:grit rdu]
    [:toughened ldu]]
   [[:master-starhopper ru]
    [:defensive-driving lu]
    [:rapid-recovery du]
    [:durable du]]
   [[:rapid-recovery rd]
    [:jump-up lrd]
    [:grit lu]
    [:knockdown du]]
   [[:dedication ru]
    [:toughened lu]
    [:dodge r]
    [:dodge lu]]])

(def scout-tree
  [[[:rapid-recovery d]
    [:stalker d]
    [:grit d]
    [:shortcut d]]
   [[:forager rdu]
    [:quick-strike lrdu]
    [:lets-ride lrdu]
    [:disorient ldu]]
   [[:rapid-recovery du]
    [:natural-hunter du]
    [:familiar-suns du]
    [:shortcut du]]
   [[:grit du]
    [:heightened-awareness u]
    [:toughened du]
    [:quick-strike du]]
   [[:utility-belt ru]
    [:dedication lr]
    [:stalker lu]
    [:disorient u]]])

(def trader-tree
  [[[:know-somebody d]
    [:convincing-demeanor]
    [:wheel-and-deal]
    [:smooth-talker]]
   [[:wheel-and-deal rdu]
    [:grit lr]
    [:spare-clip lr]
    [:toughened l]]
   [[:know-somebody rdu]
    [:nobodys-fool lr]
    [:smooth-talker lr]
    [:nobodys-fool l]]
   [[:wheel-and-deal rdu]
    [:steely-nerves lr]
    [:black-market-contacts lr]
    [:black-market-contacts ld]]
   [[:know-somebody ru]
    [:natural-negotiator lr]
    [:dedication lr]
    [:master-merchant lu]]])

(def bodyguard-tree
  [[[:toughened]
    [:barrage d]
    [:durable d]
    [:grit]]
   [[:body-guard rd]
    [:hard-headed lrdu]
    [:barrage lrdu]
    [:brace ld]]
   [[:body-guard du]
    [:side-step rdu]
    [:defensive-stance lu]
    [:brace u]]
   [[:enduring u]
    [:side-step rdu]
    [:defensive-stance lrdu]
    [:hard-headed ld]]
   [[:dedication r]
    [:barrage lru]
    [:toughened lu]
    [:improved-hard-headed u]]])

(def marauder-tree
  [[[:toughened d]
    [:frenzied-attack d]
    [:feral-strength d]
    [:lethal-blows d]]
   [[:feral-strength ru]
    [:toughened lrdu]
    [:heroic-fortitude lrdu]
    [:knockdown lu]]
   [[:enduring rd]
    [:lethal-blows lru]
    [:toughened lru]
    [:frenzied-attack ld]]
   [[:toughened ru]
    [:feral-strength lrd]
    [:natural-brawler lrd]
    [:lethal-blows lu]]
   [[:frenzied-attack r]
    [:enduring lru]
    [:defensive-stance lru]
    [:dedication l]]])

(def mercenary-solier-tree
  [[[:command d]
    [:second-wind d]
    [:point-blank d]
    [:side-step d]]
   [[:second-wind rdu]
    [:confidence ldu]
    [:strong-arm rdu]
    [:point-blank lu]]
   [[:field-commander rdu]
    [:command lrdu]
    [:natural-marksman lrdu]
    [:sniper-shot ld]]
   [[:improved-field-commander u]
    [:grit du]
    [:toughened ru]
    [:lethal-blows ld]]
   [[:deadly-accuracy r]
    [:true-aim lru]
    [:dedication lr]
    [:true-aim lu]]])

(def pilot-tree
  [[[:full-throttle d]
    [:skilled-jockey d]
    [:galaxy-mapper d]
    [:lets-ride d]]
   [[:skilled-jockey rdu]
    [:dead-to-rights ldu]
    [:galaxy-mapper rdu]
    [:rapid-recovery ldu]]
   [[:improved-full-throttle du]
    [:improved-dead-to-rights u]
    [:grit rdu]
    [:natural-pilot ldu]]
   [[:grit rdu]
    [:supreme-full-throttle l]
    [:tricky-target du]
    [:defensive-driving du]]
   [[:master-pilot ru]
    [:dedication lr]
    [:toughened lru]
    [:brilliant-evasion lu]]])

(def scoundrel-tree
  [[[:black-market-contacts d]
    [:convincing-demeanor]
    [:quick-draw]
    [:rapid-reaction d]]
   [[:convincing-demeanor du]
    [:black-market-contacts rd]
    [:convincing-demeanor ld]
    [:quick-strike du]]
   [[:hidden-storage du]
    [:toughened rdu]
    [:black-market-contacts ldu]
    [:side-step du]]
   [[:toughened du]
    [:rapid-reaction rdu]
    [:hidden-storage ldu]
    [:side-step du]]
   [[:dedication ru]
    [:natural-charmer lru]
    [:soft-spot lru]
    [:quick-strike lu]]])

(def thief-tree
  [[[:street-smarts d]
    [:black-market-contacts]
    [:indistinguishable]
    [:bypass-security d]]
   [[:black-market-contacts rdu]
    [:dodge lrd]
    [:grit lrd]
    [:hidden-storage ldu]]
   [[:stalker rdu]
    [:grit lrdu]
    [:rapid-reaction lrdu]
    [:shortcut ldu]]
   [[:bypass-security du]
    [:natural-rogue rdu]
    [:street-smarts lrdu]
    [:jump-up ldu]]
   [[:master-of-shadows u]
    [:dodge ru]
    [:indistinguishable lu]
    [:dedication u]]])

(def mechanic-tree
  [[[:gearhead d]
    [:toughened d]
    [:fine-tuning d]
    [:solid-repairs d]]
   [[:redundant-systems rdu]
    [:solid-repairs lrdu]
    [:gearhead lrdu]
    [:grit ldu]]
   [[:solid-repairs rdu]
    [:enduring lrdu]
    [:bad-motivator lrdu]
    [:toughened ldu]]
   [[:contraption rdu]
    [:solid-repairs lrdu]
    [:fine-tuning ldu]
    [:hard-headed du]]
   [[:natural-tinkerer u]
    [:hold-together u]
    [:dedication u]
    [:improved-hard-headed u]]])

(def outlaw-tech-tree
  [[[:tinkerer d]
    [:utinni! d]
    [:speaks-binary d]
    [:tinkerer d]]
   [[:solid-repairs rdu]
    [:grit lrdu]
    [:utinni! lrdu]
    [:toughened ldu]]
   [[:utility-belt rdu]
    [:side-step lrdu]
    [:brace lrdu]
    [:defensive-stance ldu]]
   [[:jury-rigged rdu]
    [:speaks-binary lru]
    [:inventor ldu]
    [:jury-rigged du]]
   [[:inventor ru]
    [:dedication l]
    [:known-schematic ru]
    [:brace lu]]])

(def slicer-tree
  [[[:codebreaker d]
    [:grit]
    [:technical-aptitude]
    [:bypass-security]]
   [[:defensive-stance du]
    [:technical-aptitude rd]
    [:grit lrd]
    [:bypass-security ld]]
   [[:natural-programmer du]
    [:bypass-security rdu]
    [:defensive-slicing lrdu]
    [:grit ldu]]
   [[:defensive-slicing rdu]
    [:improved-defensive-slicing lrdu]
    [:codebreaker lrdu]
    [:resolve ldu]]
   [[:skilled-slicer ru]
    [:master-slicer lru]
    [:mental-fortress lru]
    [:dedication lu]]])

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
                                :resilience]
                       :talent-tree doctor-tree}
                      {:name "Politico"
                       :key :politico
                       :skills [:charm
                                :coercion
                                :deception
                                :core-worlds]
                       :talent-tree politico-tree}
                      {:name "Scholar"
                       :key :scholar
                       :skills [:outer-rim
                                :underworld
                                :xenology
                                :perception]
                       :talent-tree scholar-tree}]}
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
                                :streetwise]
                       :talent-tree fringer-tree}
                      {:name "Scout"
                       :key :scout
                       :skills [:athletics
                                :medicine
                                :piloting-planetary
                                :survival]
                       :talent-tree scout-tree}
                      {:name "Trader"
                       :key :trader
                       :skills [:deception
                                :core-worlds
                                :underworld
                                :negotiation]
                       :talent-tree trader-tree}]}
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
                                :ranged-heavy]
                       :talent-tree bodyguard-tree}
                      {:name "Marauder"
                       :key :marauder
                       :skills [:coercion
                                :melee
                                :resilience
                                :survival]
                       :talent-tree marauder-tree}
                      {:name "Mercenary Soldier"
                       :key :mercenary-soldier
                       :skills [:discipline
                                :gunnery
                                :leadership
                                :ranged-heavy]
                       :talent-tree mercenary-solier-tree}]}
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
                                :piloting-space]
                       :talent-tree pilot-tree}
                      {:name "Scoundrel"
                       :key :scoundrel
                       :skills [:charm
                                :cool
                                :deception
                                :ranged-light]
                       :talent-tree scoundrel-tree}
                      {:name "Thief"
                       :key :thief
                       :skills [:computers
                                :skulduggery
                                :stealth
                                :vigilance]
                       :talent-tree thief-tree}]}
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
                                :skulduggery]
                       :talent-tree mechanic-tree}
                      {:name "Outlaw Tech"
                       :key :outlaw-tech
                       :skills [:education
                                :underworld
                                :mechanics
                                :streetwise]
                       :talent-tree outlaw-tech-tree}
                      {:name "Slicer"
                       :key :slicer
                       :skills [:computers
                                :education
                                :underworld
                                :stealth]
                       :talent-tree slicer-tree}]}])
