package org.tkit.onecx.notification.domain.models;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NOTIFICATION_META")
public class NotificationMeta extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTIFICATION_ID")
    private Notification notification;

}
