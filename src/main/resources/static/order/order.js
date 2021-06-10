app.controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:8855/';

    $scope.formatDate = function(date){
        return new Date(date);
    };

    if (document.URL.match(".*order/[0-9]+$")) {
        $scope.id = document.URL.split("order/")[1];
    }

    $scope.getOrderInfo = function (id) {
        $http.get(contextPath + 'api/v1/order/' + id)
            .then(function successCallback(response) {
                $scope.orderInfo = response.data;
            }, function errorCallback(response) {
                window.alert(response.data.message);
            });

    }

    $scope.getOrderInfo($scope.id);

});