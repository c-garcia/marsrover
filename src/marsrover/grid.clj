; grid management functions
(ns marsrover.grid)

(defn make-grid 
  "Creates a grid with or without obstacles. Size is specified as [w h], 
  obstaces as [[1 2][2 3]]"
  ([[width height] obstacles-seq ]
    (letfn [(make-obstacles-map 
              [obs-seq]
              (apply hash-map (mapcat (fn [x] [x true]) obs-seq)))]
      (let [obs-map (make-obstacles-map obstacles-seq)]
        {:dim [width height] :obstacles obs-map})))
  ([size]
   (make-grid size [])))

(defn get-grid-dimensions
  "returns grid dimensions as a pair of longs"
  [grid]
  (:dim grid))

(defn get-grid-obstacles
  "returns the grid obstacles as a sequence of pairs of longs"
  [grid]
  (keys (:obstacles grid)))

(defn grid-obstacle-at?
  "checks if there is an obstacle in the position"
  [grid pos]
  (get (:obstacles grid) pos))
