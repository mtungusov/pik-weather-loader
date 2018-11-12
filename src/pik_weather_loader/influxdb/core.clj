(ns pik-weather-loader.influxdb.core
  (:require [org.httpkit.client :as http]
            [mount.core :refer [defstate]]
            [pik-weather-loader.config :refer [settings]]))


(defstate influxdb
          :start
          {:default-params {:url (get-in settings [:influxdb :url])
                            :method :post
                            :timeout 100
                            :query-params {:db (get-in settings [:influxdb :db])}
                            :basic-auth [(get-in settings [:influxdb :user]) (get-in settings [:influxdb :password])]}})


(defn write [data]
  (let [params (assoc (:default-params influxdb) :body data)]
    (http/request params)))


;(identity settings)
;(def r (write-metric "request_count,api=yandex,status=200 value=5"))
;(identity @r)
