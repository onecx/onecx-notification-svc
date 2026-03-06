package org.tkit.onecx.notification.domain.service.mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tkit.onecx.notification.domain.models.ContentMeta;
import org.tkit.onecx.notification.domain.models.Notification;
import org.tkit.onecx.notification.domain.service.models.NotificationStreamEntry;

import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;
import gen.org.tkit.onecx.notification.rs.internal.model.ContentMetaDTO;
import gen.org.tkit.onecx.notification.rs.internal.model.NotificationDTO;

@Mapper
public interface NotificationMapper {

    List<NotificationDTO> mapEntries(List<NotificationStreamEntry> streamEntries);

    @Mapping(target = "severity", ignore = true)
    @Mapping(target = "removeContentMetaItem", ignore = true)
    @Mapping(target = "persist", ignore = true)
    @Mapping(target = "issuer", ignore = true)
    @Mapping(target = "contentTitle", source = "title")
    @Mapping(target = "contentMeta", ignore = true)
    @Mapping(target = "contentIcon", source = "icon")
    @Mapping(target = "contentBody", source = "body")
    NotificationDTO map(NotificationStreamEntry notificationStreamEntry);

    @Mapping(target = "title", source = "contentTitle")
    @Mapping(target = "icon", source = "contentIcon")
    @Mapping(target = "body", source = "contentBody")
    NotificationStreamEntry mapDTOToStream(NotificationDTO notificationDTO);

    @Mapping(target = "title", source = "contentTitle")
    @Mapping(target = "icon", source = "contentIcon")
    @Mapping(target = "body", source = "contentBody")
    NotificationStreamEntry mapDTOToStream(NotificationDTOV1 notificationDTOV1);

    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "issuedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "delivered", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "content.body", source = "contentBody")
    @Mapping(target = "content.title", source = "contentTitle")
    @Mapping(target = "content.icon", source = "contentIcon")
    Notification mapDTO(NotificationDTO notificationDTO);

    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "issuedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "delivered", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "content.body", source = "contentBody")
    @Mapping(target = "content.title", source = "contentTitle")
    @Mapping(target = "content.icon", source = "contentIcon")
    Notification mapDTOV1(NotificationDTOV1 notificationDTOv1);

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

    List<ContentMeta> map(List<Map<String, String>> contentMeta);

    default ContentMeta map(Map<String, String> contentMetaItem) {
        if (contentMetaItem == null) {
            return null;
        }
        ContentMeta contentMeta = new ContentMeta();
        contentMeta.setKey(contentMetaItem.get("key"));
        contentMeta.setValue(contentMetaItem.get("value"));
        return contentMeta;
    }
}
