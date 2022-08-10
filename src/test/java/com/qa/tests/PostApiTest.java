package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
//import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostApiTest extends TestBase {
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
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient= new RestClient();
		HashMap <String, String> headerMap = new HashMap<String , String>();
		headerMap.put("Content-Type", "application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		Users users= new Users("morpheus" , "leader");
		
		String filePath1 = "C:\\Users\\TISHAN1KA\\eclipse-workspace\\restapi\\src\\main\\java\\com\\qa\\data\\users.json";
		
		//System.out.println(filePath1);
		
		//This is marshalling ==> java to Json
		//Object to json File
		mapper.writeValue(new File(filePath1), users);
		
		//Object to json String
		String userJsonString = mapper.writeValueAsString(users);
		//System.out.println("userJsonString = " + userJsonString);
		closeableHttpResponse = restClient.postUser(url, userJsonString, headerMap);
		
		
		//get Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals( statusCode , testBase.Response_Code_201);
		
		//Json String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("responseString = " + responseString);
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("responseJson = " + responseJson);
		
		//This is unmarshalling ==>json to java 
		//json to java object
		Users userResObj = mapper.readValue(responseString, Users.class); //actual user object
		System.out.println(userResObj);
		
		Assert.assertTrue(users.getName().equals(userResObj.getName()));
		Assert.assertTrue(users.getJob().equals(userResObj.getJob()) );
		System.out.println(userResObj.getId());
		System.out.println(userResObj.getCreatedAt());
	}
	
}
