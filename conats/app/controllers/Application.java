package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.mvc.*;

import utils.Utils;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result typecheck() {
		JsonNode json = request().body().asJson();
		String code = json.findPath("code").textValue();
		if (code == null) {
			return badRequest("Missing parameter [name]");
		} else {
			Logger.info("code is " + code);
			return ok(Utils.typeCheck(code));
		}

	}

}
