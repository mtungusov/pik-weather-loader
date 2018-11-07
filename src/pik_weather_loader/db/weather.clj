(ns pik-weather-loader.db.weather
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "weather/c.sql")
