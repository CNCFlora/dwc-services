(ns dwc-rest.core-test
  (:use dwc-rest.core
        midje.sweet))

(fact "It converts formats"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\"\n\"Vicia faba\""
        json "[{\"scientificName\":\"Vicia faba\"}]"]
    (convert {:from :csv :to :json :source (str-to-file csv)}) => json
    ))

(fact "Coordinates to decimal"
  (coord2decimal "38 53 55N") => 38.89861297607422
  (coord2decimal "38d 53 55 N") => 38.89861297607422
  (coord2decimal "38d 53' 55'' S") => -38.89861297607422
  (coord2decimal "77 2 16W") => -77.03778076171875)

(fact "It can fix coords systems"
  (fix-coords {:latitude "38d 53 55N" :longitude "77 2' 16'' W"})
      => {:latitude "38d 53 55N" :longitude "77 2' 16'' W" 
          :decimalLatitude 38.89861297607422 :decimalLongitude -77.03778076171875}
  (fix-coords {:latitude "38d 53 55N"})
      => {:latitude "38d 53 55N" }
  (fix-coords {:decimalLatitude 38.89861297607422 :decimalLongitude -77.03778076171875})
      => {:decimalLatitude 38.89861297607422 :decimalLongitude -77.03778076171875})

(fact "Fixes occurrenceID"
  (fix-id {:occurrenceID "123" :id "321"})
    => {:occurrenceID "123" :id "321"}
  (fix-id {:id "321"})
    => {:occurrenceID "321" :id "321"}
  (fix-id {:institutionCode "inst" :collectionCode "col" :catalogNumber "num"} )
    => {:institutionCode "inst" :collectionCode "col" :catalogNumber "num" :occurrenceID "urn:occurrence:inst:col:num"}
          )

(fact "It converts formats"
  (let [occs [{:scientificName "Vicia faba"}]
        csv  "\"scientificName\",\"id\"\n\"Vicia faba\",\"123\""
        json "[{\"occurrenceID\":\"123\",\"id\":\"123\",\"scientificName\":\"Vicia faba\"}]"]
    (convert {:fixes true :from :csv :to :json :source (str-to-file csv)}) => json
    ))
