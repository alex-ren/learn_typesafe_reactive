
// Tell pace to track Post.
window.paceOptions = {
    ajax: {
        trackMethods: ["GET", "POST"],
        trackWebSockets: false
    },
    document: false,
    restartOnPushState: false
};
    
var atseditor = ace.edit("atseditor");
atseditor.setTheme("ace/theme/monokai");
atseditor.setValue("// Please write program here.\n");

var cspseditor = ace.edit("cspseditor");
cspseditor.setTheme("ace/theme/monokai");
cspseditor.setValue("// Please write model here.\n");

var ats_file_name;  // variable crossing the boundary of AngularJS

// =============================


angular.module("root", ['ui.bootstrap'])  // 
.controller("index", ["$scope", "$http", function($scope, $http) {
    $scope.favoriteWord;
    $scope.favoriteColor;
    $scope.favoriteShape;
    $scope.value;
    $scope.typecheckres = "No Type Checking Result yet";
    $scope.selectATS = function () {
        var fileInput = document.querySelector('#atsfile');
        fileInput.click();  // use the invisible fileInput to select the file
    };
    
    $scope.updateATSFile = function () {
    	$scope.ats_file = ats_file_name;
    }    
    
    $scope.typeCheck = function () {
    	if (window.console) {
    	  console.log("root.typecheck!");
    	}
    	var atscode = atseditor.getValue();
    	$http.post('/ats/typecheck', {code: atscode}).
    	  success(function(data, status, headers, config) {
    		  $scope.typecheckres = data;
    	  }).
    	  error(function(data, status, headers, config) {
    		  alert("Exception occurrs in type checking:\n" + data);
    	  });
    };
    



	// ============================
    
	var in_atsfile = document.getElementById("atsfile");
	//in_atsfile.onclick = function() {
	//	this.value = null;
	//};
	$scope.ats_file = "No file is selected.";
	
	in_atsfile.onchange = function() {
		
		var f = this.files[0];
		ats_file_name = f.name;
		var hackbt = document.getElementById("update_ats_file");  // use the button inspected by AngularJS
		hackbt.click();
		if (!f) {
			
	//	} else if (!f.type.match('csp.*')) {
	//	    alert(f.name + " is not a valid text file.");
		} else {
		    var r = new FileReader();
		    r.onload = function(e) { 
		        var contents = e.target.result;
		        atseditor.setValue(contents);
		    }
		    r.readAsText(f);
		} 
	
	};
	
	$scope.genModel = function() {
    	if (window.console) {
      	  console.log("root.genmodel!");
      	}
      	var atscode = atseditor.getValue();
      	$http.post('/ats/genmodel', {code: atscode}).
      	  success(function(data, status, headers, config) {
      		  cspseditor.setValue(data);
      	  }).
      	  error(function(data, status, headers, config) {
      		  alert("Exception occurrs in generating model:\n" + data);
      	  });
	}
	
	// ============================
	
	$scope.modelcheckmsg = "No model checking result yet.";
	$scope.modelcheckres = "no output yet";
	$scope.hasRes = false;
	$scope.isModelChecking = false;
	
    $scope.modelCheck = function () {
    	if (window.console) {
    	  console.log("root.modelcheck!");
    	}
    	

    	var atscode = cspseditor.getValue();
    	$scope.isModelChecking = true;
    	$http.post('/ats/modelcheck', {code: atscode}).
    	  success(function(data, status, headers, config) {
    		  $scope.isModelChecking = false;
//  			result.put("errno", res.m_errno);
//			result.put("msg", res.m_msg);
//			result.put("result", res.m_res);
    		  $scope.modelcheckmsg = data.msg;
    		  if (0 == data.errno) {
    			  console.log("errno is " + data.errno);
    			  $scope.hasRes = true;
    			  $scope.modelcheckres = data.result;
    		  } else {
    			  $scope.hasRes = false;
    			  $scope.modelcheckres = "You should not see me.";
    			  
    		  }
    		  
    	  }).
    	  error(function(data, status, headers, config) {
    		  $scope.isModelChecking = false;
    		  alert("Exception occurrs in model checking:\n" + data);
    	  });
    };
	
}]);

// =============================


    
