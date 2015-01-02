(ns caesar-cipher.solver-test
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [caesar-cipher.solver :refer :all]
            [clojure.string :as str]))

(defspec get-weights-generates-frequencies
  100
  (prop/for-all [s (gen/not-empty (gen/vector gen/char-alpha))]
     (let [test-str (str/lower-case (apply str s))
          test-len (count test-str)
          test-freq (frequencies test-str)
          test-res (get-weights test-str)]
      (=  test-res (reduce merge
                           (map (fn [[k v]] {k (/ v test-len)})
                                test-freq))))))

(defspec get-weights-handles-ascii
  100
  (prop/for-all [s gen/string-ascii]
     (let [test-str (str/lower-case s)
          test-len (count test-str)
          test-res (get-weights test-str)]
       (=  test-res (get-weights s)))))

(defspec error-for-item-test
  100
  (prop/for-all [c gen/char-alpha
                 n1 (gen/choose 5 50)
                 d1 (gen/choose 100 500)
                 n2 (gen/choose 5 50)
                 d2 (gen/choose 100 500)]
     (let [obs (/ n1 d1)
           expc (/ n2 d2)
           dif (- obs expc)]
       (= (/ (* dif dif) expc) (error-for-item {c obs} {c expc})))))

(defspec error-for-msg-test
  100
  (prop/for-all [msg gen/string-alpha-numeric
                 lang gen/string-alpha-numeric]
                (let [authority (get-weights msg)]
                  (= (error-for-msg msg authority)
                     (reduce +
                             (map (fn [[k v]] (error-for-item {k v} authority))
                                  (get-weights msg)))))))
