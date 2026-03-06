package org.tkit.onecx.notification.domain.models;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MEDIA_CONFIGURATION")
public class MediaConfiguration extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "CONFIG")
    private String config;

    @Column(name = "ENDPOINT")
    private String endpoint;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "NOTIFICATION_SETTINGS_GUID")
    private NotificationSettings notificationSettings;
}
