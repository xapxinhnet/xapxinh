package net.xapxinh.center.server.api.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

public interface HttpApi {

	public String toHttpParammeters(Map<String, String> params) throws ScriptException;
	
	public String sendHttpGETRequest(String url) throws IOException;

	public int getResponseCodeHttpGETRequest(String url) throws IOException;

	public String sendHttpPOSTRequest(String url, HashMap<String, String> paramMap) throws IOException ;

	public String getHttpBaseUrl(String host, int port);
}
