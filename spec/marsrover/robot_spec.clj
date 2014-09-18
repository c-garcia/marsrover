;; Examples for the robot management functions
(ns marsrover.robot-spec
  (:require [speclj.core :refer :all]
            [marsrover.robot :refer :all]
            [marsrover.grid :refer [make-grid]]))

(describe "robot"
          (describe "creation"
                    (let [[x y dir] [0 0 \n]]
                      (context (str "given the position [" x "," y "], the direction " dir )
                               (context "when I create a robot"
                                        (with grid (make-grid [100 100]))
                                        (with sut (make-robot [x y] dir @grid))
                                        (it "does exist"
                                            (should-not-be-nil @sut))
                                        (it "has the correct position"
                                            (should= [x y] (get-robot-position @sut)))))))
          (describe "movement"
                    (let [[x y dir] [0 0 \n]]
                      (context (str "given a 100x 100 grid, a robot in the position [" x "," y "] and facing " dir )
                               (with grid (make-grid [100 100]))
                               (with initial (make-robot [x y] dir @grid))
                               (context "when I ask it to move forward"
                                         (with sut (move-robot @initial \f))
                                         (it (str "ends at " [x (inc y)])
                                             (should= [0 1] (get-robot-position @sut)))
                                         (it (str "and faces " dir)
                                             (should= \n (get-robot-direction @sut))))
                               (context "when I ask it to turn left"
                                         (with sut (move-robot @initial \l))
                                         (it (str "ends at " [x y])
                                             (should= [0 0] (get-robot-position @sut)))
                                         (it (str "and faces " \w)
                                             (should= \w (get-robot-direction @sut))))
                               (context "when I ask it to turn right"
                                         (with sut (move-robot @initial \r))
                                         (it (str "ends at " [x y])
                                             (should= [0 0] (get-robot-position @sut)))
                                         (it (str "and faces " \e)
                                             (should= \e (get-robot-direction @sut))))
                               (context "when I ask it to move backwards"
                                         (with sut (move-robot @initial \b))
                                         (it (str "ends at " [x y])
                                             (should= [0 99] (get-robot-position @sut)))
                                         (it (str "and faces " \n)
                                             (should= \n (get-robot-direction @sut))))
                               ))))



