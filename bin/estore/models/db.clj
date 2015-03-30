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

(defn get-order-id[]
  (def query (str "SELECT MAX(`order_id`) FROM `order`"))
  (def order-id-string (str "STRING: " (apply str (sql/query db [query]))))
  (def order-id-int (parse-int order-id-string))
  (def order-id (+ order-id-int 1))
  (if (number? order-id) order-id 0)
  )

(defn save-order [idorder iduser date address city state zipcode phone]              
	(sql/db-do-prepared db "INSERT INTO `order`(`order_id`,`user_id`, `date`, `shipping_address`, `shipping_city`, `shipping_state`, `shipping_zipcode`, `shipping_phone`) VALUES (?,?,?,?,?,?,?,?)" [idorder iduser date address city state zipcode phone])
 )

(defn save-order-item[ordid proid quantity]
  (sql/db-do-prepared db "INSERT INTO `orderitem`(`order_id`, `product_id`, `quantity`) VALUES (?,?,?)" [ordid proid quantity])
 )

(defn get-user-id[username password]
    (def query (str "SELECT `id` FROM `user` WHERE `username` = '" username "' AND `password` = '" password "'"))
    (sql/query db [query])
  )

(defn get-username[username password]
    (def query (str "SELECT `username` FROM `user` WHERE `username` = '" username "' AND `password` = '" password "'"))
    (sql/query db [query])
  )

(defn register[username password email]
  (sql/db-do-prepared db "INSERT INTO `user`(`username`, `password`, `email`) VALUES (?,?,?)" [username password email])
  )

(defn get-category-from-product[product-id]
  
  (def query (str "SELECT `cateogoryid` FROM `product` WHERE `productid` = " product-id))
    (sql/query db [query])
  )

(defn get-orders [userid]
  (def query (str "SELECT * FROM `order` WHERE user_id = " userid))
  (sql/query db [query])
  )

(defn get-order-items[orderid]
  (def query (str "SELECT * FROM `orderitem` WHERE `order_id` = " orderid))
  (sql/query db [query])
  )

(defn get-product-name[productid]
  
  (def query (str "SELECT `name` FROM `product` WHERE `productid` = " productid))
  (sql/query db [query])
  )

(defn get-product-price[productid]
  
  (def query (str "SELECT `price` FROM `product` WHERE `productid` = " productid))
  (sql/query db [query])
  )


(defn get-addresses [userid]
  (def query (str "SELECT * FROM `address` WHERE user_id = " userid))
  (sql/query db [query])
  )

(defn get-address-ajax-query [adrid]
  (def query (str "SELECT * FROM `address` WHERE `address_id` = " adrid))
  (println (sql/query db [query]))
  (sql/query db [query])
  )

(defn save-address-query [id name address city state zipcode phone]              
	(sql/db-do-prepared db "INSERT INTO `address`(`user_id`, `name`, `street`, `city`, `state`, `zipcode`, `phone`) VALUES (?,?,?,?,?,?,?)" [id name address city state zipcode phone])
 )

