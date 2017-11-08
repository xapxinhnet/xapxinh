package net.xapxinh.center.server.api.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sun.net.www.protocol.http.HttpURLConnection;

public class HttpApiImpl implements HttpApi {

	static Logger logger = Logger.getLogger(HttpApiImpl.class.getName());

	public String sendHttpGETRequest(String url) throws IOException  {
		// logger.info("URL: " + url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		// int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print result
		// logger.info("Response: " + response);
		return response.toString();
	}

	public Document sendHttpGETRequestDocument(String uri, String username, String password) throws IOException, ParserConfigurationException, SAXException   {

			URL obj = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			String userpass = username + ":" + password;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			con.setRequestProperty("Authorization", basicAuth);

			DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			Document document = objDocumentBuilder.parse(con.getInputStream());
			return document;
		
	}

	/*
	 * public Document sendHttpGETRequestDocumen(String uri) throws Exception {
	 * URL obj = new URL(uri); HttpURLConnection con = (HttpURLConnection)
	 * obj.openConnection();
	 * 
	 * // optional default is GET con.setRequestMethod("GET"); //add request
	 * header con.setRequestProperty("User-Agent", "Mozilla/5.0"); int
	 * responseCode = con.getResponseCode();
	 * 
	 * logger.info("Response code: " + responseCode);
	 * 
	 * DocumentBuilderFactory objDocumentBuilderFactory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder objDocumentBuilder
	 * = objDocumentBuilderFactory.newDocumentBuilder(); Document document =
	 * objDocumentBuilder.parse(con.getInputStream()); return document; }
	 */

	public int getResponseCodeHttpGETRequest(String url) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		return con.getResponseCode(); // 200; 404...
	}

	@SuppressWarnings({ "rawtypes" })
	public String sendHttpPOSTRequest(String url, HashMap<String, String> paramMap) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "";
		int paramCount = 0;
		for (Map.Entry entry : paramMap.entrySet()) {
			if (paramCount == 0) {
				urlParameters = urlParameters + (String) entry.getKey() + "=" + (String) entry.getValue();
			}
			else {
				urlParameters = urlParameters + "&" + (String) entry.getKey() + "=" + (String) entry.getValue();
			}
		}

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	@Override
	public String toHttpParammeters(Map<String, String> params) throws ScriptException {
		String parametters = "";
		if (params == null || params.isEmpty()) {
			return "";
		}
		int i = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			if (i == 0) {
				parametters = parametters + "?";
			}
			else {
				parametters = parametters + "&";
			}
			parametters = parametters + entry.getKey() + "=" + URIEncoder.encodeURIComponent(entry.getValue());
			i++;
		}
		return parametters;
	}

	@Override
	public String getHttpBaseUrl(String host, int port) {
		if (80 == port) {
			return "http://" + host;
		}
		return "http://" + host + ":" + port;
	}
}
