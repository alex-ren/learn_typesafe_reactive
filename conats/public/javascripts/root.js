

angular.module("root", ['ui.bootstrap'])  // 
.controller("index", ["$scope", function($scope) {
    $scope.favoriteWord;
    $scope.favoriteColor;
    $scope.favoriteShape;
    $scope.value;
    $scope.typeCheck = function () {
    	if (window.console) {
    	  console.log("root.typecheck!");
    	}
    }
}]);



    
