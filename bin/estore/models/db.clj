(ns estore.models.db
(:require [clojure.java.jdbc :as sql])
(:import java.sql.DriverManager))

(def db {:subprotocol "mysql" 
              :subname "//localhost:3306/estore"
              :user "root"
              :password ""})

(defn list-categories []
  (sql/query db ["SELECT * FROM category"])
  )

(defn list-products-for-category [category-id-for-list]
  (def query-list-shows (str "SELECT * FROM `product` WHERE cateogoryid = " category-id-for-list))
  (sql/query db [query-list-shows])
  )

(defn get-product [productid]
  (def query (str "SELECT * FROM `product` WHERE productid = " productid))
  (sql/query db [query])
  )