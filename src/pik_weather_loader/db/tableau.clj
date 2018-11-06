(ns pik-weather-loader.db.tableau
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "tableau/q.sql")
