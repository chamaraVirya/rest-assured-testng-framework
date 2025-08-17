import api.GetBookingApi;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.time.Duration;

public class AwaitilityTests{

    @Test
    public void waitUntilAsserted() {
        var getBookingAPi = new GetBookingApi();

        Awaitility.await().and()
                  .with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .then().untilAsserted(() -> {
                      getBookingAPi.getBookingById(20)
                                   .then().assertThat().statusCode(200);

                      getBookingAPi.getBookingById(20)
                                   .then().assertThat().statusCode(400);

                  });

    }

    @Test
    public void waitUntil() {
        var getBookingAPi = new GetBookingApi();

        Awaitility.await().and()
                  .with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .then().until(() -> {
                      var statusCode = getBookingAPi.getBookingById(20).statusCode();
                      return statusCode == 400;

                  });

    }

    @Test
    public void waitUntilAndIgnoreAllExceptions() {
        var getBookingAPi = new GetBookingApi();

        Awaitility.await().and()
                  .with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                .and().ignoreExceptions()
                  .then().until(() -> {
                      getBookingAPi.getBookingById(20).then().assertThat().statusCode(400);
                      return true;

                  });

    }

    @Test
    public void waitUntilAndIgnoreSpecificException() {
        var getBookingAPi = new GetBookingApi();

        Awaitility.await().and()
                  .with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                .and().ignoreExceptionsInstanceOf(AssertionError.class)
                  .then().until(() -> {
                      getBookingAPi.getBookingById(20).then().assertThat().statusCode(400);
                      return true;

                  });

    }

    @Test
    public void definePollingDelay() {
        var getBookingAPi = new GetBookingApi();

        Awaitility.await().and()
                  .with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                .and().ignoreExceptionsInstanceOf(AssertionError.class)
                .and().pollDelay(Duration.ofMillis(1000))
                  .then().until(() -> {
                      getBookingAPi.getBookingById(20).then().assertThat().statusCode(400);
                      return true;

                  });

    }

}
