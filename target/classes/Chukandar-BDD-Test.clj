(ns Chukandar-BDD-Test
  (:require [ChukandarMain :refer :all]))


;(>> '(user enter "arvinder" in user-name and click login button))
(>> '(user open url "http://demo.guru99.com/selenium/newtours/" in chrome browser))
(>> '(user enter "Arvinder Shinh" in user-name))
(>> '(user enter "123@chukandar" in password))
(>> '(user click on Sign-In button))

