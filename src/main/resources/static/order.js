app.controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/';

    $scope.formatDate = function(date){
        return new Date(date);
    };

    if (document.URL.match(".*order/[0-9]+$")) {
        $scope.id = document.URL.split("order/")[1];
        console.log($scope.id);
    }

    $scope.getOrderInfo = function (id) {
        console.log('in get', id);
        $http.get(contextPath + 'api/v1/order/' + id)
            .then(function successCallback(response) {
                console.log(response.data);
                $scope.orderInfo = response.data;
            }, function errorCallback(response) {
                window.alert(response.data.message);
            });

    }

    $scope.getOrderInfo($scope.id);

});