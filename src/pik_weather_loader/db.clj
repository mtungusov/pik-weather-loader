(ns pik-weather-loader.db
  (:require [mount.core :refer [defstate]]
            [pik-weather-loader.config :refer [settings]]))


(defstate db-tableau
          :start
          {:subprotocol (get-in settings [:db-tableau :subprotocol])
           :subname     (get-in settings [:db-tableau :subname])
           :user        (get-in settings [:db-tableau :user])
           :password    (get-in settings [:db-tableau :password])})


(defstate db-weather
          :start
          {:subprotocol (get-in settings [:db-weather :subprotocol])
           :subname     (get-in settings [:db-weather :subname])
           :user        (get-in settings [:db-weather :user])
           :password    (get-in settings [:db-weather :password])})


;(identity settings)
;(q/projects db-tableau)
;(def t (q/projects db-tableau))
;(class (:lat (first t)))
