package com.testautomation.apitesting.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostApiRequestPojo {
	
	
	@Test
	public void postRequestApi() {
		try {
			//serelize
			BookingDates bd = new BookingDates("2024-12-01","2024-12-03");
			Booking booking = new Booking("Lia", "Tan", true, 500000, "car rental", bd);
			
			ObjectMapper obj = new ObjectMapper();
			String reqbody = obj.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
			
			//System.out.println(reqbody);
			
			//deserelize
			Booking bookingdetail = obj.readValue(reqbody, Booking.class);
			//System.out.println(bookingdetail.getFirstname());
			//System.out.println(bookingdetail.getAdditionalneed());
			
			Response res = 
			RestAssured.given()
			.contentType(ContentType.JSON)
			.body(reqbody)
			.baseUri("https://restful-booker.herokuapp.com/booking")
			.when()
			.post()
			.then()
			.assertThat()
			.statusCode(200)
			.extract()
			.response();
			
			System.out.println(res.path("booking.additionalneeds"));
			System.out.println(bookingdetail.getAdditionalneeds());
			Assert.assertEquals(res.path("booking.firstname"), bookingdetail.getFirstname());
			Assert.assertEquals(res.path("booking.lastname"), bookingdetail.getLastname());
			Assert.assertEquals(res.path("booking.additionalneeds"), bookingdetail.getAdditionalneeds());
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
