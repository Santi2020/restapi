package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

//import junit.framework.Assert;

public class GetAPITest extends TestBase{
	TestBase testBase ;
	String mainUrl ;
	String apiUrl ;
	String url ;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		System.out.println("GetAPITest ==> @BeforeMethod ==> setUp");
		testBase = new TestBase();	
		mainUrl = prop.getProperty("url");
		apiUrl =prop.getProperty("serviceurl");
		url = mainUrl + apiUrl;
	}
	
	@Test(priority =1)
	public void getTest() throws ClientProtocolException, IOException {
		System.out.println("GetAPITest ==> getTest ");
		restClient = new RestClient();
		System.out.println("url = " + url );
		closeableHttpResponse = restClient.get(url);
		
		//Get Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("httpResponse.getStatusLine() = " + closeableHttpResponse.getStatusLine());
		System.out.println("ststusCode = " + Response_Code_200);
		
		Assert.assertEquals(statusCode, 200, "Asert code is not 200");
		
		//Get Entity and store it in response String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8" );
		System.out.println("responseString = " + responseString);
		
		//Convert Responsestring to Json
		JSONObject responseJson =  new JSONObject(responseString);
		System.out.println("responseJson = " + responseJson);
		
		String perPageValue = TestUtil.getValueByJPath(responseJson ,"/per_page");
		System.out.println("perPageValue = " + perPageValue);
		
		Assert.assertEquals(perPageValue, "6");
		//Assert.assertEquals(perPageValue, 6, "Per page value is incorrect.");
		
		String totalValue = TestUtil.getValueByJPath(responseJson ,"/total");
		System.out.println("totalValue = " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue) , 12);
		
		//Fetching value from array
		
		String lastName = TestUtil.getValueByJPath(responseJson ,"data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson ,"data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson ,"data[0]/avatar");
		String first_name = TestUtil.getValueByJPath(responseJson ,"data[0]/first_name");
		
		
		System.out.println("lastName , id ,avatar , first_name= " + lastName + " , " + id + " , " + avatar + " , " + first_name);
		Assert.assertEquals("Bluth",lastName);
		
		
		
		//Get Response all header
		
		Header[] headersArray= closeableHttpResponse.getAllHeaders();
		
		// Convert array to haspMap
		HashMap <String, String> allHeaders = new HashMap<String , String>();
		
		for(Header h : headersArray )
		{
			allHeaders.put(h.getName(), h.getValue());
		}
		System.out.println("allHeaders = " + allHeaders);
		
	}

	
	
	
	@Test(priority =2)
	public void getTestWithHeaders() throws ClientProtocolException, IOException {
		System.out.println("GetAPITest ==> getTestWithHeaders ");
		
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Connection", "keep-alive");
		// Add necessary headers
		//headerMap.put("username", "test");
		//headerMap.put("password", "test123");
		
		
		//headerMap.put("Auth-token", "123456");
		restClient = new RestClient();
		System.out.println("url = " + url );
		closeableHttpResponse = restClient.get(url, headerMap);
		
		//Get Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("httpResponse.getStatusLine() = " + closeableHttpResponse.getStatusLine());
		System.out.println("ststusCode = " + Response_Code_200);
		
		Assert.assertEquals(statusCode, 200, "Asert code is not 200");
		
		//Get Entity and store it in response String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8" );
		System.out.println("responseString = " + responseString);
		
		//Convert Responsestring to Json
		JSONObject responseJson =  new JSONObject(responseString);
		System.out.println("responseJson = " + responseJson);
		
		String perPageValue = TestUtil.getValueByJPath(responseJson ,"/per_page");
		System.out.println("perPageValue = " + perPageValue);
		
		Assert.assertEquals(perPageValue, "6");
		//Assert.assertEquals(perPageValue, 6, "Per page value is incorrect.");
		
		String totalValue = TestUtil.getValueByJPath(responseJson ,"/total");
		System.out.println("totalValue = " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue) , 12);
		
		//Fetching value from array
		
		String lastName = TestUtil.getValueByJPath(responseJson ,"data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson ,"data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson ,"data[0]/avatar");
		String first_name = TestUtil.getValueByJPath(responseJson ,"data[0]/first_name");
		
		
		System.out.println("lastName , id ,avatar , first_name= " + lastName + " , " + id + " , " + avatar + " , " + first_name);
		Assert.assertEquals("Bluth",lastName);
		
		
		
		//Get Response all header
		
		Header[] headersArray= closeableHttpResponse.getAllHeaders();
		
		// Convert array to haspMap
		HashMap <String, String> allHeaders = new HashMap<String , String>();
		
		for(Header h : headersArray )
		{
			allHeaders.put(h.getName(), h.getValue());
		}
		System.out.println("allHeaders = " + allHeaders);
		
	}

}
