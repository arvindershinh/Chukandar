(ns Chukandar-BDD-Test2
  (:require [ChukandarMain :refer :all] [Chukandar-BDD-Test1 :refer :all]))

(defn Login []
  (>> '(:given user open url "http://demo.guru99.com/selenium/newtours/" in chrome browser))
  (+> '(:and user perform Credential-Details Scenario))
  (>> '(:then user click on Sign-In button)))

(Login)







