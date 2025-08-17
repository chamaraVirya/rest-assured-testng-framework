import api.BaseTest;
import api.CreateBookingApi;
import api.DeleteBookingApi;
import api.UpdateBookingApi;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;
import utils.TestDataHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;

public class UpdateBookingApiTests extends BaseTest {

        @Test(description = "Update a new booking and validate the http code",
                dataProvider = "Booking Data with streams")
        public void updateAndValidateStatusCode(String firstName, String lastName, Boolean depositPaid,
                                                String additionalNeeds, Long totalPrice, String checkInDate,
                                                String checkOutDate){
            var updateBookingApi = new UpdateBookingApi();
            var deleteBookingApi = new DeleteBookingApi();

            var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest(firstName, lastName,
                    Math.toIntExact(totalPrice), depositPaid, additionalNeeds, checkInDate,checkOutDate);
            var createBookingApi = new CreateBookingApi();
            var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                                                                .then().assertThat().statusCode(200)
                                                                .and().body("bookingId", is (not(equalTo(0))));

            var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

            createBookingPayload.replace("lastname", this.faker.name().lastName());
            createBookingPayload.replace("totalprice", this.faker.number().randomNumber(3, true));
            createBookingPayload.replace("depositpaid", this.faker.bool().bool());

            var userName = "admin";
            var password = "password123";
            var updateBookingApiResponse = updateBookingApi.updateNewBooking(createBookingPayload, bookingId,
                                                       userName, password)
        .then().assertThat().statusCode(200)
        .and().body("bookingid", is(not(equalTo(0))));

            var deleteBookingApiResponse = deleteBookingApi.deleteBookingById(bookingId, userName, password)
                                                                .then().assertThat().statusCode(201);
        }


}