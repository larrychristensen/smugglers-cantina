(ns smugglers-cantina.rules.species.eote)

(def species
  [{:name "Bothan"
    :key :bothan
    :characteristics {:brawn 1
                      :agility 2
                      :intellect 2
                      :cunning 3
                      :willpower 2
                      :presence 2}
    :strain-threshold 11}
   {:name "Droid"
    :key :droid
    :characteristics {:brawn 1
                      :agility 1
                      :intellect 1
                      :cunning 1
                      :willpower 1
                      :presence 1}}
   {:name "Gand"
    :key :gand
    :characteristics {:brawn 2
                      :agility 2
                      :intellect 2
                      :cunning 2
                      :willpower 3
                      :presence 1}}
   {:name "Human"
    :key :human
    :characteristics {:brawn 2
                      :agility 2
                      :intellect 2
                      :cunning 2
                      :willpower 2
                      :presence 2}}
   {:name "Rodian"
    :key :rodian
    :characteristics {:brawn 2
                      :agility 3
                      :intellect 2
                      :cunning 2
                      :willpower 1
                      :presence 2}}
   {:name "Trandoshan"
    :key :trandoshan
    :characteristics {:brawn 3
                      :agility 1
                      :intellect 2
                      :cunning 2
                      :willpower 2
                      :presence 2}
    :wound-threshold 12
    :strain-threshold 9}
   {:name "Twi'lek"
    :key :twi-lek
    :characteristics {:brawn 1
                      :agility 2
                      :intellect 2
                      :cunning 2
                      :willpower 2
                      :presence 3}
    :strain-threshold 11}
   {:name "Wookie"
    :key :wookie
    :characteristics {:brawn 3
                      :agility 2
                      :intellect 2
                      :cunning 2
                      :willpower 1
                      :presence 2}
    :wound-threshold 14
    :strain-threshold 8}])
