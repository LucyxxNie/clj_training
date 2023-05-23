(ns clj-training.mount
  (:require
    [mount.core :as mount :refer [defstate]]))

(def refnum [{:a 1 :b 2}
             {:a 1 :b 2}
             {:a 2 :b 3}])

(def num1 (atom nil))
(def num2 (atom nil))

(into nil refnum)

(defn change-num1
  [num]
  (swap! num into refnum))

(defn reset-num!
  [num]
  (reset! num nil))

(defstate change-num1
          :start (change-num1 num1)
          :stop (reset-num! num1))

#_(defstate change-num2
            :start (inc-num num2)
            :stop (reset-num! num2))

(deref num1)
(mount/start)

(deref num1)


(mount/stop)

(deref num1)
