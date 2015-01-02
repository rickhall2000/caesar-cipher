(ns caesar-cipher.core
  (:require [clojure.core.typed :refer [ann] :as t]))

(ann lower-case? [Number -> Boolean])
(defn lower-case? [c]
  (and (>= (int c) (int \a))
       (<= (int c) (int \z))))

(ann let-to-num [char -> Number])
(defn let-to-num [l]
  (- (int l) (int \a)))

(ann num-to-let [Number -> char])
(defn num-to-let [l]
  (char (+ l (int \a))))

(ann shift [Number Number -> Number])
(defn shift [n i]
  (mod (+ i n) 26))

(ann xlate-let [Number char -> char])
(defn xlate-let [n let]
  (->> (let-to-num let)
       (shift n)
       (num-to-let)))

(ann ^:no-check translate [Number String -> String])
(defn translate [n phrase]
  (apply str
         (for [letter phrase]
           (if (lower-case? letter)
             (xlate-let n letter)
             letter))))
