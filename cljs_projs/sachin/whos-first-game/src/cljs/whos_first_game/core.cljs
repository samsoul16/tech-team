(ns whos-first-game.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to whos-first-game"]])


;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))


(def board (atom {:size 10
                  :chance 0
                  :a {:current-pos [0 0]
                      :dir :right
                      :color "red"
                      :path []}

                  :b {:current-pos [0 0]
                      :dir :right
                      :color "red"
                      :path []}
                  :board []}))

(defn make-board
  []
  (let [size (:size @board)])
  (apply vector (for [x (range size)
                      y (range size)]
                  {:pos-x x :pos-y y :value ""})))

(defn fill-board
  []
  (let [size (:size @board)]
    (map #(swap! board assoc-in [:board % :value] %) (range (* size size)))))


(defn get-element
  [i j]
  (first (filter (fn [x]
                   (let [values (map val x)]
                     (and
                      (= i (first values))
                      (= j (second values)))))
                 (:board @board))))

(defn get-position
  [value]
  (let [map1  (filter (fn [x]
                        (= value (:value x)))
                      (get-in @board [:board]))
        val-map (flatten (map vals map1))]
    [(first val-map) (second val-map)]))



(defn update-current-position
  [x y]
  (swap! board assoc-in [:current-pos] [x y]))


(defn move-player
  [steps direction]
  (let [[x-pos y-pos] (:current-pos @board)]
    (condp = direction
      :right (let [ans (mapv (fn [y]
                               (:value (get-element x-pos y)))
                             (range y-pos (+ y-pos steps)))
                   [x y] (get-position (last ans))]
               (update-current-position (inc x) y)
               ans)

      :down (let [ans (mapv (fn [x]
                              (:value (get-element x y-pos)))
                            (range x-pos (+ x-pos steps)))
                  [x y] (get-position (last ans))]
              (update-current-position x (dec  y))
              ans)

      :left (let [ans (mapv (fn [y]
                              (:value (get-element x-pos y)))
                            (reverse  (range (- (inc  y-pos) steps) (inc y-pos))))
                  [x y] (get-position (last ans))]
              (update-current-position (dec x) y)
              ans)

      :up (let [ans (mapv (fn [x]
                            (:value (get-element x y-pos)))
                          (reverse (range (- (inc  x-pos) steps) (inc x-pos))))
                [x y] (get-position (last ans))]
            (update-current-position x (inc y))
            ans))))



(defn change-direction
  [dir]
  (swap! board assoc-in [:dir] dir))


(defn execute-spiral
 [element]
  (mapcat (fn [x]
            (let [ele (element @board)
                  dir (:dir ele)
                  cur-pos (:current-pos ele)]
              (condp = dir
                :right (do
                         (change-direction :down)
                         (move-player x :right))

                :down  (do
                         (change-direction :left)
                         (move-player x :down))

                :left (do
                        (change-direction :up)
                        (move-player x :left))

                :up (do
                      (change-direction :right)
                      (move-player x :up)))))
          (get-path-dir (:size @board))))

(defn get-path-dir
  [size]
  (reverse (drop 1
                 (drop-last (mapcat
                             (fn [x y] [x y])
                             (range 2 (+ 2 size) 2) (range 2 (+ 2 size) 2))))))
