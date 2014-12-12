var editor = ace.edit("atseditor");
editor.setTheme("ace/theme/monokai");
//editor.getSession().setMode("ace/mode/javascript");
    
var in_atsfile = document.getElementById("atsfile");

in_atsfile.onclick = function() {
	this.value = null;
};

in_atsfile.onchange = function() {
	console.log(this.value);
	var f = this.files[0];
	
	if (!f) {
		
//	} else if (!f.type.match('csp.*')) {
//	    alert(f.name + " is not a valid text file.");
	} else {
	    var r = new FileReader();
	    r.onload = function(e) { 
	        var contents = e.target.result;
	        editor.setValue(contents);
	    }
	    r.readAsText(f);
	} 

};


