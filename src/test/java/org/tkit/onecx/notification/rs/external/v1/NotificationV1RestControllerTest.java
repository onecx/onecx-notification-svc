package org.tkit.onecx.notification.rs.external.v1;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.List;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.JsonBody;
import org.tkit.onecx.notification.rs.external.v1.controllers.NotificationV1RestController;
import org.tkit.onecx.notification.test.AbstractTest;
import org.tkit.quarkus.security.test.GenerateKeycloakClient;
import org.tkit.quarkus.test.WithDBData;

import gen.org.tkit.onecx.notification.bff.client.model.ContentMetaBffDTO;
import gen.org.tkit.onecx.notification.bff.client.model.IssuerBffDTO;
import gen.org.tkit.onecx.notification.bff.client.model.NotificationBffDTO;
import gen.org.tkit.onecx.notification.bff.client.model.SeverityBffDTO;
import gen.org.tkit.onecx.notification.rs.external.v1.model.ContentMetaDTOV1;
import gen.org.tkit.onecx.notification.rs.external.v1.model.IssuerDTOV1;
import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;
import gen.org.tkit.onecx.notification.rs.external.v1.model.SeverityDTOV1;
import io.quarkiverse.mockserver.test.InjectMockServerClient;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(NotificationV1RestController.class)
@GenerateKeycloakClient(clientName = "testClient", scopes = { "ocx-no:read", "ocx-no:write" })
@WithDBData(value = {
        "data/notifications-testdata.xml" }, deleteBeforeInsert = true, deleteAfterTest = true, rinseAndRepeat = true)
class NotificationV1RestControllerTest extends AbstractTest {

    @InjectMockServerClient
    MockServerClient mockServerClient;

    @BeforeEach
    void resetExpectation() {
        clearExpectation(mockServerClient);
    }

    @Test
    void dispatchNotificationWithPersistTest() {

        NotificationDTOV1 notificationDTOV1 = new NotificationDTOV1();
        notificationDTOV1.persist(true).applicationId("app1").senderId("sender1").receiverId("receiver1")
                .severity(SeverityDTOV1.LOW).issuer(IssuerDTOV1.USER)
                .contentMeta(List.of(new ContentMetaDTOV1().key("key").value("value")));

        NotificationBffDTO notificationBffDTO = new NotificationBffDTO();
        notificationBffDTO.persist(true).applicationId("app1").senderId("sender1").receiverId("receiver1")
                .severity(SeverityBffDTO.LOW).issuer(IssuerBffDTO.USER)
                .contentMeta(List.of(new ContentMetaBffDTO().key("key").value("value")));

        addExpectation(mockServerClient
                .when(request().withPath("/notifications/dispatch").withMethod(HttpMethod.POST)
                        .withBody(JsonBody.json(notificationBffDTO)))
                .respond(httpRequest -> response().withStatusCode(Response.Status.OK.getStatusCode())));

        given()
                .auth().oauth2(keycloakTestClient.getClientAccessToken("testClient"))
                .when()
                .contentType(APPLICATION_JSON)
                .body(notificationDTOV1)
                .post()
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test
    void dispatchNotificationFailedButPersistTest() {

        NotificationDTOV1 notificationDTOV1 = new NotificationDTOV1();
        notificationDTOV1.persist(true).applicationId("app1").senderId("sender1").receiverId("receiver1")
                .severity(SeverityDTOV1.LOW).issuer(IssuerDTOV1.USER)
                .contentMeta(List.of(new ContentMetaDTOV1().key("key").value("value")));

        NotificationBffDTO notificationBffDTO = new NotificationBffDTO();
        notificationBffDTO.persist(true).applicationId("app1").senderId("sender1").receiverId("receiver1")
                .severity(SeverityBffDTO.LOW).issuer(IssuerBffDTO.USER)
                .contentMeta(List.of(new ContentMetaBffDTO().key("key").value("value")));

        addExpectation(mockServerClient
                .when(request().withPath("/notifications/dispatch").withMethod(HttpMethod.POST)
                        .withBody(JsonBody.json(notificationBffDTO)))
                .respond(httpRequest -> response().withStatusCode(Response.Status.BAD_REQUEST.getStatusCode())));

        given()
                .auth().oauth2(keycloakTestClient.getClientAccessToken("testClient"))
                .when()
                .contentType(APPLICATION_JSON)
                .body(notificationDTOV1)
                .post()
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test
    void dispatchNotificationWithoutPersistTest() {

        NotificationDTOV1 notificationDTOV1 = new NotificationDTOV1();
        notificationDTOV1.persist(false).applicationId("app1").senderId("sender1").receiverId("receiver1")
                .severity(SeverityDTOV1.LOW).issuer(IssuerDTOV1.USER);

        NotificationBffDTO notificationBffDTO = new NotificationBffDTO();
        notificationBffDTO.persist(false).applicationId("app1").senderId("sender1").receiverId("receiver1")
                .severity(SeverityBffDTO.LOW).issuer(IssuerBffDTO.USER);

        addExpectation(mockServerClient
                .when(request().withPath("/notifications/dispatch").withMethod(HttpMethod.POST)
                        .withBody(JsonBody.json(notificationBffDTO)))
                .respond(httpRequest -> response().withStatusCode(Response.Status.OK.getStatusCode())));

        given()
                .auth().oauth2(keycloakTestClient.getClientAccessToken("testClient"))
                .when()
                .contentType(APPLICATION_JSON)
                .body(notificationDTOV1)
                .post()
                .then()
                .statusCode(200)
                .extract().response();
    }
}
