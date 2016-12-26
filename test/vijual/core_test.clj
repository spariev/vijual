(ns vijual.core-test
  (:require [clojure.test :refer :all]
            [vijual.core :as v]))

(deftest ^:unit test-draw-tree
  (v/draw-tree [[:north-america [:usa [:miami] [:seattle] [:idaho [:boise]]]] [:europe [:germany] [:france [:paris] [:lyon] [:cannes]]]])
  (v/draw-binary-tree [[:north-america [:usa [:miami] [:seattle] [:idaho [:boise]]]] [:europe [:germany] [:france [:paris] [:lyon] [:cannes]]]]))

(deftest ^:unit test-draw-graph
  (v/draw-graph [[:a :b] [:b :c] [:c :d] [:a :d] [:e :f] [:a :f] [:g :e] [:d :e]] {:a "Upper Floor" :b "Lower Floor" :c "Garden" :d "Stairway" :e "Front Yard" :f "Basement" :g "Walk Way"})
  (v/draw-directed-graph [[:a :b] [:b :c] [:c :d] [:a :d] [:e :f] [:a :f] [:g :e] [:d :e]] {:a "Upper Floor" :b "Lower Floor" :c "Garden" :d "Stairway" :e "Front Yard" :f "Basement" :g "Walk Way"}))


