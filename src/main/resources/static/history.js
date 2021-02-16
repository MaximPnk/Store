app.controller('historyController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:8189/';

    $scope.formatDate = function(date){
        return new Date(date);
    };

    $scope.fillHistoryForCustomer = function () {
        $http.get(contextPath + 'api/v1/order/curr')
            .then(function (response) {
                console.log(response.data);
                $scope.history = response.data;
        });
    }

    $scope.fillHistoryForAdmin = function () {
        $http.get(contextPath + 'api/v1/order/')
            .then(function (response) {
                $scope.history = response.data;
            });
    }

    if ($rootScope.isAdmin) {
        $scope.fillHistoryForAdmin()
    } else if ($rootScope.isCustomer) {
        $scope.fillHistoryForCustomer();
    }

    $scope.getOrderInfo = function(id) {
        window.location = contextPath + '#!/order/' + id;
    }

});