package org.tkit.onecx.notification.rs.external.v1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.notification.domain.service.NotificationService;

import gen.org.tkit.onecx.notification.rs.external.v1.NotificationV1Api;
import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class NotificationV1RestController implements NotificationV1Api {

    @Inject
    NotificationService notificationService;

    @Override
    public Uni<Response> dispatchNotification(NotificationDTOV1 notificationDTOV1) {
        try (var ignored = notificationService.dispatchNotification(notificationDTOV1)) {
            return Uni.createFrom().item(Response.status(Response.Status.OK).build());
        }
    }
}
