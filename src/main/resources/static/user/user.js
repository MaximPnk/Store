app.controller('userController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/';

    $scope.formatDate = function(date){
        return new Date(date);
    };

    $scope.fillUser = function () {
        $http.get(contextPath + 'api/v1/user/')
            .then(function (response) {
                $scope.u = response.data;
            });
    }

    $scope.fillUser();

});