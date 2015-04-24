# estore

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Import project to eclipse as a Leiningen project

Database is in root of the project called estore.sql, import it into mysql

Initial settings for database are:
    
    - :subprotocol "mysql" 
    - :subname "//localhost:3306/estore"
    - :user "root"
    - :password ""
    
Database contains some initial data so you can immediately start with application

Default user is:

    - username: stefan
    - password: nikolic

To start a web server for the application, run:

    lein ring server
    
## Description

This is a demo application for ecommerce store. Some of core functionalities are 

    - looking for products in categories
    - looking for product in product detail page
    - adding products to basket
    - creating addresses for your user
    - checkout ( enter your address details or choose from address )
    - make an order

## Hints

    If you want for making order functionalities not to brake you must disable antivirus, because your computer will also be a server so it can send order email after successful purchase


## License

Copyright © 2015
