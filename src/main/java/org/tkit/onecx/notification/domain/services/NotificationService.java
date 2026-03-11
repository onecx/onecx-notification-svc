package org.tkit.onecx.notification.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tkit.onecx.notification.domain.daos.ContentMetaDAO;
import org.tkit.onecx.notification.domain.daos.NotificationDAO;
import org.tkit.onecx.notification.rs.external.v1.mappers.NotificationMapper;

import gen.org.tkit.onecx.notification.bff.client.api.NotificationInternalBffApi;
import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;

@ApplicationScoped
public class NotificationService {
    @Inject
    NotificationDAO notificationDAO;

    @Inject
    ContentMetaDAO contentMetaDAO;

    @Inject
    NotificationMapper mapper;

    @Inject
    @RestClient
    NotificationInternalBffApi bffApi;

    public Response dispatchNotification(NotificationDTOV1 notificationDTOV1) {
        var notification = mapper.mapToPersist(notificationDTOV1);
        if (notificationDTOV1.getPersist()) {
            var createdNotification = notificationDAO.create(notification);
            notificationDTOV1.getContentMeta()
                    .forEach(contentMetaDTOV1 -> contentMetaDAO.create(mapper.map(contentMetaDTOV1, createdNotification)));
        }
        try (Response ignore = bffApi.dispatchNotification(
                mapper.mapToBffDTODispatch(notificationDTOV1, notification.getId()))) {
            return Response.status(Response.Status.OK).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.OK).build();
        }
    }
}
