(ns caesar-cipher.core-test
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [caesar-cipher.core :refer :all]
            [clojure.string :as str]))

(def gen-lower-case (gen/such-that lower-case? gen/char-alpha 100))

(defspec lower-case?-works
  100
  (prop/for-all [c gen/char-ascii]
    (= (lower-case? c)
       (and (>= (int c) (int \a))
            (<= (int c) (int \z))))))

(defspec let-to-num-returns-0-to-25
  100
  (prop/for-all [c gen-lower-case]
    (let [v (let-to-num c)]
      (and (>= v 0)
           (< v 26)))))

(defspec let-to-num-to-let
  100
  (prop/for-all [c gen-lower-case]
    (= c (num-to-let (let-to-num c)))))

(defspec shift-round-trip
  100
  (prop/for-all [n (gen/elements (range 26))
                 c gen-lower-case]
     (= (num-to-let (shift (* -1 n) (shift n (let-to-num c))))
        c)))

(defspec shift-returns-0-to-25
  100
  (prop/for-all [n (gen/elements (range 26))
                 c gen-lower-case]
    (let [v (shift n (let-to-num c))]
      (and (>= v 0)
           (< v 26)))))

(defspec xlate-let-combines-the-steps
  100
  (prop/for-all [n (gen/elements (range 26))
                 c gen-lower-case]
    (= (num-to-let (shift n (let-to-num c)))
       (xlate-let n c))))

(defspec translate-round-trip
  100
  (prop/for-all [n (gen/elements (range 26))
                 v gen/string-ascii]
    (= (translate (* -1 n) (translate n v))
       v)))

(defspec translate-only-lowercase
  100
  (prop/for-all [n (gen/elements (range 26))
                 s  gen/string-ascii]
    (= (remove lower-case? s)
       (remove lower-case? (translate n s)))))

(defspec translate-0-change-nothing
  100
  (prop/for-all [s gen/string-ascii]
    (= s (translate 0 s))))

(defspec translate-changes-things
  100
  (prop/for-all [n (gen/elements (range 1 26))
                 v (gen/vector gen-lower-case)]
    (not (= v (translate n v)))))
