package config.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestBaseConfig {

    private static final Map<String, String> headerMap = new HashMap<>();
    private static final List<Filter> filters = new ArrayList<>();
    private static RequestSpecification vediogame_requestSpec;
    private static ResponseSpecification vediogame_responseSpec;
    private static final String pathToFile = "/src/test/resources/";
    public static final Function<String, String> getFile = s -> System.getProperty("user.dir")+ pathToFile + s +".txt";
    public static final Supplier<ObjectMapper> getObjectMapper = () ->
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static final ObjectMapper    objectMapper = getObjectMapper.get();

    @BeforeAll
    public static void setup(){

        headerMap.put("Content-Type", "application/json");
        headerMap.put("Accept", "application/json");

        filters.add(new RequestLoggingFilter());
        filters.add(new ResponseLoggingFilter());

        vediogame_requestSpec = new RequestSpecBuilder().
                setBaseUri( "http://localhost").
                setBasePath("/app/").
                setPort(8080).
                addHeaders(headerMap).
                addFilters(filters).
                build();

        vediogame_responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).build();

        RestAssured.requestSpecification = vediogame_requestSpec;
        RestAssured.responseSpecification = vediogame_responseSpec;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (cls, charset) -> {
                    ObjectMapper om = new ObjectMapper().findAndRegisterModules();
                    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return om;
                }
        ));

    }
}
