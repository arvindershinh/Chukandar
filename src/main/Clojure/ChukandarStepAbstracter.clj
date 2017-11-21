(ns ChukandarStepAbstracter
  (:require [ActionRepository :refer :all ] [ElementRepository :refer :all ]))



(defn action [actions step]
  (let [action-keys (keys actions)
        action-transformer (fn [x] (keep #(if ((->> x (.toLowerCase) (keyword)) (% actions)) (name %)) action-keys))
        action-finder (fn [x] (map #(if (symbol? %) (->> % (name) (action-transformer))) x))
        action-result (->> step (action-finder) (remove #(empty? %)))]
       (if (= (count action-result) 1)
           (->> action-result (first) (first))
           (throw (Exception. "ambiguous step - step Should have single action")))
    ))

(defn element [elements step]
  (let [element-keys (->> elements (keys) (map #(name %)) (set))
        element-finder (keep #(if (symbol? %) (->> % (name) (.toLowerCase) (get element-keys))) step)]
    (if (= (count element-finder) 1)
      (->> element-finder (first) (name))
      (throw (Exception. "ambiguous step - step Should have single element")))
    ))

(defn data [step]
  (let [data-finder (filter #(string? %) step)]
    (condp = (count data-finder)
      0 nil
      1 (->> data-finder (first) (name))
      (throw (Exception. "ambiguous step - step Should have single data string")))
    ))


(defn step-abstracter [step]
  (let [abstract-step-trans (transient [])]
    (conj! abstract-step-trans (action actions step))
    (conj! abstract-step-trans (element (-> {} (into elements) (into browsers)) step))
    (conj! abstract-step-trans (data step))
    (persistent! abstract-step-trans)))

(defn step-partitioner [step]
  (->> step
       (partition-by #(and
                        (symbol? %)
                        (= (.toLowerCase (name %)) "and"))
                     )
       (remove #(and
                  (symbol? (first %))
                  (= (.toLowerCase (name (first %))) "and")))
       ))

(defn step-collector [super-step]
  (->> super-step (step-partitioner) (map #(step-abstracter %)))
  )
