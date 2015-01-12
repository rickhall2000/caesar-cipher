(ns caesar-cipher.solver
  (:require [caesar-cipher.core :refer [lower-case?]]
            [clojure.string :as str]))

(defn get-weights [msg]
  (let [base (->> msg (str/lower-case) (filter lower-case?))
        denom (count base)
        freqs (frequencies base)]
    (->> (frequencies base)
         (reduce-kv (fn [m k v]
                      (assoc m k (/ v denom))) {}))))

(defn error-for-item [test authority]
  (let [[char observed] (first test)
        expected (get authority char 0)
        diff (- observed expected)]
    (if (zero? expected)
        (* diff diff)
        (/ (* diff diff) expected))))

(defn error-for-msg [msg authority]
  (let [observations (get-weights msg)]
    (reduce +
            (map (fn [[k v]] (error-for-item {k v} authority)) observations))))
