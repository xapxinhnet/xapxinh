package net.xapxinh.center.server.api.net;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public final class URIEncoder {
	
	private URIEncoder() {
		// prevent installation
	}
	
	public static String encodeURIComponent(String dir) throws ScriptException {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		return engine.eval("encodeURIComponent('" + dir + "')").toString();
	}
}
