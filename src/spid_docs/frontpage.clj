(ns spid-docs.frontpage
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [spid-docs.apis :as apis]
            [spid-docs.layout :as layout]))

(def cols 3)

(defn render-service-apis [num service]
  [:div {:class (if (= (inc num) cols) "lastUnit" "unit s1of3")}
   [:div.item
    [:h3 [:a {:href (apis/api-index-url service)} (:title service)]]
    [:p "APIs: " (str/join ", " (map #(apis/get-name (:id %)) (:apis service)))]]])

(defn create-page [apis]
  {:title "SPiD API Documentation"
   :body (list [:h1 "SPiD API Documentation"]
               (slurp (io/resource "frontpage.html"))
               [:h2 "API reference"]
               [:p "Looking for API details? Here you will find extensive reference documentation of all API endpoints."]
               (map #(vector :div.line (map-indexed render-service-apis %))
                    (partition cols cols [] apis)))})
