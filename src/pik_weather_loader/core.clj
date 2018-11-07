(ns pik-weather-loader.core
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount]
            [pik-weather-loader.loader :as loader])
  (:gen-class))



(defn -main [& args]
  (log/info "PIK Weather Loader")
  (mount/start))
  ;(loader/update-projects)
  ;(log/info "Update Projects")
  ;(process))
