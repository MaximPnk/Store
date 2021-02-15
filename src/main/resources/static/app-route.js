var app = angular.module('app', ['ngRoute', 'ngStorage'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/auth', {
                templateUrl: 'auth.html',
                controller: 'authController'
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
            .otherwise({
                redirectTo: '/'
        });
    });