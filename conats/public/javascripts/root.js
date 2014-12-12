

angular.module("root", ['ui.bootstrap'])  // 
.controller("index", ["$scope", "$http", function($scope, $http) {
    $scope.favoriteWord;
    $scope.favoriteColor;
    $scope.favoriteShape;
    $scope.value;
    $scope.typecheckres = "No Type Checking Result yet";
    $scope.typeCheck = function () {
    	if (window.console) {
    	  console.log("root.typecheck!");
    	}
    	var atscode = editor.getValue();  // todo: dependency injection
    	console.log("source is " + atscode);
    	$http.post('/ats/typecheck', {code: atscode}).
    	  success(function(data, status, headers, config) {
    		  console.log("data is " + data);
    		  $scope.typecheckres = data;
    	  }).
    	  error(function(data, status, headers, config) {
    		  console.log("error");
    	  });
    };
}]);



    
