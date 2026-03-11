package org.tkit.onecx.notification.rs.external.v1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tkit.onecx.notification.domain.models.ContentMeta;
import org.tkit.onecx.notification.domain.models.Notification;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import gen.org.tkit.onecx.notification.bff.client.model.ContentMetaBffDTO;
import gen.org.tkit.onecx.notification.bff.client.model.NotificationBffDTO;
import gen.org.tkit.onecx.notification.rs.external.v1.model.ContentMetaDTOV1;
import gen.org.tkit.onecx.notification.rs.external.v1.model.NotificationDTOV1;

@Mapper(uses = OffsetDateTimeMapper.class)
public interface NotificationMapper {

    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "issuedBy", source = "issuer")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "delivered", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "contentMeta", ignore = true)
    Notification mapToPersist(NotificationDTOV1 notificationDTOV1);

    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "issuedBy", source = "issuer")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "delivered", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    Notification map(NotificationDTOV1 notificationDTOV1);

    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual")
    @Mapping(target = "notification", source = "notification")
    ContentMeta map(ContentMetaDTOV1 contentMetaDTOV1, Notification notification);

    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "notification", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    ContentMeta map(ContentMetaDTOV1 contentMetaDTOV1);

    ContentMetaBffDTO mapToBffDTO(ContentMetaDTOV1 contentMetaDTOV1);

    @Mapping(target = "issuer", source = "notification.issuedBy")
    NotificationBffDTO mapToBffDTO(Notification notification, boolean persist);

    @Mapping(target = "issuer", source = "notification.issuedBy")
    @Mapping(target = "contentMeta", source = "contentMeta")
    NotificationBffDTO mapToBffDTOWithMeta(Notification notification, Boolean persist, List<ContentMetaDTOV1> contentMeta);

    @Mapping(target = "creationDate", ignore = true)
    NotificationBffDTO mapToBffDTODispatch(NotificationDTOV1 notificationDTOV1, String id);
}
