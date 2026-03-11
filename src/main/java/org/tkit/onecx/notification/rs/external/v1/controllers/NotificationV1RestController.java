package org.tkit.onecx.notification.rs.external.v1.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.notification.domain.services.NotificationService;

import gen.org.tkit.onecx.notification.rs.external.v1.NotificationV1Api;
import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;

@ApplicationScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class NotificationV1RestController implements NotificationV1Api {

    @Inject
    NotificationService notificationService;

    @Override
    public Response dispatchNotification(NotificationDTOV1 notificationDTOV1) {
        try (Response res = notificationService.dispatchNotification(notificationDTOV1)) {
            return Response.status(res.getStatus()).build();
        }
    }
}
