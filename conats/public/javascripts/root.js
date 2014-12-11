

angular.module("root", ['ui.bootstrap'])  // 
.controller("index", ["$scope", "$http", function($scope, $http) {
    $scope.favoriteWord;
    $scope.favoriteColor;
    $scope.favoriteShape;
    $scope.value;
    $scope.typeCheck = function () {
    	if (window.console) {
    	  console.log("root.typecheck!");
    	}
    	$http.post('/ats/typecheck', {code: "ee"}).
    	  success(function(data, status, headers, config) {
    		  console.log("data is " + data);
    	  }).
    	  error(function(data, status, headers, config) {
    		  console.log("error");
    	  });
    };
}]);



    
