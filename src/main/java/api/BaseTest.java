package api;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;
import utils.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class BaseTest {

    protected final Faker faker = TestDataHelper.getFaker();

    @DataProvider(name = "Booking Data with streams", parallel = true)
    public Object[][] bookingDataWithStreams() {
        var faker = this.faker;
        var name = faker.name();
        var dateFormatter = DateTimeFormatter.ISO_DATE;
        return IntStream.range(0, 2)
                        .mapToObj(i -> {

                            var numberOfPlusDays = TestDataHelper.getRandomNumber(2);

                            return new Object[]{name.firstName(), name.lastName(),
                                    faker.bool().bool(),
                                    faker.food().dish(), faker.number().randomNumber(3, true),
                                    TestDataHelper.getFutureDate(numberOfPlusDays, dateFormatter),
                                    TestDataHelper.getFutureDate(numberOfPlusDays + 4, dateFormatter)
                            };
                        })
                        .toArray(Object[][]::new);
    }
}
