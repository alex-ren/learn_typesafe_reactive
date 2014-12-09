
if (window.console) {
  console.log("root.js111!");
}

angular.module("root", [])
.controller("index", ["$scope", function($scope) {
    $scope.favoriteWord;
    $scope.favoriteColor;
    $scope.favoriteShape;
}]);

if (window.console) {
  console.log("root.js222!");
}


    
