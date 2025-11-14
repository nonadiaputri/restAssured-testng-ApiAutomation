package com.testautomation.apitesting.tests;

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
import net.minidev.json.JSONArray;

public class PostApiRequestUsingFile extends BaseTest {

	@Test
	public void postApiRequest() {
		
		try {
			String bodyreq = FileUtils.readFileToString(new File(FileNameConstant.POST_API_REQ_BODY),"UTF-8");
			
			
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
			
			JSONArray jsonArray = JsonPath.read(response.body().asString(),"$.booking..firstname");
			System.out.println(jsonArray.get(0));
			
			JSONArray jsonArrayLastname = JsonPath.read(response.body().asString(),"$.booking..lastname");
			System.out.println(jsonArrayLastname.get(0));
			
			JSONArray jsonArrayCheckin = JsonPath.read(response.body().asString(),"$.booking.bookingdates..checkin");
			System.out.println(jsonArrayCheckin.get(0));
			
			String firstname = (String) jsonArray.get(0);
			Assert.assertEquals(firstname, "Bruno");
			
			String lastname = (String) jsonArrayLastname.get(0);
			Assert.assertEquals(lastname, "Kenedy");
			
			String checkin = (String) jsonArrayCheckin.get(0);
			Assert.assertEquals(checkin, "2025-01-01");
			
			int id = JsonPath.read(response.body().asString(),"$.bookingid");
			
			RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking/"+id)
			.when()
			.get()
			//.get("/{bookingId}", id)
			.then()
			.assertThat()
			.statusCode(500);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
