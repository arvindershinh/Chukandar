(ns Chukandar-BDD-Test1
  (:require [ChukandarMain :refer :all]))

(defn Credential-Details []
  (>> '(:and user enter "Arvinder Shinh" in user-name))
  (>> '(:and user enter "123@chukandar" in password)))




