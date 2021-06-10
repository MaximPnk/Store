app.controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8855/';

    $scope.fillCart = function() {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.shopCartUUID)
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.fillCart();

    $scope.rm = function (id) {
        $http({
            url: contextPath + "api/v1/cart/rm/",
            method: 'DELETE',
            params: {
                uuid: $localStorage.shopCartUUID,
                product_id: id
            }
        }).then(function () {
            $scope.fillCart();
        });
    };

    $scope.clearCart = function() {
        $http({
            url: contextPath + "api/v1/cart/clear/",
            method: 'DELETE',
            params: {
                uuid: $localStorage.shopCartUUID
            }
        }).then(function () {
            $scope.fillCart();
        });
    };

    $scope.inc = function (id) {
        $http({
            url: contextPath + "api/v1/cart/inc/",
            method: 'PUT',
            params: {
                uuid: $localStorage.shopCartUUID,
                product_id: id
            }
        }).then(function () {
            $scope.fillCart();
        });
    };

    $scope.dec = function (id) {
        $http({
            url: contextPath + "api/v1/cart/dec/",
            method: 'PUT',
            params: {
                uuid: $localStorage.shopCartUUID,
                product_id: id
            }
        }).then(function () {
            $scope.fillCart();
        });
    };

    $scope.makeOrder = function () {
        $http({
            url: contextPath + "api/v1/order/",
            method: 'POST',
            params: {
                uuid: $localStorage.shopCartUUID,
                address: $scope.address
            }
        }).then(function successCallback() {
            window.location = contextPath + '#!/shop'
        })
    }

    $scope.confirm = false;

    $scope.confirmOrder = function () {
        $scope.confirm = true;
    }

    $scope.cancelConfirmation = function () {
        $scope.confirm = false;
    }

});