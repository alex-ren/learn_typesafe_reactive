
if (window.console) {
  console.log("root.js111!");
}

angular.module("root", [])
    .controller("index", ["$scope", function($scope) {
        $scope.message = "Hello World";
    }]);

if (window.console) {
  console.log("root.js222!");
}


    
