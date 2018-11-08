(ns pik-weather-loader.db.weather-c
  (:require [hugsql.core :as hugsql]
            [pik-weather-loader.utils :refer [to-json]]))


(hugsql/def-db-fns "weather/c.sql")


(defn weather-to-db [db v]
  (let [db-val (update v :forecast to-json)]
    (weather! db db-val)))
