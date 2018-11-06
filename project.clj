(defproject pik-weather-loader "0.1.0-SNAPSHOT"
  :description "Load DATA from Yandex.Weather API"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jdmk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [cprop "0.1.13"]
                 [mount "0.1.14"]
                 [com.layerware/hugsql "0.4.9"]
                 [net.sourceforge.jtds/jtds "1.3.1"]
                 [org.clojure/java.jdbc "0.7.8"]]

  :main pik-weather-loader.core
  :profiles {:uberjar {:omit-source true
                       :aot :all
                       :uberjar-name "pik-weather-loader.jar"}})
