package org.tkit.onecx.notification.domain.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.notification.domain.daos.NotificationDAO;
import org.tkit.onecx.notification.domain.service.mapper.NotificationMapper;

import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;
import gen.org.tkit.onecx.notification.rs.internal.model.NotificationDTO;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRedisService redisService;

    @Inject
    NotificationMapper notificationMapper;

    @Inject
    NotificationDAO notificationDAO;

    public Response dispatchNotification(NotificationDTOV1 notificationDTOv1) {
        var notificationEntry = notificationMapper.mapDTOToStream(notificationDTOv1);
        if (Boolean.TRUE.equals(notificationDTOv1.getPersist())) {
            notificationDAO.create(notificationMapper.mapDTOV1(notificationDTOv1));
        }
        return redisService.addStreamEntry(notificationEntry);
    }

    public Response dispatchNotification(NotificationDTO notificationDTO) {
        var notificationEntry = notificationMapper.mapDTOToStream(notificationDTO);
        if (Boolean.TRUE.equals(notificationDTO.getPersist())) {
            notificationDAO.create(notificationMapper.mapDTO(notificationDTO));
        }
        return redisService.addStreamEntry(notificationEntry);
    }

    public Uni<List<NotificationDTO>> receiveNotification(String receiverId, String applicationId) {
        return redisService.readStreamEntries(receiverId, applicationId)
                .map(notificationMapper::mapEntries);
    }
}
