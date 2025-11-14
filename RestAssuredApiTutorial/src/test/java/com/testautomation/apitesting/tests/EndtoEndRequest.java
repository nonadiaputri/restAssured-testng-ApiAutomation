package com.testautomation.apitesting.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.FileNameConstant;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EndtoEndRequest extends BaseTest {
	
	@Test
	public void e2eRequest() {
		
		try {
			//post api call
			String bodyreq = FileUtils.readFileToString(new File(FileNameConstant.POST_API_REQ_BODY),"UTF-8");
			
			String tokenreq = FileUtils.readFileToString(new File(FileNameConstant.TOKEN_REQUEST), "UTF-8");
			
			String putreq = FileUtils.readFileToString(new File(FileNameConstant.PUT_REQUEST), "UTF-8");
			//System.out.println(putreq);
			
			Response response =
			RestAssured.given()
			.contentType(ContentType.JSON)
			.body(bodyreq)
			.baseUri("https://restful-booker.herokuapp.com/booking")
			.when()
			.post()
			.then()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();		
			
			int id = JsonPath.read(response.body().asString(),"$.bookingid");
			
			//get api call
			RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking/"+id)
			.when()
			.get()
			//.get("/{bookingId}", id)
			.then()
			.assertThat()
			.statusCode(200);
			
			//get token api
			Response token_response = 
			RestAssured.given()
			.contentType(ContentType.JSON)
			.body(tokenreq)
			.baseUri("https://restful-booker.herokuapp.com/auth")
			.when()
			.post()
			.then()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();
			
			String token = JsonPath.read(token_response.body().asString(), "$.token");
			
			//System.out.println(token);
			
			//put api 
			Response put_response = 
			RestAssured.given()
			.contentType(ContentType.JSON)
			.header("Cookie", "token="+token)
			.body(putreq)
			.baseUri("https://restful-booker.herokuapp.com/booking/"+id)
			.when()
			.put()
			.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();
			
			
			
			//from put body request
			String firstname = new io.restassured.path.json.JsonPath(putreq).getString("firstname");
			int tprice = new io.restassured.path.json.JsonPath(putreq).getInt("totalprice");
			
			AssertJUnit.assertEquals(firstname, put_response.path("firstname"));
			AssertJUnit.assertEquals(tprice, put_response.path("totalprice"));
			
			//delete api call
			
			RestAssured.given()
			.contentType(ContentType.JSON)
			.header("Cookie", "token="+token)
			.baseUri("https://restful-booker.herokuapp.com/booking/"+id)
			.when()
			.delete()
			.then()
			.assertThat()
			.statusCode(201);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
