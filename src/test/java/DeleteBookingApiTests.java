import api.CreateBookingApi;
import api.DeleteBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;

import static org.hamcrest.Matchers.*;

public class DeleteBookingApiTests {

    private DeleteBookingApi deleteBookingApi;

    @BeforeClass
    public void initApi(){
        this.deleteBookingApi = new DeleteBookingApi();
    }

    @Test(description = "Delete and existing booking")
    public void deleteBookingTest(){
        var userName = "admin";
        var password = "password123";

        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest("Zach", "Newman",
                799, false, "Nothing Else", "2024-02-02", "2024-03-03");
        var createBookingApi = new CreateBookingApi();
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                                                       .then().assertThat().statusCode(200)
                                                       .and().body("bookingId", is (not(equalTo(0))));

        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

        var deleteBookingApiResponse = this.deleteBookingApi.deleteBookingById(bookingId, userName, password)
                .then().assertThat().statusCode(201);
    }


}
