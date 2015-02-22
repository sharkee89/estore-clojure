(ns estore.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to estore"]
     (include-css "/css/screen.css")
     [:script {:src "http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.2.min.js"}]
     [:script {:src "/js/app.js"}]]
    [:body body]))
