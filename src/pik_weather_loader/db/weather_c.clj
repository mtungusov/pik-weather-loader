(ns pik-weather-loader.db.weather-c
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "weather/c.sql")
