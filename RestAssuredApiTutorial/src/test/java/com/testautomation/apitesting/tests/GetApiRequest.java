package com.testautomation.apitesting.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetApiRequest {
	
	@Test
	public void getAllBooking() {
		
		Response response =
		RestAssured.given()
		.contentType(ContentType.JSON)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
		.get()
		.then()
		.assertThat()
		.statusCode(200)
		.statusLine("HTTP/1.1 200 OK")
		.extract()
		.response();
		
		Assert.assertTrue(response.getBody().asString().contains("bookingid"));
		Assert.assertNotNull(response.jsonPath().get("bookingid"));
		
		
	}
	
	@Test
	public void getAllBooking_invalidmethod() {
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
		.post()
		.then()
		.assertThat()
		.statusCode(500);
		//should be 405 
	}
	
	@Test
	public void getAllBooking_invalidurl() {
		RestAssured.given()
		.contentType(ContentType.JSON)
		.baseUri("https://restful-booker.heroku.com/booking")
		.when()
		.get()
		.then()
		.assertThat()
		.statusCode(404);
	}

}
