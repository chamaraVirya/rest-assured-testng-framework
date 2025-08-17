import api.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CRUDTests extends BaseTest {

    @Test(description = "CRUD Operation on RestFul booker API resource", dataProvider = "Booking Data with streams")
    public void crudTest(String firstName, String lastName, Boolean depositPaid,
                         String additionalNeeds, Long totalPrice, String checkInDate,
                         String checkOutDate){

        var updateBookingApi = new UpdateBookingApi();
        var deleteBookingApi = new DeleteBookingApi();
        var getBookingApi = new GetBookingApi();

        //Create a new booking - step 1
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest(firstName, lastName,
                Math.toIntExact(totalPrice), depositPaid, additionalNeeds, checkInDate,checkOutDate);
        var createBookingApi = new CreateBookingApi();
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                                                       .then().assertThat().statusCode(200)
                                                       .and().body("bookingId", is (not(equalTo(0))));

        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

        //Retrieve this created booking using booking ID
        var getBookingByIdApiResponse = getBookingApi.getBookingById(bookingId);
        this.validateRetrievedBookingDataFromGetApi(firstName, lastName, depositPaid,
                additionalNeeds, Math.toIntExact(totalPrice), checkInDate, checkOutDate, getBookingByIdApiResponse);

        //Update the booking using PUT api
        var updatedLastName = this.faker.name().lastName();
        var updatedTotalPrice = Math.toIntExact(this.faker.number().randomNumber(3, true));
        var updatedDepositPaid = this.faker.bool().bool();

        createBookingPayload.replace("lastname", updatedLastName);
        createBookingPayload.replace("totalprice", updatedTotalPrice);
        createBookingPayload.replace("depositpaid", updatedDepositPaid);

        var userName = "admin";
        var password = "password123";
        var updateBookingApiResponse = updateBookingApi.updateNewBooking(createBookingPayload, bookingId,
                                                               userName, password)
                                                       .then().assertThat().statusCode(200)
                .and().body("lastname", is(equalTo(updatedLastName)))
                .and().body("totalprice", is(equalTo(updatedTotalPrice)))
                .and().body("depositpaid", is(equalTo(updatedDepositPaid)));

        var deleteBookingApiResponse = deleteBookingApi.deleteBookingById(bookingId, userName, password)
                                                       .then().assertThat().statusCode(201);

        getBookingApi.getBookingById(bookingId).then().assertThat().statusCode(404);

    }

    private void validateRetrievedBookingDataFromGetApi(String firstName, String lastName, Boolean depositPaid, String additionalNeeds, Integer totalPrice, String checkInDate, String checkOutDate, Response getBookingByIdApiResponse) {
        getBookingByIdApiResponse
                                                          .then().assertThat().statusCode(200)
                                                             .and().body("firstname", is(equalTo(firstName)))
                                                             .and().body("lastname", is(equalTo(lastName)))
                                                             .and().body("totalprice", is(equalTo(totalPrice)))
                                                             .and().body("depositpaid", is(equalTo(depositPaid)))
                                                             .and().body("additionalneeds", is(equalTo(additionalNeeds)))
                                                             .and().rootPath("bookingdates")
                                                             .and().body("checkin", equalTo(checkInDate))
                                                             .and().body("checkout", equalTo(checkOutDate))
                                                             .and().detachRootPath("bookingdates");
    }


}
