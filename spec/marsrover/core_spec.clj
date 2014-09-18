; Examples for the user facing API
(ns marsrover.core-spec
  (:require [speclj.core :refer :all]
            [marsrover.core :refer :all]
            [marsrover.robot :refer [make-robot get-robot-position get-robot-direction]]
            [marsrover.grid :refer [make-grid]]))

(defn make-ok-status
  [target-pos target-dir target-grid]
  {:status :ok :robot (make-robot target-pos target-dir target-grid)})

(defn make-obstacle-found-status
  [target-pos target-dir obstacle-pos target-grid]
  {:status :obstacle-found :where obstacle-pos :robot (make-robot target-pos target-dir target-grid)})

(describe "Acceptance Examples: obstacle-free movement"
          (context "Given a 100x100 grid, and a robot facing north with on the position 0 0"
                   (with grid (make-grid [100 100]))
                   (with robot (make-robot [0 0] \n @grid))
                   (context "When I ask it to move 'ffrff'"
                            (with sut (tell-robot @robot "ffrff"))
                            (it "stops ok at [2 2] e"
                                (should= (make-ok-status [2 2] \e @grid) @sut)))
                   (context "When I ask to move 'lff'"
                            (with sut (tell-robot @robot "lff"))
                            (it "stops ok at [98 0] w"
                                (should= (make-ok-status [98 0] \w @grid) @sut)))))

(describe "Acceptance Examples: movement with obstacles"
          (context "Given a 10x10 grid, with obstacles at (1,1), (2,2) and (3,3)"
                   (with grid (make-grid [10 10] [[1 1] [2 2] [3 3]]))
                   (context "And given a robot at [0 0 n]" 
                            (with initial (make-robot [0 0] \n @grid))
                            (context "when I ask it to move 'fff'"
                                     (with sut (tell-robot @initial "ff"))
                                     (it "stops ok at [0 2] n"
                                         (should= (make-ok-status [0 2] \n @grid) @sut)))
                            (context "when I ask it to move 'rflf'"
                                     (with sut (tell-robot @initial "rflf"))
                                     (it "founds obstacle at [1 1], stops at [1 0] n"
                                         (should= (make-obstacle-found-status [1 0] \n [1 1] @grid) @sut))))))

(describe "Acceptance Examples: movement with obstacles and edge wrapping"
          (context "Given a 3x3 grid, with obstacles at (0,1) and (1,1)"
                   (with grid (make-grid [3 3] [[0 1] [1 1]]))
                   (context "And given a robot at [0 0 n]" 
                            (with initial (make-robot [0 0] \n @grid))
                            (context "when I ask it to move 'rfffr'"
                                     (with sut (tell-robot @initial "rfffr"))
                                     (it "stops ok at [0 0] s"
                                         (should= (make-ok-status [0 0] \s @grid) @sut)))
                            (context "when I ask it to move 'rfflfrf'"
                                     (with sut (tell-robot @initial "rfflfrf"))
                                     (it "founds obstacle at [0 1], stops at [2 1] e"
                                         (should= (make-obstacle-found-status [2 1] \e [0 1] @grid) @sut))))))
