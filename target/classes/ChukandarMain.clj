(ns ChukandarMain
  (:require [ChukandarStepAbstracter :refer :all]
            [ElementRepository :refer :all]
            [FunctionRepository :refer :all])
  (:import BDD_automation_framework.WebDriverProvider
           BDD_automation_framework.WebOperation
           org.openqa.selenium.By))

(def webOperation (atom nil))

(doall (map #(derive % ::element) (keys elements)))
(doall (map #(derive % ::browser) (keys browsers)))

(defmulti action-resolver (fn [action element:function & _] [action element:function]))


(defmethod action-resolver [::get ::browser] [_ element:function input]
  (swap! webOperation (fn [x] (WebOperation. (element:function browsers))))
  (.get @webOperation input))

(defmethod action-resolver [::click ::element] [_ element:function _]
  (.click @webOperation (element:function elements)))

(defmethod action-resolver [::select ::element] [_ element:function input]
  (println (element:function elements)))

(defmethod action-resolver [::sendKey ::element] [_ element:function input]
  (.sendKey @webOperation (element:function elements) input))

(defmethod action-resolver [::getName ::element] [_ element:function input]
  (println (element:function elements)))

(defmethod action-resolver [:Compose :Mail] [action element:function input]
  (println (get functions [action element:function])))

(defmethod action-resolver [:Reply :Mail] [action element:function input]
  (println (get functions [action element:function])))



(defn keyword-converter [[action element input]]
  [(keyword "ChukandarMain" action) (keyword "ElementRepository" element) input])

(defn >> [step]
  (let [x (map #(keyword-converter %) (step-collector step))]
    (doall (map #(apply action-resolver %) x))))

#_ ((action-resolver ::get :ElementRepository/internet-explorer ::input)
     (action-resolver ::click :ElementRepository/user ::input)
     (action-resolver ::select :ElementRepository/password ::input)
     (action-resolver ::sendKey :ElementRepository/login ::input)
     (action-resolver ::getName :ElementRepository/login ::input)
     (action-resolver :Compose :Mail ::input)
     (action-resolver :Reply :Mail ::input))

;(println (element:function browsers))