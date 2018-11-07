(ns pik-weather-loader.db.weather-q
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "weather/q.sql")
