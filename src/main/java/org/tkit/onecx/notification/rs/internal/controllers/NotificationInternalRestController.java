package org.tkit.onecx.notification.rs.internal.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.notification.domain.service.NotificationService;

import gen.org.tkit.onecx.notification.rs.internal.NotificationInternalApi;
import gen.org.tkit.onecx.notification.rs.internal.model.NotificationDTO;
import gen.org.tkit.onecx.notification.rs.internal.model.NotificationRetrieveRequestDTO;
import gen.org.tkit.onecx.notification.rs.internal.model.NotificationRetrieveResponseDTO;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class NotificationInternalRestController implements NotificationInternalApi {

    @Inject
    NotificationService notificationService;

    @Override
    public Uni<Response> dispatchNotification(NotificationDTO notificationDTO) {
        try (var ignored = notificationService.dispatchNotification(notificationDTO)) {
            return Uni.createFrom().item(Response.status(Response.Status.OK).build());
        }
    }

    @Override
    public Uni<Response> retrieveNotifications(NotificationRetrieveRequestDTO notificationRetrieveRequestDTO) {
        return notificationService.receiveNotification(notificationRetrieveRequestDTO.getReceiverId(),
                notificationRetrieveRequestDTO.getApplicationId())
                .map(notifications -> {
                    var res = new NotificationRetrieveResponseDTO();
                    res.setNotifications(notifications);
                    return Response.status(Response.Status.OK).entity(res).build();
                });
    }
}
