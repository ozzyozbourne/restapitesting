package com.osaid.languageapp;

import com.jayway.jsonpath.JsonPath;
import config.test.FootBallApiConFig;
import end.points.FootBallApi;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FootBallTest extends FootBallApiConFig {

    @Test
    public void testA(){

        given().
                queryParam("areas", 2072).
        when().
                get("areas").
        then();

    }

    @Test
    public void testB(){
        given().
                pathParam("teamId", "57").
        when().
                get(FootBallApi.TEAMS).
        then().
                body("founded", equalTo(1886));
    }

    @Test
    public void testC(){
        var resposne  = given().
                                pathParam("teamId", "57").
                        when().
                                get(FootBallApi.TEAMS).
                        then().
                                contentType(ContentType.JSON).
                                extract().response();

        var header = resposne.getHeaders();
        System.out.println(header.toString());
    }

    @Test
    public void testD(){
        var resposne =  given().
                                  pathParam("year", "2021").
                        when().
                                  get(FootBallApi.COMPETITIONS).jsonPath().get("seasons[0].currentMatchday");
        System.out.println(resposne);
    }

    @Test
    public void testE(){
        ArrayList<?> resposne =  given().
                pathParam("year", "2021").
                when().
                get(FootBallApi.COMPETITIONSNEW).path("teams.name");
        resposne.forEach(System.out::println);
    }

    @Test
    public void testF(){
        var resposne =  given().
                pathParam("year", "2021").
                when().
                get(FootBallApi.COMPETITIONSNEW).then().extract().response().asPrettyString();

       List<?> a = JsonPath.read(resposne, "teams..shortName");
       a.forEach(System.out::println);
    }

}

