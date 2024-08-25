package rest;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Jsonplaceholder_part2 {

	// Base URI for the API
	private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

	// Test case for GET request to fetch all posts
	@Test(priority = 0)
	public void getPost() {
		RestAssured.baseURI = BASE_URI;

		// Create a request specification
		RequestSpecification given = RestAssured.given();

		// Send GET request to fetch all posts
		Response getAllPosts = given.request(Method.GET, "posts");

		// Print and validate the response
		System.out.println("getAllPosts Response: " + getAllPosts.asPrettyString());
		System.out.println("getAllPosts Status Code: " + getAllPosts.getStatusCode());

		// Assert status code
		Assert.assertEquals(getAllPosts.getStatusCode(), 200, "Status code is not 200");

		// Assert response contains expected data
		Assert.assertTrue(getAllPosts.getBody().asString().contains("userId"),
				"Response body does not contain 'userId'");
	}

	// Test case for POST request to create a new post
	@Test(priority = 1)
	public void postRequest() {
		RestAssured.baseURI = BASE_URI;
		String requestBody = "{\"title\": \"Hi\", \"body\": \"World\", \"userId\": 1}";

		// Create a request specification
		RequestSpecification given = RestAssured.given().header("Content-Type", "application/json").body(requestBody);

		// Send POST request to create a new post
		Response postUser = given.request(Method.POST, "/posts");

		// Print and validate the response
		System.out.println("PostRequest Response: " + postUser.asPrettyString());
		System.out.println("PostRequest Status Code: " + postUser.getStatusCode());

		// Assert status code
		Assert.assertEquals(postUser.getStatusCode(), 201, "Status code is not 201 (Created)");

		// Assert response contains expected data
		Assert.assertTrue(postUser.getBody().asString().contains("id"), "Response body does not contain 'id'");
	}

	// Test case for PUT request to update an existing post
	@Test(priority = 2)
	public void putRequest() {
		RestAssured.baseURI = BASE_URI;
		String requestBody = "{\"title\": \"Hi\", \"body\": \"Everyone\", \"userId\": 1}";

		// Create a request specification
		RequestSpecification given = RestAssured.given().header("Content-Type", "application/json").body(requestBody);

		// Send PUT request to update the post with ID 1
		Response putUser = given.request(Method.PUT, "/posts/1");

		// Print and validate the response
		System.out.println("PutRequest Response: " + putUser.asPrettyString());
		System.out.println("PutRequest Status Code: " + putUser.getStatusCode());

		// Assert status code
		Assert.assertEquals(putUser.getStatusCode(), 200, "Status code is not 200");

		// Assert response contains expected data
		Assert.assertTrue(putUser.getBody().asString().contains("title"), "Response body does not contain 'title'");
	}

	// Test case for DELETE request to delete an existing post
	@Test(priority = 3)
	public void deleteRequest() {
		RestAssured.baseURI = BASE_URI;

		// Create a request specification
		RequestSpecification given = RestAssured.given();

		// Send DELETE request to delete the post with ID 1
		Response deleteUser = given.request(Method.DELETE, "/posts/1");

		// Print and validate the response
		System.out.println("DeleteRequest Response: " + deleteUser.asPrettyString());
		System.out.println("DeleteRequest Status Code: " + deleteUser.getStatusCode());

		// Assert status code
		Assert.assertEquals(deleteUser.getStatusCode(), 200, "Status code is not 200");

		// Optionally check if the response body is empty or contains a confirmation
		// message
		Assert.assertTrue(deleteUser.getBody().asString().isEmpty(), "Response body is not empty");
	}

	// Negative test case for POST request with invalid data
	// Note: The JSONPlaceholder API will accept any data provided in the request
	// body, which may lead to assertion failures if the response does not match the
	// expected format or content. Therefore, some assertions in this method might
	// fail depending on the actual response from the API.
	@Test(priority = 4)
	public void negativePostRequest() {
		RestAssured.baseURI = BASE_URI;
		// Invalid request body (e.g., missing required fields or invalid format)
		String invalidRequestBody = "{\"invalidField\": \"value\"}";

		// Create a request specification
		RequestSpecification given = RestAssured.given().header("Content-Type", "application/json")
				.body(invalidRequestBody);

		// Send POST request with invalid data
		Response response = given.request(Method.POST, "/posts");

		// Print and validate the response for error
		System.out.println("NegativePostRequest Response: " + response.asPrettyString());
		System.out.println("NegativePostRequest Status Code: " + response.getStatusCode());

		// Assert status code for bad request (or any other expected error code)
		Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400 (Bad Request)");

		// Assert response contains an error message or indication
		Assert.assertTrue(response.getBody().asString().contains("error"), "Response body does not contain 'error'");
	}
}
