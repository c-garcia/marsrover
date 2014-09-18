(defproject marsrover "1.0"
  :description "Mars Rover Kata in Clojure"
  :url "https://github.com/c-garcia/marsrover"
  :license {:name "Creative Commons Attribution International 4.0"
            :url "http://creativecommons.org/licenses/by/4.0/"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[speclj "3.1.0"]]}}
  :plugins [[speclj "3.1.0"]]
  :test-paths ["spec"])
