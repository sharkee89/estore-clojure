(ns estore.routes.home
  (:require [compojure.core :refer :all]
            [estore.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session])
  (use estore.models.db))

(defn home [& [category_id name user pass]]
  (layout/common [:h1 "Hello Estore!"]
	    [:ul  
    (for [{:keys [category_id name]}
       (list-categories)]
		  [:li
		   [:a {:href (str "/category/" category_id)} name]
		  ])]
     [:a {:href "/login"} "Sign in"]
     [:a {:href "/logout"} "Sign out"]
     
     
     [:h1 (str "Basket Default: " (session/get :basket))]
     [:h1 (str "User: " (session/get :user))]
     [:a {:href "/shipping-address"} "Checkout"]1
     [:a {:href "/sss"} "Iterates"]
     
	  ))

(defn category-page [category_id]
  (layout/common
    [:ul  
    (for [{:keys [productid name]}
       (list-products-for-category category_id)]
		  [:li
		   [:a {:href (str "/product/" productid)} name]
		  ])]
    [:h1 (str "User: " (session/get :user))]
    [:h1 (str "Basket Default: " (session/get :basket))]
    
    ))

(defn product-page [product-id &[quantity]]
  (layout/common
    [:ul  
    (for [{:keys [productid name price description]}
       (get-product product-id)]
      
      
      [:ul
       [:li [:a {:href "#" :class "product-id"} productid]]
       [:li [:a {:href "#" :class "name"} name]]
       [:li [:a {:href "#" :class "price"} price]]
       [:input {:id "quantity" :type "text" :name quantity}]
       [:li [:a {:href "#"} description]
       ][:a {:class "add-to-cart" :href "#"} "Add to cart"]
       ]
    )]
    [:h1 (str "User: " (session/get :user))]
    [:h1 (str "Basket: " (get (session/get :basket) :name))]
    
    ))

(defn login-page [&[user pass ruser rpass reppass email]]
(layout/common
	(form-to [:post "/login"]
          [:h3 "Sign in"]
          [:p "Username:"]
          (text-field "user" user)
          [:p "Password:"]
          (text-field "pass" pass)
          [:br]
          (submit-button "Login"))
  (form-to [:post "/register"]
          [:h3 "Register"]
          [:p "Username:"]
          (text-field "ruser" ruser)
          [:p "Password:"]
          (text-field "rpass" rpass)
          [:p "Repeat password:"]
          (text-field "reppass" reppass)
          [:p "Email:"]
          (text-field "email" email)
          [:br]
          (submit-button "Register"))
 
 ))

(defn shipping-address [& [address city ]]
  (layout/common
    [:h3 "Input your shipping information"]
    (form-to [:post "/save-order"]
          [:p "Address:"]
          (text-field "address" address)
          [:p "City:"]
          (text-field "city" city)
          [:br]
          (submit-button "Make an order"))
    
    [:h1 (str "User: " (session/get :user))]
    [:h1 (str "Basket Default: " (session/get :basket))]
    
    )
  )

(defn not-found []
  (layout/common
  [:h1 "not found bro!"]
  )
  )

(defn sss []
  (layout/common
  (def mapa [{:name "Stefanija"}])
  (doseq [keyval mapa] (prn "PRODUCT:")(prn (get keyval :name)))
  (println "-----------------------------")
  )
  )


(defroutes home-routes
 (GET "/login" [] (login-page))
 (POST "/login" [user pass](def user-id (get-user-id user pass))(def user-username (get-username user pass))(def uid (get (read-string (apply str user-id)) :id))(def uname (get (read-string (apply str user-username)) :username))
       (session/put! :user {:id uid :username uname})(def condition (login-user user pass))(if(true? condition)(not-found)(home)))             
 (POST "/register" [ruser rpass email](register ruser rpass email)(home))
 (GET "/logout" [] (session/clear!)(home))
 (GET "/" [] (home))
 (GET "/category/:category_id" [category_id] (category-page category_id))
 (GET "/product/:product-id" [product-id] (product-page product-id))
 (GET "/addToCart/:productid/:name/:quantity/:price/:overallprice" [productid name quantity price overallprice] (def b (if (empty? (session/get :basket))(vector (hash-map :id productid, :name name, :quantity quantity, :price price, :overallprice overallprice))(conj (session/get :basket) (hash-map :id productid :name name :quantity quantity :price price :overallprice overallprice))))(session/put! :basket b)(home))
 (GET "/shipping-address" [] (shipping-address))
 (POST "/save-order" [address city](def orderid (get-order-id))(save-order orderid (get (session/get :user) :id) "2015-08-08" address city)(doseq [keyval (session/get :basket)](save-order-item orderid (get keyval :id)(get keyval :quantity)))(session/remove! :basket)(home))
 (GET "/sss" [] (sss))
 )
