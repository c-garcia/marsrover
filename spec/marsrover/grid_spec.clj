;; Examples for the grid management functions
(ns marsrover.grid-spec
  (:require [speclj.core :refer :all]
            [marsrover.grid :refer :all]))

(describe "grid"
          (describe "creation without obstacles"
                    (context "given 10 15 as dimensions"
                              (context "when I create a grid"
                                      (with sut (make-grid [10 15]))
                                      (it "has the proper dimensions"
                                        (should= [10 15] (get-grid-dimensions @sut))))))
          (describe "creation with obstacles"
                    (context "given 4 4 as dimensions and obstacles at [1 1] [2 2]"
                             (context "when I create a grid"
                                      (with sut (make-grid [4 4] [[1 1] [2 2]]))
                                      (it "has the proper dimensions"
                                          (should= [4 4] (get-grid-dimensions @sut)))
                                      (it "has the proper obstacles"
                                          (should= (sort [[1 1] [2 2]]) (sort (get-grid-obstacles @sut)))))))
          (describe "check obstacles"
                    (context "given a grid 4 4 as dimensions and obstacles at [1 1] [2 2]"
                             (with sut (make-grid [4 4] [[1 1][2 2]]))
                             (context "when I check if there is an obstacle in [1 1]"
                                      (it "says yes"
                                          (should (grid-obstacle-at? @sut [1 1]))))
                             (context "when I check if there is an obstacle in [2 2]"
                                      (it "says yes"
                                          (should (grid-obstacle-at? @sut [2 2]))))
                             (context "when I check if there is an obstacle in [2 3]"
                                      (it "says no"
                                          (should-not (grid-obstacle-at? @sut [2 3])))))))
