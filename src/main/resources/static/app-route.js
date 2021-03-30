var app = angular.module('app', ['ngRoute', 'ngStorage'])
    .config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/home', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/history', {
                templateUrl: 'history/history.html',
                controller: 'historyController'
            })
            .when('/product-form', {
                templateUrl: 'product-form/product-form.html',
                controller: 'productFormController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/shop', {
                templateUrl: 'shop/shop.html',
                controller: 'shopController'
            })
            .when('/user', {
                templateUrl: 'user/user.html',
                controller: 'userController'
            })
            .when('/order/:id', {
                templateUrl: 'order/order.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/home'
        });
    });