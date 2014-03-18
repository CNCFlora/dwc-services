(ns dwc-services.core
  (:use dwc.fixes)
  (:use dwc.validation)
  (:use dwc.csv)
  (:use dwc.xlsx)
  (:use dwc.json)
  (:use dwc.geojson)
  (:use dwc.archive))

(def reader
  {:csv read-csv
   :xlsx read-xlsx
   :json read-json
   :geojson read-geojson
   :dwca read-archive
   :archive read-archive})

(def writer
  {:json write-json
   :geojson write-geojson})

(defn str-to-file
  [data] 
   (let [tmp (java.io.File/createTempFile "dwc-" ".tmp")]
     (spit tmp data)
     (.getAbsolutePath tmp)))

(defn convert
  [{from :from to :to source :source fixes :fixes}]
   ((get writer (keyword to ))
    (let [data ((get reader (keyword from)) source)]
      (if-not fixes
       data
       (-fix-> data)))))

(defn fix
  [data]
   (-fix-> (read-json data)))

(defn validation
  [data] 
   (map validate (read-json data)))
