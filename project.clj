(defproject caesar-cipher "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.5.9"]
                 [org.clojure/core.typed "0.2.74"]]
  :core.typed {:check [caesar-cipher.core]}
  :plugins [[lein-typed "0.3.5"]])
