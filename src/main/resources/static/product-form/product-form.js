app.controller('productFormController', function ($scope, $http) {
    const contextPath = 'http://localhost:8855/';

    //TODO кидает кривую страницу неавторизованным

    if (document.URL.match(".*id=[0-9]+$")) {
        $scope.id = document.URL.split("id=")[1];
    }

    $scope.fillForm = function() {
        if ($scope.id != null) {
            $http.get(contextPath + 'api/v1/products/' + $scope.id)
                .then(function (response) {
                    $scope.productInForm = response.data;
                });
        }
    }

    $scope.fillForm();

    $scope.createOrUpdate = function () {
        if ($scope.id == null) {
            $http.post(contextPath + 'api/v1/products/', $scope.productInForm)
        } else {
            $http.put(contextPath + 'api/v1/products/', $scope.productInForm)
        }
        window.location = contextPath + '#!/shop'
    }

});