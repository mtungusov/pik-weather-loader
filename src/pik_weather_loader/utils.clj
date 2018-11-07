(ns pik-weather-loader.utils
  (:require [jsonista.core :as j]))


(defn to-json [v]
  (j/write-value-as-string v))


(defn from-json [v]
  (j/read-value v (j/object-mapper {:decode-key-fn true})))


;(to-json {:key "one" :desc "some"})
;(from-json "{\"key\":\"one\",\"desc\":\"some\"}")
;(to-json "some str")
;(from-json "\"some str\"")

