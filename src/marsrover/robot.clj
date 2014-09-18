; robot management functions
(ns marsrover.robot
  (:require [marsrover.grid :refer [get-grid-dimensions]]))

(defn make-robot
  "creates a robot on a grid"
  [pos dir grid]
  {:pos pos :dir dir :grid grid})

(defn get-robot-position
  "gets the robot position as [x y]"
  [robot]
  (:pos robot))

(defn get-robot-direction
  "gets robot direction as n w s e"
  [robot]
  (:dir robot))

(defn get-robot-grid
  "gets the grid the robot is in"
  [robot]
  (:grid robot))

(defn- make-edge-wrap-fn
  "wraps the robot position across the grid edges"
  [[w h]]
  (fn [[x y]] [ (mod x w) (mod y h) ]))

(defn- move-forward
  "moves robot forward"
  [robot]
  (let [adv-vector (case (get-robot-direction robot)
                     \n [0 1]
                     \e [1 0]
                     \s [0 -1]
                     \w [-1 0])]
    (let [edge-wrap (make-edge-wrap-fn (-> robot get-robot-grid get-grid-dimensions))
          new-pos (map  + adv-vector (get-robot-position robot))
          wrapped-pos (edge-wrap new-pos)]
      (make-robot 
        wrapped-pos
        (get-robot-direction robot)
        (:grid robot)))))

(defn- move-backwards
  "moves robot backwards"
  [robot]
  (let [adv-vector (case (get-robot-direction robot)
                     \n [0 -1]
                     \e [-1 0]
                     \s [0 1]
                     \w [1 0])]
    (let [edge-wrap (make-edge-wrap-fn (-> robot get-robot-grid get-grid-dimensions))
          new-pos (map + adv-vector (get-robot-position robot))
          wrapped-pos (edge-wrap new-pos)]
      (make-robot 
        wrapped-pos
        (get-robot-direction robot)
        (:grid robot)))))

(defn- turn-right 
  "turns robot right"
  [robot]
  (let [new-pos (get-robot-position robot)]
    (make-robot
      new-pos
      (-> (filter (fn [[x _]] (= x (get-robot-direction robot))) [[\n \e][\e \s][\s \w][\w \n]])
          first
          second)
      (:grid robot))))

(defn- turn-left 
  "turns robot left"
  [robot]
  (let [new-pos (get-robot-position robot)]
    (make-robot
      new-pos
      (-> (filter (fn [[x _]] (= x (get-robot-direction robot))) [[\n \w][\e \n][\s \e][\w \s]])
          first
          second)
      (:grid robot))))

(defn move-robot
  "moves robot according to a single command"
  [robot cmd]
  (case cmd
    \f (move-forward robot)
    \b (move-backwards robot)
    \l (turn-left robot)
    \r (turn-right robot)))
