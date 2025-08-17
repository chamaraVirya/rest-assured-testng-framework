package utils;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestHelper {

    public static Map<String, Object> getCreateBookingApiRequest(String firstName, String lastName,
                                                                 int totalPrice, boolean depositPaid,
                                                                 String additionalNeeds,
                                                                 String checkInDate, String checkoutDate){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", firstName);
        requestBody.put("lastname", lastName);
        requestBody.put("totalprice", totalPrice);
        requestBody.put("depositpaid", depositPaid);
        requestBody.put("additionalneeds", additionalNeeds);


        Map<String, Object> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin", checkoutDate);
        bookingDatesMap.put("checkout", checkoutDate);

        requestBody.put("bookingdates", bookingDatesMap);

        return requestBody;
    }
}
