(ns estore.models.db
(:require [clojure.java.jdbc :as sql])
(:import java.sql.DriverManager))

(def db {:subprotocol "mysql" 
              :subname "//localhost:3306/estore"
              :user "root"
              :password ""})

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

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

(defn login-user[username password]
  (def query (str "SELECT `id` FROM `user` WHERE `username` = '" username "' AND `password` = '" password "'"))
  (def score (sql/query db [query]))
  (empty? score)
 )





(defn save-checkout [iduser date address city]
	(sql/db-do-prepared db "INSERT INTO `order`(`user_id`, `date`, `shipping_address`, `shipping_city`) VALUES (?,?,?,?)" [iduser date address city])
 )