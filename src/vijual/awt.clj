(ns vijual.awt
  (:require [vijual.core :refer :all]
            [clojure.math.numeric-tower :refer [floor abs ceil]])
  (:import (java.io File)
           (javax.imageio ImageIO)
           (java.awt Color)
           (java.awt.image BufferedImage)))

(defn save-image
  "This is just a convenience function for the examples- Saves a bitmap to a file."
  [img name]
  (let [file (new File (str name ".png"))]
    (ImageIO/write (cast java.awt.image.BufferedImage img) "png" file)))

(defn draw-shapes-image
  "Draws a list of shapes to an awt image."
  [{:keys [line-wid]} shapes]
  (let [img (new BufferedImage (shapes-width shapes) (shapes-height shapes)(. BufferedImage TYPE_4BYTE_ABGR))
        graphics (. img createGraphics)]
    (doseq [{:keys [type x y width height text]} shapes]
      (when (= type :rect)
        (when (= height line-wid)
          (.setColor graphics (Color. 255 255 255))
          (.fillRect graphics (+ x line-wid) (- y line-wid) (- width (* 2 line-wid)) (* line-wid 3)))
        (when (= width line-wid)
          (.setColor graphics (Color. 255 255 255))
          (.fillRect graphics (- x line-wid) (+ y line-wid) (* line-wid 3) (- height (* 2 line-wid))))
        (.setColor graphics (Color. 0 0 0))
        (.fillRect graphics x y width height)
        (.setColor graphics (Color. 240 240 240))
        (.fillRect graphics (+ x line-wid) (+ y line-wid) (- width (* line-wid 2)) (- height (* line-wid 2)))
        (.setColor graphics (Color. 0 0 0))
        (doseq [[s n] (map vector (seq text) (iterate inc 0))]
          (.drawString graphics s (floor (+ 2 x)) (floor (+ y 8 (* n 10)))))))
    (doseq [{:keys [type x y width height dir]} shapes]
      (when (= type :arrow)
        (.setColor graphics (Color. 255 255 255))
        (.fillRect graphics x y width height)
        (.setColor graphics (Color. 0 0 0))
        (condp = dir
          :left (.fillPolygon graphics (int-array [x (+ x arrow-size) (+ x arrow-size)]) (int-array [(+ y (half height)) (- (+ y (half height)) arrow-size) (+ y (half height) arrow-size)]) 3)
          :right (.fillPolygon graphics (int-array [(+ x width) (- (+ x width) arrow-size) (- (+ x width) arrow-size)]) (int-array [(+ y (half height)) (- (+ y (half height)) arrow-size) (+ y (half height) arrow-size)]) 3)
          :up (.fillPolygon graphics (int-array [(inc (+ x (half width))) (- (+ x (half width)) arrow-size) (+ x (half width) arrow-size 1)]) (int-array [(dec y) (+ y arrow-size) (+ y arrow-size)]) 3)
          :down (.fillPolygon graphics (int-array [(+ x (half width)) (- (+ x (half width)) arrow-size) (+ x (half width) arrow-size)]) (int-array [(+ y height) (- (+ y height) arrow-size) (- (+ y height) arrow-size)]) 3))))
    img))

(defn draw-tree-image
  "Draws a tree to a java image."
  [tree]
  (draw-shapes-image image-dim (tree-to-shapes image-dim (layout-tree image-dim (idtree tree)))))

(defn draw-graph-image
  "Draws an undirected graph to a java image. Requires a list of pairs representing the edges of the graph. Additionally, a separate map can be included containing node information mapped via the node ids in the edge list."
  ([edges nodes]
     (draw-shapes-image image-dim (graph-to-shapes image-dim (layout-graph image-dim edges nodes false))))
  ([edges]
     (draw-graph-image edges {})))

(defn draw-directed-graph-image
  "Draws an directed graph to a java image. Requires a list of pairs representing the edges of the graph. Additionally, a separate map can be included containing node information mapped via the node ids in the edge list."
  ([edges nodes]
     (draw-shapes-image image-dim (graph-to-shapes image-dim (layout-graph image-dim edges nodes true))))
  ([edges]
     (draw-directed-graph-image edges {})))
