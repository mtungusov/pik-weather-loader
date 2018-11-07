-- :name disable-projects! :! :n
-- :doc Disable all projects
UPDATE projects SET
  live = 0


-- :name project! :! :n
-- :doc Insert or Update project
UPDATE projects SET
  uid = :uid, name = :name, lat = :lat, lon = :lon, live = 1
WHERE uid = :uid
IF @@rowcount = 0
  BEGIN
    INSERT INTO projects (uid, name, lat, lon, live)
    VALUES (:uid, :name, :lat, :lon, 1)
  END


-- :name weather! :! :n
-- :doc Insert weather data from Yandex
INSERT INTO weather_history (uid, obs_time, temp, condition, wind_speed, wind_gust, wind_dir, wind_pik, wind_pik_at, forecast)
VALUES (:uid, :obs_time, :temp, :condition, :wind_speed, :wind_gust, :wind_dir, :wind_pik, :wind_pik_at, :forecast)
