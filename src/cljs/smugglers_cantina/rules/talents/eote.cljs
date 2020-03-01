(ns smugglers-cantina.rules.talents.eote)

(def talents
  [{:name "Anatomy Lessons", :key :anatomy-lessons}
   {:name "Armor Master", :key :armor-master}
   {:name "Bacta Specialist", :key :bacta-specialist}
   {:name "Bad Motivator", :key :bad-motivator}
   {:name "Barrage", :key :barrage}
   {:name "Billiant Evasion", :key :billiant-evasion}
   {:name "Black Market Contacts", :key :black-market-contacts}
   {:name "Blooded", :key :blooded}
   {:name "Body Guard", :key :body-guard}
   {:name "Brace", :key :brace}
   {:name "Bypass Security", :key :bypass-security}
   {:name "Codebreaker", :key :codebreaker}
   {:name "Command", :key :command}
   {:name "Confidence", :key :confidence}
   {:name "Contraption", :key :contraption}
   {:name "Convincing Demeanor", :key :convincing-demeanor}
   {:name "Crippling Blow", :key :crippling-blow}
   {:name "Dead to Rights", :key :dead-to-rights}
   {:name "Deadly Accuracy", :key :deadly-accuracy}
   {:name "Dedication", :key :dedication}
   {:name "Defensive Driving", :key :defensive-driving}
   {:name "Defensive Slicing", :key :defensive-slicing}
   {:name "Defensive Stance", :key :defensive-stance}
   {:name "Disorient", :key :disorient}
   {:name "Dodge", :key :dodge}
   {:name "Durable", :key :durable}
   {:name "Enduring", :key :enduring}
   {:name "Expert Tracker", :key :expert-tracker}
   {:name "Familiar Suns", :key :familiar-suns}
   {:name "Feral Strength", :key :feral-strength}
   {:name "Field Commander", :key :field-commander}
   {:name "Fine Tuning", :key :fine-tuning}
   {:name "Forager", :key :forager}
   {:name "Frenzied Attack", :key :frenzied-attack}
   {:name "Full Throttle", :key :full-throttle}
   {:name "Galaxy Mapper", :key :galaxy-mapper}
   {:name "Gearhead", :key :gearhead} {:name "Grit", :key :grit}
   {:name "Hard Headed", :key :hard-headed}
   {:name "Heightened Awareness", :key :heightened-awareness}
   {:name "Heroic Fortitude", :key :heroic-fortitude}
   {:name "Hidden Storage", :key :hidden-storage}
   {:name "Hold Together", :key :hold-together}
   {:name "Hunter", :key :hunter}
   {:name "Improved Armor Master", :key :improved-armor-master}
   {:name "Improved Dead to Rights", :key :improved-dead-to-rights}
   {:name "Improved Defensive Slicing",
    :key :improved-defensive-slicing}
   {:name "Improved Field Commander", :key :improved-field-commander}
   {:name "Improved Full Throttle", :key :improved-full-throttle}
   {:name "Improved Hard Headed", :key :improved-hard-headed}
   {:name "Improved Inspiring Rhetoric",
    :key :improved-inspiring-rhetoric}
   {:name "Improved Scathing Tirade", :key :improved-scathing-tirade}
   {:name "Improved Stim Application",
    :key :improved-stim-application}
   {:name "Improved Stunning Blow", :key :improved-stunning-blow}
   {:name "Indistinguishable", :key :indistinguishable}
   {:name "Inspiring Rhetoric", :key :inspiring-rhetoric}
   {:name "Intense Focus", :key :intense-focus}
   {:name "Intense Presence", :key :intense-presence}
   {:name "Intimidating", :key :intimidating}
   {:name "Inventor", :key :inventor}
   {:name "Inventory", :key :inventory}
   {:name "Jump Up", :key :jump-up}
   {:name "Jury Rigged", :key :jury-rigged}
   {:name "Kill With Kindness", :key :kill-with-kindness}
   {:name "Knockdown", :key :knockdown}
   {:name "Know Somebody", :key :know-somebody}
   {:name "Knowledge Specialization", :key :knowledge-specialization}
   {:name "Known Schematic", :key :known-schematic}
   {:name "Lethal Blows", :key :lethal-blows}
   {:name "Let's Ride", :key :lets-ride}
   {:name "Master Doctor", :key :master-doctor}
   {:name "Master Merchant", :key :master-merchant}
   {:name "Master of Shadows", :key :master-of-shadows}
   {:name "Master Pilot", :key :master-pilot}
   {:name "Master Starhopper", :key :master-starhopper}
   {:name "Mental Fortress", :key :mental-fortress}
   {:name "Natural Brawler", :key :natural-brawler}
   {:name "Natural Charmer", :key :natural-charmer}
   {:name "Natural Doctor", :key :natural-doctor}
   {:name "Natural Enforcer", :key :natural-enforcer}
   {:name "Natural Hunter", :key :natural-hunter}
   {:name "Natural Marksman", :key :natural-marksman}
   {:name "Natural Negotiator", :key :natural-negotiator}
   {:name "Natural Outdoorsman", :key :natural-outdoorsman}
   {:name "Natural Pilot", :key :natural-pilot}
   {:name "Natural Programmer", :key :natural-programmer}
   {:name "Natural Rogue", :key :natural-rogue}
   {:name "Natural Scholar", :key :natural-scholar}
   {:name "Natural Tinkerer", :key :natural-tinkerer}
   {:name "Nobody's Fool", :key :nobodys-fool}
   {:name "Outdoorsman", :key :outdoorsman}
   {:name "Plausible Deniability", :key :plausible-deniability}
   {:name "Point Blank", :key :point-blank}
   {:name "Precise Aim", :key :precise-aim}
   {:name "Pressure Point", :key :pressure-point}
   {:name "Quick Draw", :key :quick-draw}
   {:name "Quick Strike", :key :quick-strike}
   {:name "Rapid Reaction", :key :rapid-reaction}
   {:name "Rapid Recovery", :key :rapid-recovery}
   {:name "Redundant Systems", :key :redundant-systems}
   {:name "Researcher", :key :researcher}
   {:name "Resolve", :key :resolve}
   {:name "Respected Scholar", :key :respected-scholar}
   {:name "Scathing Tirade", :key :scathing-tirade}
   {:name "Second Wind", :key :second-wind}
   {:name "Shortcut", :key :shortcut}
   {:name "Side Step", :key :side-step}
   {:name "Skilled Jockey", :key :skilled-jockey}
   {:name "Smooth Talker", :key :smooth-talker}
   {:name "Sniper Shot", :key :sniper-shot}
   {:name "Soft Spot", :key :soft-spot}
   {:name "Solid Repairs", :key :solid-repairs}
   {:name "Spare Clip", :key :spare-clip}
   {:name "Speaks Binary", :key :speaks-binary}
   {:name "Stalker", :key :stalker}
   {:name "Steely Nerves", :key :steely-nerves}
   {:name "Stim Application", :key :stim-application}
   {:name "Street Smarts", :key :street-smarts}
   {:name "Stroke of Genius", :key :stroke-of-genius}
   {:name "Strong Arm", :key :strong-arm}
   {:name "Stunning Blow", :key :stunning-blow}
   {:name "Supreme Full Throttle", :key :supreme-full-throttle}
   {:name "Supreme Inspiring Rhetoric",
    :key :supreme-inspiring-rhetoric}
   {:name "Supreme Scathing Tirade", :key :supreme-scathing-tirade}
   {:name "Supreme Stim Application", :key :supreme-stim-application}
   {:name "Surgeon", :key :surgeon} {:name "Swift", :key :swift}
   {:name "Targeted Blow", :key :targeted-blow}
   {:name "Technical Aptitude", :key :technical-aptitude}
   {:name "Tinkerer", :key :tinkerer}
   {:name "Toughened", :key :toughened}
   {:name "Tricky Target", :key :tricky-target}
   {:name "True Aim", :key :true-aim}
   {:name "Utility Belt", :key :utility-belt}
   {:name "Utinni!", :key :utinni!}
   {:name "Well Rounded", :key :well-rounded}
   {:name "Wheel and Deal", :key :wheel-and-deal}])
