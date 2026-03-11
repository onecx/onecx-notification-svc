package org.tkit.onecx.notification.rs.internal.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.notification.domain.daos.NotificationDAO;

import gen.org.tkit.onecx.notification.rs.internal.NotificationInternalApi;

@ApplicationScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class NotificationRestController implements NotificationInternalApi {

    @Inject
    NotificationDAO notificationDAO;

    @Override
    public Response markNotificationAsDelivered(String id) {
        notificationDAO.markAsDelivered(id);
        return Response.status(Response.Status.OK).build();
    }
}
