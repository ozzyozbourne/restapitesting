package com.osaid.languageapp;

import end.points.VedioGamesEndPoint;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import config.test.TestBaseConfig;

public class SayHelloTest extends TestBaseConfig{
    @Test
    public void testSayHello() {
        given()
                .log().all()
        .when()
                .get("videogames")
        .then()
                .log().all();
    }

    @Test
    public void InterFaceTest(){
        get(VedioGamesEndPoint.ALL_VEDIO_GAMES).then().log().all();
    }
}
