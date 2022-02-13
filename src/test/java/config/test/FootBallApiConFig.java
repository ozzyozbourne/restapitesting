package config.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class FootBallApiConFig {

    private static final Map<String, String> headerMap = new HashMap<>();
    private static final List<Filter> filters = new ArrayList<>();
    private static RequestSpecification football_requestSpec;
    private static ResponseSpecification football_responseSpec;
    private static final String pathToFile = "/src/test/resources/";
    public static final Function<String, String> getFile = s -> System.getProperty("user.dir")+ pathToFile + s +".txt";

    @BeforeAll
    public static void setup(){

        headerMap.put("X-Auth-Token", "7f5498058ef54161adc7694307a08dcd");
        headerMap.put("X-Response-Control", "minified");

        filters.add(new RequestLoggingFilter());
        filters.add(new ResponseLoggingFilter());

        football_requestSpec = new RequestSpecBuilder().
                setBaseUri( "https://api.football-data.org").
                setBasePath("/v2/").
                addHeaders(headerMap).
                addFilters(filters).
                build();

        football_responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).build();

        RestAssured.requestSpecification = football_requestSpec;
        RestAssured.responseSpecification = football_responseSpec;

    }

}
