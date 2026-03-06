package org.tkit.onecx.notification.domain.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NOTIFICATION_SETTINGS")
public class NotificationSettings extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ALL_OFF")
    private boolean allOff;

    @OneToMany(mappedBy = "notificationSettings", fetch = FetchType.LAZY)
    private Set<MediaConfiguration> preferredMedia = new HashSet<>();

}
