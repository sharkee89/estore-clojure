(ns estore.routes.home
  (:require [compojure.core :refer :all]
            [estore.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session])
  (use estore.models.db))

(defn home [& [category_id name]]
  (layout/common [:h1 "Hello Estore!"]
	    [:ul  
    (for [{:keys [category_id name]}
       (list-categories)]
		  [:li
		   [:a {:href (str "/category/" category_id)} name]
		  ])]
     [:a {:href "/login"} "Sign in"]
     [:a {:href "/logout"} "Sign out"]
     [:h1 (str "User: " (session/get :user))]
     
     
     [:h1 (str "Basket Default: " (session/get :basket))]
     
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
    [:h1 (str "Basket: " (get (session/get :basket) :secondProduct))]
    
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

(defn login-page [&[user pass]]
(layout/common
	(form-to [:post "/login"]
          [:p "Username:"]
          (text-field "user" user)
          [:p "Password:"]
          (text-field "pass" pass)
          [:br]
          (submit-button "Login"))))

(defroutes home-routes
  (GET "/login" [] (login-page))
  (POST "/login" [user pass]
  (session/put! :user user)(home))
  (GET "/logout" [] 
  (session/clear!)(home))
  (GET "/" [] (if (empty? (session/get :basket)) (session/put! :basket {}))(home))
  (GET "/category/:category_id" [category_id] (category-page category_id))
  (GET "/product/:product-id" [product-id] (product-page product-id))
  (GET "/addToCart/:productid/:name/:quantity/:price/:overallprice" [productid name quantity price overallprice] (println (str "NAME IS " name)) (def b (array-map (session/get :basket) {:id productid :name name :quantity quantity :price price :overallprice overallprice})) (session/put! :basket b) (println (str "BBB" b)) (println (session/get :basket)) (home))
 )
