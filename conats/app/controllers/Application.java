package controllers;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.*;
import play.libs.Json;
import play.mvc.*;

import utils.Utils;
import utils.Utils.ModelCheckResult;
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
			return badRequest("Missing parameter [code]");
		} else {
//			Logger.info("code is " + code);
			return ok(Utils.typeCheck(code));
		}

	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result genmodel() {
		JsonNode json = request().body().asJson();
		String code = json.findPath("code").textValue();
		if (code == null) {
			return badRequest("Missing parameter [code]");
		} else {
//			Logger.info("code is " + code);
			String model;
			try {
				model = Utils.genModel(code);
			} catch (IOException e) {
				Logger.error(e.toString());
				return internalServerError("Error in generating model.");
			} catch (InterruptedException e) {
				Logger.error(e.toString());
				return internalServerError("Error in generating model.");
			}
			
			return ok(model);
		}

	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result modelcheck() {
		JsonNode json = request().body().asJson();
		String code = json.findPath("code").textValue();
		int engine = json.findPath("engine").asInt();
		
		if (null == code) {
			return badRequest("Missing parameter [code]");
		} else {
//			Logger.info("model check, code is " + code);
			ModelCheckResult res = Utils.modelCheck(code, engine);
			
			ObjectNode result = Json.newObject();
			result.put("errno", res.m_errno);
			result.put("msg", res.m_msg);
			result.put("result", res.m_res);
			try {
				Logger.info("begin sleep");
				Thread.sleep(1000);
				Logger.info("end sleep");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ok(result);
		}

	}

}


