(ns pik-weather-loader.loader
  (:require [clojure.java.jdbc :refer [with-db-transaction]]
            [clojure.tools.logging :as log]
            [pik-weather-loader.db :refer [db-tableau db-weather]]
            [pik-weather-loader.db.tableau :as nsi]
            [pik-weather-loader.db.weather-c :as weather-c]
            [pik-weather-loader.db.weather-q :as weather-q]
            [pik-weather-loader.yandex.core :refer [forecast process-one]]))


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
;(weather-q/projects db-weather)


(defn- forecast-to-db [v]
  (let [f (process-one v)
        {:keys [error uid]} f]
    (if error
      (log/error (str "Process error: " error ", uid:" uid))
      (weather-c/weather-to-db db-weather f))))


;(defn load-forecasts []
;  (let [projects (weather-q/projects db-weather)
;        futures (doall (map forecast projects))]
;    (doseq [v futures]
;      (forecast-to-db v))))


(defn- load-some-forecasts [projects]
  (log/info "Load " (count projects) " forecasts")
  (doseq [p projects]
    (-> p
        (forecast)
        (forecast-to-db))))


(defn load-forecasts []
  (let [projects (weather-q/projects db-weather)]
    (doseq [pp (partition-all 4 projects)]
      (load-some-forecasts pp))))

;(def t '(1 2 3 4 5 6 7))
;(partition-all 4 t)


;(identity f)
;(load-forecasts)
