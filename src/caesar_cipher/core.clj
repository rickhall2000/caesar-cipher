(ns caesar-cipher.core)

(defn lower-case? [c]
  (and (>= (int c) (int \a))
       (<= (int c) (int \z))))

(defn let-to-num [l]
  (- (int l) (int \a)))

(defn num-to-let [l]
  (char (+ l (int \a))))

(defn shift [n i]
  (mod (+ i n) 26))

(defn xlate-let [n let]
  (->> (let-to-num let)
       (shift n)
       (num-to-let)))

(defn translate [n phrase]
  (apply str
         (for [letter phrase]
           (if (lower-case? letter)
             (xlate-let n letter)
             letter))))
