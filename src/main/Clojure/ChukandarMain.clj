(ns ChukandarMain
  (:require [ChukandarStepAbstracter :refer :all]
            [ElementRepository :refer :all])
  (:import BDD_automation_framework.WebDriverProvider
           BDD_automation_framework.WebOperation
           org.openqa.selenium.By))

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

(def this-ns "ChukandarMain")
(def webOperation (atom nil))

(def local-elements (reduce #(assoc %1 (->> %2 (key) (name) (keyword this-ns)) (val %2)) {} elements))
(def local-browsers (reduce #(assoc %1 (->> %2 (key) (name) (keyword this-ns)) (val %2)) {} browsers))

(doall (map #(derive % ::element) (keys local-elements)))
(doall (map #(derive % ::browser) (keys local-browsers)))

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(defmulti action-resolver (fn [action element:function & _] [action element:function]))

(defmethod action-resolver [::get ::browser] [_ element:function input]
  (swap! webOperation (fn [x] (WebOperation. (element:function local-browsers))))
  (.get @webOperation input))

(defmethod action-resolver [::click ::element] [_ element:function _]
  (.click @webOperation (element:function local-elements)))

(defmethod action-resolver [::select ::element] [_ element:function input]
  (println (element:function local-elements)))

(defmethod action-resolver [::sendKey ::element] [_ element:function input]
  (.sendKey @webOperation (element:function local-elements) input))

(defmethod action-resolver [::getName ::element] [_ element:function input]
  (println (element:function local-elements)))

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(defn keyword-converter [[action element input]]
  [(keyword this-ns action) (keyword this-ns element) input])

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(defn >> [step]
  (let [x (map #(keyword-converter %) (step-collector step))]
    (doall (map #(apply action-resolver %) x))))

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(defmacro +> [step]
  `(eval (->> ~step
              (partition 2 1)
              (keep #(if (= (-> % (last) (name) (.toLowerCase)) "scenario") (first %)))
              )))

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;(>> '(user enter "arvinder" in user-name and click login button))
;(defmacro Scenario [scenario-name & steps] `(do ~@steps))
;(defmacro +> [step] `(~step))