app.controller('authController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:8189/';

    $scope.tryToAuth = function() {
        $http.post(contextPath + 'auth/', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.shopToken = response.data.token;

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $rootScope.checkAuth();
                    window.location = contextPath + '#!/shop';
                }
            }, function errorCallback(response) {
                window.alert(response.data.message);
            });
    }

});