(ns estore.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [noir.session :as session])
  (use estore.models.db))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to estore"]
     (include-css "/css/bootstrap.min.css")
     (include-css "/css/full-slider.css")
     (include-css "/css/screen.css")
     [:script {:src "/js/jquery.js"}]
     [:script {:src "/js/bootstrap.min.js"}]
     [:script {:src "/js/app.js"}]
     ]
    [:body 
     [:nav {:class "navbar navbar-inverse navbar-static-top" :role "navigation"}
      [:div.container
       [:div.navbar-header
        [:button {:type "button" :class "navbar-toggle" :data-toggle "collapse" :data-target "#bs-example-navbar-collapse-1"}
         [:span.sr-only "Toggle navigation"]
         [:span.icon-bar]
         [:span.icon-bar]
         [:span.icon-bar]
         ]
        [:a {:class "navbar-brand" :href "/"} "Start Bootstrap"]
        ]
       [:div {:class "collapse navbar-collapse" :id "bs-example-navbar-collapse-1"}
       
        [:ul {:class "nav navbar-nav"}  
		    (for [{:keys [category_id name]}
		       (list-categories)]
				  [:li
				   [:a {:href (str "/category/" category_id)} name]
				  ])
        ]
        [:ul {:class "nav navbar-nav"}
         (if (nil? (session/get :user))  
          [:li[:a {:href "/login"} "Sign in"]]
	        [:li[:a {:href "/logout"} "Sign out"]]
           )
         
          [:li [:a {:href "/cart" :class "view-cart"} "View cart"]]
         [:li [:a {:href (str "/profile/" (get (session/get :user) :id))}(get (session/get :user) :username)]]
        ]
        ]
       ]
      ]
     body
     ]))

(defn slider []
  [:header {:id "myCarousel" :class "carousel slide"}
   
   [:ol.carousel-indicators
    [:li {:data-target "#myCarousel" :data-slide-to "0" :class "active"}]
    [:li {:data-target "#myCarousel" :data-slide-to "1"}]
    [:li {:data-target "#myCarousel" :data-slide-to "2"}]
    ]
   
   [:div.carousel-inner
    [:div {:class "item active"}
     [:div {:class "fill" :style "background-image:url('/img/slider-image1.jpg');"}]
     [:div.carousel-caption
      [:h2 "Caption 1"]
      ]
     ]
    [:div.item
     [:div {:class "fill" :style "background-image:url('/img/slider-image2.jpg');"}]
     [:div.carousel-caption
      [:h2 "Caption 1"]
      ]
     ]
    [:div.item
     [:div {:class "fill" :style "background-image:url('/img/slider-image3.jpg');"}]
     [:div.carousel-caption
      [:h2 "Caption 1"]
      ]
     ]
    ]
   
   [:a {:class "left carousel-control" :href "#myCarousel" :data-slide "prev"}
    [:span.icon-prev]
    ]
   
   [:a {:class "right carousel-control" :href "#myCarousel" :data-slide "next"}
    [:span.icon-next]
    ]
   
   ]
  )

