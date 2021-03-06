package controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.mvc.*;
import play.twirl.api.Html;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready1."));
    }
    
    public static Result hello() {
    	List<Integer> xs = new ArrayList<Integer>();
    	xs.add(1);
    	xs.add(3);
    	Html html = helloworld.render("ddd", xs);
    	return ok(html);
    }

    public static Result createObj() {
  	  JsonNode json = request().body().asJson();
  	  if(json == null) {
  	    return badRequest("Expecting Json data");
  	  } else {
  	    String name = json.findPath("name").textValue();
  	    if(name == null) {
  	      return badRequest("Missing parameter [name]");
  	    } else {
  	      return ok("Hello " + name);
  	    }
  	  }
  	}
}
