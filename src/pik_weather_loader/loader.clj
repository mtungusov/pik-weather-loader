(ns pik-weather-loader.loader
  (:require [clojure.java.jdbc :refer [with-db-transaction]]
            [pik-weather-loader.db :refer [db-tableau db-weather]]
            [pik-weather-loader.db.tableau :as nsi]
            [pik-weather-loader.db.weather-c :as weather-c]
            [pik-weather-loader.db.weather-q :as weather-q]))


(defn update-projects []
  (let [values (nsi/projects db-tableau)]
    (with-db-transaction [tx db-weather]
      (weather-c/disable-projects! tx)
      (doseq [v values]
        (weather-c/project! tx v)))))


;(nsi/projects db-tableau)
;(weather-c/disable-projects! db-weather)
;(update-projects)

;(class (:lat (first (weather-q/projects db-weather))))
