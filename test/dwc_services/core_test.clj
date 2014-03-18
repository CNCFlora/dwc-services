(ns dwc-services.core-test
  (:use dwc-services.core
        midje.sweet))

(fact "It converts formats"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\"\n\"Vicia faba\""
        json "[{\"scientificName\":\"Vicia faba\"}]"]
    (convert {:from :csv :to :json :source (str-to-file csv)}) => json
    ))


(fact "It converts formats with fixes"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\",\"id\"\n\"Vicia faba\",\"123\""
        json "[{\"occurrenceID\":\"123\",\"id\":\"123\",\"scientificName\":\"Vicia faba\"}]"]
    (convert {:fixes true :from :csv :to :json :source (str-to-file csv)}) => json
    ))