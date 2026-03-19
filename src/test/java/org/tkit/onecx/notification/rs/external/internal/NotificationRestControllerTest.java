package org.tkit.onecx.notification.rs.external.internal;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.tkit.onecx.notification.rs.internal.controllers.NotificationRestController;
import org.tkit.onecx.notification.test.AbstractTest;
import org.tkit.quarkus.security.test.GenerateKeycloakClient;
import org.tkit.quarkus.test.WithDBData;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(NotificationRestController.class)
@GenerateKeycloakClient(clientName = "testClient", scopes = { "ocx-no:read", "ocx-no:write" })
@WithDBData(value = { "data/notifications-testdata.xml" }, deleteBeforeInsert = true, rinseAndRepeat = true)
class NotificationRestControllerTest extends AbstractTest {

    @Test
    void testMarkNotificationAsDelivered() {
        given()
                .auth().oauth2(keycloakTestClient.getClientAccessToken("testClient"))
                .when()
                .pathParam("id", "1")
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        given()
                .auth().oauth2(keycloakTestClient.getClientAccessToken("testClient"))
                .when()
                .pathParam("id", "2")
                .get()
                .then()
                .statusCode(200)
                .extract().response();

    }
}
