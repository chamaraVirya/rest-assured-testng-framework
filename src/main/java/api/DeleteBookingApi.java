package api;

import http.BaseApi;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static constants.ApiPath.*;

public class DeleteBookingApi extends BaseApi {

    public DeleteBookingApi() {
        super();
        super.logAllRequestData().logAllResponseData();
    }

    public Response deleteBookingById(int bookingId, String userName, String password){
        super.setBasePath(DELETE_BOOKING.getApiPath());
        super.setPathParam("bookingId", bookingId);
        super.setBasicAuth(userName, password);
        return super.sendRequest(DELETE_BOOKING.getHttpMethodType());
    }

}
