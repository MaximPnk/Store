app.controller('cartController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:8189/';

    $scope.fillCart = function() {
        $http.get(contextPath + 'api/v1/cart/')
            .then(function (response) {
                $scope.items = response.data;
            });
    };

    $scope.fillCart();

    $scope.inc = function (id) {
        $http.get(contextPath + "api/v1/cart/inc/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.dec = function (id) {
        $http.get(contextPath + "api/v1/cart/dec/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.rm = function (id) {
        $http.get(contextPath + "api/v1/cart/rm/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.clearCart = function() {
        $http.get(contextPath + "api/v1/cart/clear")
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.makeOrder = function () {
        $http.post(contextPath + 'api/v1/order/')
            .then(function successCallback() {
                window.location = contextPath + '#!/shop'
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    }

});