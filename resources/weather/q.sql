-- :name projects
-- :doc Get Projects
SELECT uid, name, lat, lon
FROM projects WHERE live = 1
