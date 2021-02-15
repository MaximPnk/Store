app.controller('registrationController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/';

    $scope.registerUser = function () {
        if ($scope.newUser.password !== $scope.pwConfirm) {
            $scope.pwMatch = true;
        } else {
            $http.post(contextPath + 'registration/', $scope.newUser)
                .then(function successCallback() {
                    $scope.registered = true;
                }, function errorCallback(response) {
                    if (response.data.message) {
                        alert(response.data.message);
                    } else if (response.data.messages) {
                        for (let e in response.data.messages) {
                            alert(response.data.messages[e]);
                        }
                    }
                });
        }
    }

});