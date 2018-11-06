(ns pik-weather-loader.core
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount])
  (:gen-class))



(defn -main [& args]
  (log/info "PIK Weather Loader")
  (mount/start))
