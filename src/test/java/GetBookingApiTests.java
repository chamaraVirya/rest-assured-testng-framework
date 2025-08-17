import api.GetBookingApi;
import listeners.RetryAnalyzer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GetBookingApiTests {

    @Parameters("testParam")
    @Test(description = "Basic http status check for get booking ids API")
    public void validateStatusCodeForGetBookingApi(@Optional String testParam){
        System.out.println("Test param is: " + testParam);
        var getBookingIdsResponse = new GetBookingApi().getAllBookingIds()
                .then().assertThat().statusCode(200);
    }

    @Test(description = "Basic http status check for get booking by ID API")
    public void validateStatusCodeForGetBookingByIdApi(){
        var getBookingByIdApiResponse = new GetBookingApi().getBookingById(20)
                                                      .then().assertThat().statusCode(200);
    }
}
