package api;

import http.BaseApi;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.CreateBookingRequest;

import java.util.Map;

import static constants.ApiPath.*;

public class UpdateBookingApi extends BaseApi {

    public UpdateBookingApi() {
        super();
        super.logAllSpecificRequestDetail(LogDetail.BODY).logAllResponseData();
        super.setContentType(ContentType.JSON);
    }

    public Response updateNewBooking(Map<String, Object> createBookingPayload, int bookingId,
                                     String userName, String password) {

        return getCreateBookingApiResponse(createBookingPayload, bookingId, userName, password);
    }

    public Response updateNewBooking(CreateBookingRequest createBookingRequest, int bookingId,
                                     String userName, String password) {
        return getCreateBookingApiResponse(createBookingRequest, bookingId, userName, password);

    }

    private Response getCreateBookingApiResponse(Object createBookingPayload, int bookingId,
                                                 String userName, String password) {
        super.setBasePath(UPDATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        super.setPathParam("bookingId", bookingId);
        super.setBasicAuth(userName, password);
        return super.sendRequest(UPDATE_BOOKING.getHttpMethodType());
    }



}
