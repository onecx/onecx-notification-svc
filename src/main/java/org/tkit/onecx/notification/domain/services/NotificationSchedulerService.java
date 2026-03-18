package org.tkit.onecx.notification.domain.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tkit.onecx.notification.domain.daos.NotificationDAO;
import org.tkit.onecx.notification.rs.external.v1.mappers.NotificationMapper;

import gen.org.tkit.onecx.notification.bff.client.api.NotificationInternalBffApi;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class NotificationSchedulerService {

    static final String JOB_ID = "notification.publish";
    @Inject
    NotificationDAO notificationDAO;

    @Inject
    @RestClient
    NotificationInternalBffApi bffApi;

    @Inject
    NotificationMapper mapper;

    @Scheduled(identity = "notification.publish", cron = "${onecx.notification.scheduler.expression}")
    void republishPersistedNotifications() {
        var notifications = notificationDAO.findAllNotDelivered();
        try {
            notifications.forEach(notification -> {
                try {
                    bffApi.dispatchNotification(mapper.mapToBffDTO(notification, true));
                } catch (Exception ex) {
                    Log.error("Error dispatching notification with id " + notification.getId(), ex);
                }
            });
        } catch (Exception ex) {
            log.error("Scheduler for job id: '" + JOB_ID + "' failed.", ex);
            throw ex;
        }
    }
}
