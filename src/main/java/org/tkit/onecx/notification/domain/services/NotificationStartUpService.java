package org.tkit.onecx.notification.domain.services;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tkit.onecx.notification.domain.daos.NotificationDAO;
import org.tkit.onecx.notification.rs.external.v1.mappers.NotificationMapper;

import gen.org.tkit.onecx.notification.bff.client.api.NotificationInternalBffApi;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;

@Startup
@Singleton
public class NotificationStartUpService {

    @Inject
    NotificationDAO notificationDAO;

    @Inject
    NotificationMapper mapper;

    @Inject
    @RestClient
    NotificationInternalBffApi bffApi;

    @PostConstruct
    public void init() {
        var notDeliveredNotifications = notificationDAO.findAllNotDelivered();
        notDeliveredNotifications.forEach(notification -> {
            try {
                bffApi.dispatchNotification(mapper.mapToBffDTO(notification, true));
            } catch (Exception ex) {
                Log.error("Error dispatching notification with id " + notification.getId(), ex);
            }
        });
    }
}
