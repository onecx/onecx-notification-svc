package org.tkit.onecx.notification.domain.services;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.JsonBody;
import org.tkit.onecx.notification.test.AbstractTest;
import org.tkit.quarkus.test.WithDBData;

import gen.org.tkit.onecx.notification.bff.client.model.IssuerBffDTO;
import gen.org.tkit.onecx.notification.bff.client.model.NotificationBffDTO;
import gen.org.tkit.onecx.notification.bff.client.model.SeverityBffDTO;
import io.quarkiverse.mockserver.test.InjectMockServerClient;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@WithDBData(value = {
        "data/notifications-testdata.xml" }, deleteBeforeInsert = true, deleteAfterTest = true, rinseAndRepeat = true)
class NotificationStartUpServiceTest extends AbstractTest {

    @Inject
    NotificationStartUpService service;

    @InjectMockServerClient
    MockServerClient mockServerClient;

    @BeforeEach
    void resetExpectation() {
        clearExpectation(mockServerClient);
    }

    @Test
    void startupTest() {
        NotificationBffDTO notificationBffDTO = new NotificationBffDTO();
        notificationBffDTO.persist(true).applicationId("app1").senderId("user1").receiverId("user2")
                .id("2")
                .severity(SeverityBffDTO.LOW).issuer(IssuerBffDTO.SYSTEM);

        addExpectation(mockServerClient
                .when(request().withPath("/notifications/dispatch").withMethod(HttpMethod.POST)
                        .withBody(JsonBody.json(notificationBffDTO)))
                .respond(httpRequest -> response().withStatusCode(Response.Status.OK.getStatusCode())));
        service.init();
        Assertions.assertDoesNotThrow(() -> service.init());
    }

    @Test
    void startupTestException() {
        NotificationBffDTO notificationBffDTO = new NotificationBffDTO();
        notificationBffDTO.persist(true).applicationId("app1").senderId("user1").receiverId("user2")
                .id("2")
                .severity(SeverityBffDTO.LOW).issuer(IssuerBffDTO.SYSTEM);

        addExpectation(mockServerClient
                .when(request().withPath("/notifications/dispatch").withMethod(HttpMethod.POST)
                        .withBody(JsonBody.json(notificationBffDTO)))
                .respond(httpRequest -> response().withStatusCode(Response.Status.BAD_REQUEST.getStatusCode())));
        service.init();
        Assertions.assertDoesNotThrow(() -> service.init());
    }
}
