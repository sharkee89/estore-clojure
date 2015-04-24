(ns estore.routes.home
  (:require [compojure.core :refer :all]
            [estore.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [postal.core :refer [send-message]])
  (use estore.models.db))

(def email "stefannikolic989@gmail.com")
(def pass "lespaul1989")

(def conn {:host "smtp.gmail.com"
           :ssl true
           :user email
           :pass pass})


(defn home [& [category_id name user pass]]
  (layout/common
     (layout/slider)
     
     
	  ))

(defn category-page [category_id]
  (layout/common

    [:div.row
    [:div.col-md-3]
    [:div.col-md-9
    [:div.row  
     
    (for [{:keys [productid name price description]}
       (list-products-for-category category_id)]
		  [:div {:class "col-md-4 catproduct"}
       [:p {:class "product-id-cat" :style "display:none"} productid]
		   [:a {:href (str "/product/" productid) :class "name-cat"} name]
       [:div[:p "Quantity:"][:input {:type "text" :id "quantitycat" :class "form-control"}]]
       [:div[:p "Price:"][:p.price-cat (str price)]]
       [:div.cat-picture[:a {:href (str "/product/" productid)} [:img {:src (str "/img/" name ".png") :width "95px" :height "95px"}]]
       [:a {:href "#" :class "add-to-cart-cat btn btn-success" :type "button"} "Add to cart"]]
		  ])]]
    ]
    
    ))

(defn product-page [product-id &[quantity]]
  (layout/common
    
    
    
    
    
    
    (for [{:keys [productid name price description]}
       (get-product product-id)]
      
      [:div.row
      [:div.col-md-6
       [:img {:src (str "/img/" name ".png") :width "395px" :height "395px"}]
       ]
      [:div.col-md-6
      [:ul
       [:p {:class "product-id" :style "display:none"} productid]
       [:h2 {:class "name"} name]
       [:p {:class "price"} price]
       [:input {:id "quantity" :type "text" :name quantity}]
       [:p description]
       [:a {:class "btn btn-success add-to-cart" :href "#"} "Add to cart"]
       ]
     ]]
    )
    
    
    [:h1 (str "User: " (session/get :user))]
    [:h1 (str "Basket Default: " (session/get :basket))]
    
    ))

(defn login-page [&[user pass ruser rpass reppass email]]
(layout/common
	[:div.form-login(form-to [:post "/login"]
          [:h3 "Sign in"]
          [:p "Username:"]
                          [:input {:type "text" :id "user" :name "user" :required "required" :class "form-control" :placeholder "Enter your username"}]
          [:p "Password:"]
                          [:input {:type "password" :id "pass" :name "pass" :required "required" :class "form-control" :placeholder "Enter your password"}]
          [:br]
          [:input {:type "submit" :value "Login" :class "btn btn-success"}])]
  [:div.form-register(form-to [:post "/register"]
          [:h3 "Register"]
          [:p "Username:"]
          [:input {:type "text" :id "ruser" :name "ruser" :required "required" :class "form-control" :placeholder "Enter username you want to use"}]
          [:p "Password:"]
          [:input {:type "password" :id "rpass" :name "rpass" :required "required" :class "form-control" :placeholder "Enter password you want to use"}]
          [:p "Repeat password:"]
          [:input {:type "password" :id "reppass" :name "reppass" :required "required" :class "form-control" :placeholder "Repeat your password"}]
          [:p "Email:"]
           [:input {:type "text" :id "email" :name "email" :required "required" :class "form-control" :placeholder "Enter email you want to use"}]
          [:br]
          [:input {:type "submit" :value "Register" :class "btn btn-success"}])]
 
 ))

(defn shipping-address [& [address city]]
  (layout/common
    [:h3 "Input your shipping information"]
    [:script " $(document).ready(function(){
                $( '.select-address' ).on( 'change', function() {
                var adrID = $('select[name=address-selector]').val();
                if(adrID > 0){
                 $.ajax({
								 url: '/get-address-ajax',
								 type: 'POST',
								 dataType: 'html',
								 data: {adrId: adrID}
								})
								.done(function(data) {
                 $('#address').val($(data).find('.adr_street').text());
                 $('#city').val($(data).find('.adr_city').text());
                 $('#state').val($(data).find('.adr_state').text());
                 $('#zipcode').val($(data).find('.adr_zipcode').text());
                 $('#phone').val($(data).find('.adr_phone').text());
								 console.log('success');
								})
								.fail(function() {
								 console.log('error');
								})
								.always(function() {
								 console.log('complete');
								});
                }else{
                $('#address').val('');
                 $('#city').val('');
                 $('#state').val('');
                 $('#zipcode').val('');
                 $('#phone').val('');
                }
                });
          })"
     ]
    (form-to [:post "/save-order"]
          [:div.form-group
          [:label "Select an address:"]
          
          [:select {:id "s" :name "address-selector" :class "form-control select-address"}
           [:option {:value 0} "Select an address"]
           (for [{:keys [address_id street name city state zipcode phone]}
			       (get-addresses (get (session/get :user) :id))]
			      [:option {:value address_id} name]
					  )
         ]
            
        ]
        [:div.form-group
          [:label "Address:"]
          
          [:input {:type "text" :id "address" :name "address" :class "form-control"}]]
          [:div.form-group
          [:label "City:"]
          [:input {:type "text" :id "city" :name "city" :class "form-control"}]]
          [:div.form-group
          [:label "State:"]
          [:input {:type "text" :id "state" :name "state" :class "form-control"}]]
          [:div.form-group
          [:label "Zipcode:"]
          [:input {:type "text" :id "zipcode" :name "zipcode" :class "form-control"}]]
          [:div.form-group
          [:label "Phone:"]
          [:input {:type "text" :id "phone" :name "phone" :class "form-control"}]]
          [:br]
          [:input {:type "submit" :value "Make an order" :class "btn btn-success"}])
    
    [:h1 (str "User: " (session/get :user))]
    [:h1 (str "Basket Default: " (session/get :basket))]
    
    )
  )

(defn not-found []
  (layout/common
  [:h1 "not found bro!"]
  )
  )

(defn cart-page[]
  (layout/common
    [:h1 "Your cart"]
    (if (nil? (session/get :basket)) 
      
      [:h4 "Your cart is empty"]
      
      [:div.row
                       [:div.col-md-9
                        (for [{:keys [id name price quantity overallprice]}
       (session/get :basket)]                          
                          [:div
                          [:h3 "Product"] 
                          [:div.col-md-2
                           [:a {:href (str "/product/" id)} [:img {:src (str "/img/" name ".png") :width "95px" :height "95px"}]]
                           ]
        [:div.col-md-10 [:p (str "Name: " name)]
                          [:p (str "Price: " price)]
                          [:p (str "Quantity: "quantity)]
                          [:p (str "Total: "overallprice)]]
                          ]
		 )
                        ]
                       [:div.col-md-3]
      (if (nil? (session/get :user))
       [:a {:href "/login" :class "btn btn-success"} "Sign in"]
       [:a {:href "/shipping-address" :class "btn btn-success"} "Checkout"] 
        )
                        
                       ]
      )
    
    )
  )

(defn profile-page[id]
  (layout/common
    (if(nil? (session/get :user))
      [:div[:p "You have to login first to see your orders"]
      [:a {:href "/login" class "btn btn-success"} "Sign in"]]
      [:ul
     [:li [:a {:href (str "/orders/" id)} "Orders"]]
     [:li [:a {:href (str "/adresses/" id)} "Addresses"]]
     ]
      )
    
    )
  )

(defn orders-page[id]
  (layout/common
    (if(nil? (session/get :user))
      [:div[:p "You have to login first to see your orders"]
      [:a {:href "/login" :class "btn btn-success"} "Sign in"]]
    (for [{:keys [order_id shipping_address shipping_city]}
       (get-orders id)]
		  
       [:div
        [:table
         [:tr {:class "row-1-orders"}
          [:td "Order"]
          ]
         [:tr {:class "row-2-orders"}
          [:td (str "Address: " shipping_address)]
          ]
         [:tr {:class "row-3-orders"}
          [:td (str "City: "shipping_city)]
          ]
         [:tr {:class "row-4-orders"}
          [:td "Items"]
          ]
         [:tr {:class "row-5-orders"}
          [:td]
          [:td "Name"]
          [:td "Quantity"]
          [:td "Price"]
          ]
         
          (for [{:keys [product_id quantity]}
                (get-order-items order_id)]
            [:tr {:class "row-6-orders"}
             
             [:td [:a {:href (str "/product/" product_id)} [:img {:src (str "/img/" (apply str (for [{:keys [name]}(get-product-name product_id)] name)) ".png") :width "95px" :height "95px"}]]]
             [:td (for [{:keys [name]}
                (get-product-name product_id)] name)]
             [:td quantity]
             [:td (for [{:keys [price]}
                (get-product-price product_id)] price)]
             ]
            )
          
         [:hr]
         ]
        ]
		  ))
    )
  )

(defn address-page[id]
  (layout/common
    (if(nil? (session/get :user))
      [:div[:p "You have to login first to see your addresses"]
      [:a {:href "/login" class "btn btn-success"} "Sign in"]]
    (for [{:keys [street name city state zipcode phone]}
       (get-addresses id)]
      [:div[:ul "Address"
            [:li (str "Street: " street)]
            [:li (str "Name of address: " name)]
            [:li (str "City: " city)]
            [:li (str "State: " state)]
            [:li (str "Zipcode: " zipcode)]
            [:li (str "Phone: " phone)]
            ]
       
       ]
		  ))
    (if(nil? (session/get :user))
      [:div]
    [:a {:href (str "/newaddress/" id) :class "btn btn-success"} "Create new address"]
    )
    )
  )

(defn get-address-ajax[adrId]
  (layout/common  
  (for [{:keys [street name city state zipcode phone]}
       (get-address-ajax-query adrId)]
      [:div
       [:p.adr_street (str street)]
       [:p.adr_name (str name)]
       [:p.adr_city (str city)]
       [:p.adr_state (str state)]
       [:p.adr_zipcode (str zipcode)]
       [:p.adr_phone (str phone)]
       ]
		  )
  )
  )
(defn new-address [id]
  (layout/common
    (if(nil? (session/get :user))
      [:div[:p "You have to login first to see your orders"]
      [:a {:href "/login" :class "btn btn-success"} "Sign in"]]
    [:div.create-address-form(form-to [:post "/save-address"]
          [:h1 "Create address"]
          [:input {:type "hidden" :id "id" :name "id" :value id}]
                                   [:div.form-group
          [:label "Name:"]
          [:input {:type "text" :id "name" :name "name" :class "form-control" :required "required"}]]
          [:div.form-group
          [:label "Street:"]
          
          [:input {:type "text" :id "street" :name "street" :class "form-control" :required "required"}]]
          [:div.form-group
          [:label "City:"]
          [:input {:type "text" :id "city" :name "city" :class "form-control" :required "required"}]]
          [:div.form-group
          [:label "State:"]
          [:input {:type "text" :id "state" :name "state" :class "form-control" :required "required"}]]
          [:div.form-group
          [:label "Zipcode:"]
          [:input {:type "text" :id "zipcode" :name "zipcode" :class "form-control" :required "required"}]]
          [:div.form-group
          [:label "Phone:"]
          [:input {:type "text" :id "phone" :name "phone" :class "form-control" :required "required"}]]
          [:br]
          [:input {:type "submit" :value "Create an address" :class "btn btn-success"}])])
    )
  )


(defroutes home-routes
 (GET "/login" [] (login-page))
 (POST "/login" [user pass](def user-id (get-user-id user pass))(def user-username (get-username user pass))(def uid (get (read-string (apply str user-id)) :id))(def uname (get (read-string (apply str user-username)) :username))
       (session/put! :user {:id uid :username uname})(def condition (login-user user pass))(if(true? condition)(not-found)(home)))             
 (POST "/register" [ruser rpass email](register ruser rpass email)(home))
 (GET "/logout" [] (session/remove! :user)(login-page))
 (GET "/" [] (home))
 (GET "/category/:category_id" [category_id] (session/put! :category category_id)(category-page category_id))
 (GET "/product/:product-id" [product-id] (product-page product-id))
 (GET "/addToCart/:productid/:name/:quantity/:price/:overallprice/:pagename" [productid name quantity price overallprice pagename] (def b (if (empty? (session/get :basket))(vector (hash-map :id productid, :name name, :quantity quantity, :price price, :overallprice overallprice))(conj (session/get :basket) (hash-map :id productid :name name :quantity quantity :price price :overallprice overallprice))))(session/put! :basket b)(def condition (= pagename (str "category")))(if(true? condition)(category-page (session/get :category))(product-page productid)))
 (GET "/shipping-address" [] (shipping-address))
 (POST "/save-order" [address city state zipcode phone](def orderid (get-order-id))(save-order orderid (get (session/get :user) :id) "2015-08-08" address city state zipcode phone)(doseq [keyval (session/get :basket)](save-order-item orderid (get keyval :id)(get keyval :quantity)))(send-message conn {:from email
                    :to (get (read-string (apply str (get-email (get (session/get :user) :id)))) :email)
                    :subject (str "Order " orderid " complete")
                    :body (str "Hi " (get (session/get :user) :username) 
                               "\n" "\n"
                               "Your order has been successfully completed"
                               "\n" "\n"                                                             
                               "Order number: " orderid
                               "\n" "\n"                               
                               (apply str (for [{:keys [order_id shipping_address shipping_city]}
													       (get-order orderid)]
															  
													       (str 
													         "Shipping address: "shipping_address
                                   "\n" "\n"  
													         "Shipping city: "shipping_city
                                   "\n""\n"
                                   "--------------------------------------------------------------------------------------"
                                   "\n""\n"
                                   
													          (apply str(for [{:keys [product_id quantity]}
													                (get-order-items orderid)]
													            (str                                        
													             "Product:"
													             (apply str(for [{:keys [name]}
													                (get-product-name product_id)] name))
                                       "\n""\n"
													             (str "Quantity: " quantity)
                                       "\n""\n"
                                       "Price: "
													             (apply str (for [{:keys [price]}
													                (get-product-price product_id)] price))
                                       "$"
                                       "\n""\n"
                                       "--------------------------------------------------------------------------------------"
                                       "\n""\n"
                                       
													             )
													            )
                              )
													          
													        "Total price: " (get (read-string (apply str (session/get :basket))) :overallprice) 
													         
													        )
															  ))
                               "\n"
                               "\n"
                               "The products you ordered will be shipped to your address very soon"
                               "\n"
                               "Thank you for your loyalty"
                               )})(home))
 (GET "/cart" [] (cart-page))
(GET "/profile/:id" [id] (profile-page id))
(GET "/orders/:id" [id] (orders-page id))
(GET "/adresses/:id" [id] (address-page id))
(POST "/get-address-ajax" [adrId] (get-address-ajax adrId))
(GET "/newaddress/:id" [id] (new-address[id]))
(POST "/save-address" [id name street city state zipcode phone](def id1 (read-string id))(def idForMethod (get id1 0))(save-address-query idForMethod name street city state zipcode phone) (address-page idForMethod))
 )