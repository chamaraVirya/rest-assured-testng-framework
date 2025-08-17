import api.CreateBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;
import utils.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class CreateBookingApiTests {

    private CreateBookingApi createBookingApi;

    @BeforeClass
    public void init() {
        createBookingApi = new CreateBookingApi();
    }

    @DataProvider(name = "Booking Data with for loop")
    public Object[][] bookingDataWithLoop() {
        var faker = TestDataHelper.getFaker();
        var name = faker.name();
        var dateFormatter = DateTimeFormatter.ISO_DATE;
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Object[] objects = new Object[]{name.firstName(), name.lastName(),
                    faker.bool().bool(),
                    faker.food().dish(), faker.number().randomNumber(3, true),
                    TestDataHelper.getFutureDate(10, dateFormatter),
                    TestDataHelper.getFutureDate(14, dateFormatter)};
            list.add(objects);
        }
        return list.toArray(new Object[0][]);
    }

    @Test(description = "Create a new booking and validate the response")
    public void createValidateStatusCode() {
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest("Zach", "Newman",
                799, false, "Nothing Else", "2024-02-02", "2024-03-03");
        var createBookingApiResponse = this.createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is (not(equalTo(0))));
    }

}
