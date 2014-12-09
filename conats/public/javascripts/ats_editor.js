var editor = ace.edit("atseditor");
editor.setTheme("ace/theme/monokai");
editor.getSession().setMode("ace/mode/javascript");
    
var in_atsfile = document.getElementById("atsfile");

in_atsfile.onclick = function() {
	this.value = null;
};

in_atsfile.onchange = function() {
	alert(this.value);
};


