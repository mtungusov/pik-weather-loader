(ns pik-weather-loader.yandex.core
  (:require [org.httpkit.client :as http]
            [clojure.tools.logging :as log]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]
            [pik-weather-loader.config :refer [settings]]
            [pik-weather-loader.utils :refer [from-json]]))


(defn- token []
  (get-in settings [:yandex :token]))


(defn- api-url []
  (get-in settings [:yandex :url]))


(def default-params {:timeout 200
                     :insecure? true
                     :headers {"X-Yandex-API-Key" (token)}
                     :query-params {:lang "en_US"
                                    :limit 3
                                    :extra true
                                    :hours true}})


(defn- params! [lat lon]
  (update-in default-params [:query-params] #(merge % {:lat lat :lon lon})))


(defn- weather-vals [v]
  {:temp (:temp v)
   :condition (:condition v)
   :wind_speed (:wind_speed v)
   :wind_gust (:wind_gust v)
   :wind_dir (:wind_dir v)})


(defn- filter-hours [d1 d2 now]
  (let [h1 (:hours d1)
        h2 (:hours d2)
        cur (t/hour (tc/from-long (* 1000 now)))]
    (cond-> {}
      (> 22 cur) (assoc :h2 (get h1 (+ cur 2)))
      (> 23 cur) (assoc :h1 (get h1 (inc cur)))
      (= 22 cur) (assoc :h2 (get h2 0))
      (= 23 cur) (merge {:h1 (get h2 0) :h2 (get h2 1)})
      true (update :h1 weather-vals)
      true (update :h2 weather-vals))))

;(t/date-time (java.util.Date. 1541600285000))
;(tc/from-long 1541600285000)
;(tc/to-long (tc/from-string "2018-11-07T23:18:05.000Z"))
;(t/hour (tc/from-long 1541600285000))


(defn- filter-day [d]
  (let [v (get-in d [:parts :day_short])]
    (weather-vals v)))


(defn- filter-forecast [f now]
  (let [day0 (get f 0)
        day1 (get f 1)
        day2 (get f 2)]
    (-> {:day1 (filter-day day1) :day2 (filter-day day2)}
        (merge (filter-hours day0 day1 now)))))


(defn- filter-body [body]
  (let [v (from-json body)
        fact (:fact v)
        f (:forecasts v)
        now (:now v)]
    (-> {:obs_time (:obs_time fact)}
        (merge (weather-vals fact))
        (merge {:forecast (filter-forecast f now)}))))


(defn forecast [{:keys [uid lat lon]}]
  (let [params (params! lat lon)
        resp (http/get (api-url) params)]
    {:uid uid
     :resp resp}))


(defn process-one [{:keys [uid resp]}]
  (try
    (let [{:keys [status error body]} @resp]
      (if error
        {:error "Network error"}
        (cond-> {:uid uid}
          (= 403 status) (assoc :error "API Authentication Error")
          (= 200 status) (assoc :body  (filter-body body)))))
    (catch Exception _
      {:error (identity Exception)})))


;(def r (forecast {:lat 55.75396 :lon 37.620393 :uid "0001"}))
;(:body @(:resp r))
;(filter-body (:body @(:resp r)))
;(def t (filter-body (:body @(:resp r))))
;@(:resp r)
;(update {:h1 (get-in t [:forecast :h1]) :h2 (get-in t [:forecast :h2])} :h1 weather-vals)
;(process-one r)


;(api-url)
;(identity default-params)

;GET https://api.weather.yandex.ru/v1/forecast?lat=55.75396&lon=37.620393&lang=en_US&limit=3&extra=true&hours=true

;(def r (http/get (api-url) default-params))
;
;(identity r)
;
;(def res @r)

;(:status res)
;(:body res)
;(:error res)
;
;(defn process []
;  (let [r @(http/get (api-url) default-params)]
;    (identity r)))
