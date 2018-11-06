-- :name projects :? :*
select distinct Projects.UID as uid, RTRIM(LTRIM(Projects.Name)) as name, Projects.Latitude as lat, Projects.Longitude as lon
from Projects
where Projects.TypeProject = 'Объект СМР'
  and Projects.Latitude not in (0.000000, 1.000000)
  and Projects.Longitude not in (0.000000, 1.000000)
