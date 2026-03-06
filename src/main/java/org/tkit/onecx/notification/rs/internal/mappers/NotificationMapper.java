package org.tkit.onecx.notification.rs.internal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tkit.onecx.notification.domain.models.ContentMeta;
import org.tkit.onecx.notification.domain.models.Notification;

import gen.org.tkit.onecx.notification.rs.internal.model.ContentMetaDTO;
import gen.org.tkit.onecx.notification.rs.internal.model.NotificationDTO;

@Mapper
public interface NotificationMapper {
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "issuedBy", source = "issuer")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "delivered", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "content.body", source = "contentBody")
    @Mapping(target = "content.title", source = "contentTitle")
    @Mapping(target = "content.icon", source = "contentIcon")
    Notification map(NotificationDTO notificationDTO);

    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "notification", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    ContentMeta map(ContentMetaDTO contentMetaDTO);
}
