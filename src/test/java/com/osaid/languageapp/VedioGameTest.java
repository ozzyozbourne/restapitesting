package com.osaid.languageapp;

import config.test.TestBaseConfig;
import end.points.VedioGamesEndPoint;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;
import pojo.PojoA;
import pojo.PojoB;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class VedioGameTest extends TestBaseConfig {

    @Test
    public void testA(){
        given().
        when().get(VedioGamesEndPoint.ALL_VEDIO_GAMES).
        then();
    }

    @Test
    public void testB(){
        given().
                body(new File(getFile.apply("postJsonA"))).
        when().
                post(VedioGamesEndPoint.ALL_VEDIO_GAMES).
        then();
    }

    @Test
    public void testC(){
        given().
                body(new File(getFile.apply("postXml"))).
                headers("Content-Type", "application/xml").
                headers("Accept", "application/xml").
        when().
                post(VedioGamesEndPoint.ALL_VEDIO_GAMES).
        then();
    }

    @Test
    public void testD(){
        given().
                pathParam("videoGameId", "0").
        when().
                get(VedioGamesEndPoint.SINGLE_VEDIO_GAME).
        then();
    }

    @Test
    public void testE(){
        PojoA pojoA = new PojoA("99", "2018-04-04", "My Awesome Game", "Mature", "50", "Shooter");
        given().
                body(pojoA).
        when().
                post(VedioGamesEndPoint.ALL_VEDIO_GAMES).
        then();
    }

    @Test
    public void testVedioGameSchema(){
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/xml");
        headerMap.put("Accept", "application/xml");

        given().
                pathParam("videoGameId", "50").
                headers(headerMap).
        when().
                get(VedioGamesEndPoint.SINGLE_VEDIO_GAME).
        then().
                body(matchesXsdInClasspath("VideoGameSchema.xsd"));
    }

    @Test
    public void VedioGameJsonSchema(){
        given().
                pathParam("videoGameId", "50").
        when()
                .get(VedioGamesEndPoint.SINGLE_VEDIO_GAME).
        then().
                body(matchesJsonSchemaInClasspath("VideoGameJsomSchema.json"));
    }

    @Test
    public void jsonToPojoTest(){
        var gameObj =  given().
                pathParam("videoGameId", "50").
                when()
                .get(VedioGamesEndPoint.SINGLE_VEDIO_GAME).
       then().extract().response().body().as(PojoA.class, ObjectMapperType.JACKSON_2);
        System.out.println(gameObj);
    }

    @Test
    public void jsonToPojoIncompleteTest() throws Exception{
        var gameObj =  given().
                pathParam("videoGameId", "50").
                when()
                .get(VedioGamesEndPoint.SINGLE_VEDIO_GAME).
                then().extract().response().asPrettyString();
        var c = objectMapper.readValue(gameObj, PojoB.class);

        System.out.println(c.toString());

    }
}
