(ns smugglers-cantina.rules.talents.trees)

;; example tree
#_[[[:grid :d]
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
    [:master-of-shadows]]]

(defn talent-node-keyword [talent-key level column]
  (keyword (str (name talent-key) "-level-" (inc level) "-" (inc column))))

(defn get-offsets [dir]
  (case dir
    :u [-1 0]
    :d [1 0]
    :l [0 -1]
    :r [0 1]))

(defn make-edge [talent-tree i j dir]
  (let [[y x] (get-offsets dir)
        other-i (+ i y)
        other-j (+ j x)
        [other-key] (get-in talent-tree [other-i other-j])]
    (talent-node-keyword other-key other-i other-j)))

(defn expand-talent-tree [talent-tree]
  (map-indexed
   (fn [i row]
     (map-indexed
      (fn [j [talent-key dirs]]
        {:key (talent-node-keyword talent-key i j)
         :level (inc i)
         :talent talent-key
         :dirs (set dirs)
         :edges (map
                 (partial make-edge talent-tree i j)
                 dirs)})
      row))
   talent-tree))

(defn talent-tree-map [expanded-talent-trees]
  (into {} (map (juxt :key identity)
                (flatten expanded-talent-trees))))

(defn top-level-talent-nodes [talent-nodes]
  (filter
   (fn [{:keys [level]}]
     (= 1 level))
   talent-nodes))

(defn higher-level-talent-nodes [talent-tree-map purchased-talents]
   (flatten
    (map
     (fn [talent-node-key]
       (let [edges (get-in talent-tree-map [talent-node-key :edges])]
         (map
          talent-tree-map
          edges)))
     purchased-talents)))
