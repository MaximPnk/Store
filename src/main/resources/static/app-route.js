var app = angular.module('app', ['ngRoute', 'ngStorage'])
    .config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/home', {
                templateUrl: 'home.html',
                controller: 'homeController'
            })
            .when('/cart', {
                templateUrl: 'cart.html',
                controller: 'cartController'
            })
            .when('/history', {
                templateUrl: 'history.html',
                controller: 'historyController'
            })
            .when('/product-form', {
                templateUrl: 'product-form.html',
                controller: 'productFormController'
            })
            .when('/registration', {
                templateUrl: 'registration.html',
                controller: 'registrationController'
            })
            .when('/shop', {
                templateUrl: 'shop.html',
                controller: 'shopController'
            })
            .when('/user', {
                templateUrl: 'user.html',
                controller: 'userController'
            })
            .when('/order/:id', {
                templateUrl: 'order.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/home'
        });
    });