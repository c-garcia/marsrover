; core user facing functions
(ns marsrover.core
  (:require [marsrover.grid :refer :all]
            [marsrover.robot :refer :all]))

(defn tell-robot
  "tells the robot to execute a sequence of commands. Each command is a character 
  f: forward, b: backwards, l: left, r: right.
  The robot will try to execute all the commands in the sequence, if it can it will return
  a map {:status :ok :robot robot}. Robot will be at the position determined by the sequence of
  commands.
  If an obstacle is found preventing it from moving, the robot will stop at that position and report back the
  position it is located at. {:status :obstacle-found :where [x y] :robot robot}."

  [robot cmd-seq]
  (if (nil? cmd-seq)
    {:robot robot :status :ok}
    (let [[curr-cmd & next-cmds] cmd-seq
          robot-candidate (move-robot robot curr-cmd)
          pos-candidate (get-robot-position robot-candidate)
          grid (:grid robot)]
      (if (grid-obstacle-at? grid pos-candidate)
        {:robot robot :status :obstacle-found :where pos-candidate}
        (recur robot-candidate next-cmds)))))

