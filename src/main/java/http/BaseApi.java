package http;

import config.PropertyUtil;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {

    private final RequestSpecification requestSpecification;

    public BaseApi() {
        var httpConfig = HttpClientConfig.httpClientConfig()
                                         .setParam("http.connection.timeout",
                                                 PropertyUtil.getConfig().connectionTimeout())
                                         .setParam("http.socket.timeout",
                                                 PropertyUtil.getConfig().socketTimeout());
        this.requestSpecification = RestAssured.given()
                .baseUri(PropertyUtil.getConfig().baseUrl())
                .filter(new AllureRestAssured())
                .config(RestAssured.config().httpClient(httpConfig));

    }



    public BaseApi setRequestBody(Object object){
        this.requestSpecification.body(object);
        return this;
    }

    public void setBasePath(String basePath){
        this.requestSpecification.basePath(basePath);

    }

    public BaseApi setContentType(ContentType contentType){
        this.requestSpecification.contentType(contentType);
        return this;
    }

    public BaseApi setBasicAuth(String username, String password){
        this.requestSpecification.auth().preemptive().basic(username, password);
        return this;
    }

    public void setPathParam(String paramName, Object paramValue){
        this.requestSpecification.pathParam(paramName, paramValue);

    }

    public BaseApi logAllRequestData(){
        this.requestSpecification.filter(new RequestLoggingFilter());
        return this;
    }

    public BaseApi logAllSpecificRequestDetail(LogDetail logDetail){
        this.requestSpecification.filter(new RequestLoggingFilter(logDetail));
        return this;
    }

    public void setRedirect(boolean shouldFollowRedirect){
        this.requestSpecification.redirects().follow(shouldFollowRedirect)
                .urlEncodingEnabled(false);
    }

    public BaseApi logAllResponseData(){
        this.requestSpecification.filter(new ResponseLoggingFilter());
        return this;
    }

    public BaseApi logAllSpecificResponseDetail(LogDetail logDetail){
        this.requestSpecification.filter(new ResponseLoggingFilter());
        return this;
    }

    protected Response sendRequest(Method methodType){
        final RequestSpecification when = this.requestSpecification.when();
        return switch(methodType){
            case GET -> when.get();
            case POST -> when.post();
            case PUT -> when.put();
            case DELETE -> when.delete();
            case PATCH -> when.patch();
            default -> throw new IllegalArgumentException("Invalid method type");

        };
    }

}
