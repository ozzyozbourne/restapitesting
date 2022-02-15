package com.osaid.languageapp;

import com.jayway.jsonpath.JsonPath;
import config.test.FootBallApiConFig;
import end.points.FootBallApi;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testG(){
       ArrayList<?> s = given().
                pathParam("year", "2021").
                when().
                get(FootBallApi.COMPETITIONSNEW).then().extract().response().path("teams");
        s.forEach(System.out::println);
        System.out.println("-----------------------------------------------------");
        System.out.println(s.get(0).getClass());
    }

    @Test
    public void testH(){
      Map<String, ?> st =  given().
                pathParam("year", "2021").
                when().
                get(FootBallApi.COMPETITIONSNEW).then().extract().response()
              .path("teams.find { it.name == 'Leeds United FC' }");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(st);
    }

    @Test
    public void testI(){
      String name  =   given().
                pathParam("teamId", "57").
        when().
                get(FootBallApi.TEAMS).
        then().
                extract().response().path("squad.find { it.id == 110 }.name");

        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(name);
    }

    @Test
    public void testk(){
       List<String> name  =   given().
                pathParam("teamId", "57").
                when().
                get(FootBallApi.TEAMS).
                then().
                extract().response().path("squad.findAll { it.position == 'Midfielder' }.name");

        System.out.println("-------------------------------------------------------------------------------------");
        name.forEach(System.out::println);
    }

    @Test
    public void testJ(){
        String name  =   given().
                pathParam("teamId", "57").
                when().
                get(FootBallApi.TEAMS).
                then().
                extract().response().path("squad.max { it.shirtNumber }.name");

        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(name);
    }

    @Test
    public void testL(){
        var names  =   given().
                pathParam("teamId", "57").
                when().
                get(FootBallApi.TEAMS);
        var name = names.path("squad.max { it.shirtNumber }.shirtNumber");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(name);
    }

    @Test
    public void testM(){
        var names  =   given().
                pathParam("teamId", "57").
                when().
                get(FootBallApi.TEAMS);
        var name = names.path("squad.collect { it.id }.sum()");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(name);
    }

    @Test
    public void testK(){
        var names  =   given().
                pathParam("teamId", "57").
                when().
                get(FootBallApi.TEAMS);
        var name = names.path("squad.findAll { it.position == 'Attacker' }.findAll {it.nationality == 'England'}");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(name);
    }

    @Test
    public void testN(){
        var a = "Attacker";
        var b = "England";
        var names  =   given().
                pathParam("teamId", "57").
                when().
                get(FootBallApi.TEAMS);
        var name = names.path("squad.findAll { it.position == '%s' }.findAll {it.nationality == '%s'}", a, b);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(name);
    }


}

