angular.module('app', ['ngStorage']).controller('a1authController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/';

    $scope.tryToAuth = function() {
        $http.post(contextPath + 'auth/', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.shopToken = response.data.token;

                    $scope.user.username = null;
                    $scope.user.password = null;
                    window.location = contextPath + 'a1index.html';
                }
            }, function errorCallback(response) {
                window.alert("Error");
            });
    }

});