app.controller('indexController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:8855/';

    $rootScope.decodeToken = function(token) {
        let jwtData = token.split('.')[1];
        let decodedJwtJsonData = window.atob(jwtData);
        return JSON.parse(decodedJwtJsonData);
    }

    $rootScope.addUserData = function(token) {
        $rootScope.authorized = true;
        $rootScope.username = token.sub;
        $rootScope.isAdmin = token.roles.includes("ROLE_ADMIN");
        $rootScope.isCustomer = token.roles.includes("ROLE_CUSTOMER");
    }

    $rootScope.getCart = function () {
        console.log("get cart");
        if (!$localStorage.shopCartUUID) {
            $http.post(contextPath + 'api/v1/cart/')
                .then(function successCallback(response) {
                    $localStorage.shopCartUUID = response.data;
                    window.location = contextPath + '#!/home'
                })
        }
    }

    $rootScope.logout = function () {
        delete $localStorage.shopCartUUID;
        $rootScope.clearUserData();
    }

    $rootScope.clearUserData = function() {
        delete $localStorage.shopToken;
        $rootScope.authorized = false;
        $rootScope.username = null;
        $rootScope.isAdmin = false;
        $rootScope.isCustomer = false;
        $rootScope.getCart();
    }

    $rootScope.checkAuth = function () {
        console.log("check auth");
        if ($localStorage.shopToken) {
            $rootScope.decodedShopToken = $rootScope.decodeToken($localStorage.shopToken);
            if ($rootScope.decodedShopToken.exp <= (new Date().getTime() / 1000)) {
                $rootScope.clearUserData();
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.shopToken;
                $rootScope.addUserData($rootScope.decodedShopToken);
            }
        } else {
            $rootScope.clearUserData();
        }
    }

    $scope.doesCartExists = function () {
        $http.get(contextPath + 'api/v1/cart/exists/' + $localStorage.shopCartUUID)
            .then(function(response) {
                if (!response.data) {
                    $http.post(contextPath + 'api/v1/cart/')
                        .then(function successCallback(innerResponse) {
                            $localStorage.shopCartUUID = innerResponse.data;
                        })
                }
            });
    }

    $rootScope.checkAuth();

    $scope.doesCartExists();

    $scope.tryToAuth = function() {
        $scope.user.cartId = $localStorage.shopCartUUID;
        $http.post(contextPath + 'auth/', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.shopToken = response.data.token;

                    $scope.user.username = null;
                    $scope.user.password = null;
                    $localStorage.shopCartUUID = response.data.cartId;

                    $rootScope.checkAuth();
                    window.location = contextPath + '#!/home'
                }
            }, function errorCallback(response) {
                window.alert(response.data.message);
            });
    }

});