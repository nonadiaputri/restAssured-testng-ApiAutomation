package com.testautomation.apitesting.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

public class PostApiRequest extends BaseTest {
	
	@Test
	public void createBooking() {
		
		JSONObject booking = new JSONObject();
		JSONObject bookingDate = new JSONObject();
		
		booking.put("firstname", "john");
		booking.put("lastname", "doe");
		booking.put("totalprice", "200000");
		booking.put("depositpaid", "true");
		booking.put("additionalneeds", "dinner");
		booking.put("bookingdates", bookingDate);
		bookingDate.put("checkin", "2025-01-01");
		bookingDate.put("checkout", "2025-01-04");
		
		Response response = 
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(booking.toString())
		.baseUri("https://restful-booker.herokuapp.com/booking")
		//.log().all()
		.when()
		.post()
		.then()
		.assertThat()
		//.log().all()
		//.log().ifValidationFails()
		.statusCode(200)
		.body("booking.firstname", Matchers.equalTo("john"))
		.body("booking.totalprice", Matchers.equalTo(200000))
		.body("booking.bookingdates.checkin", Matchers.equalTo("2025-01-01"))
		.extract().response();
		
		int bookingId = response.path("bookingid");
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.pathParam("bookingid", bookingId)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
		.get("{bookingid}")
		.then()
		.assertThat()
		.statusCode(200)
		.body("firstname", Matchers.equalTo("john"))
		.body("lastname", Matchers.equalTo("doe"));
	
		
	}

}
