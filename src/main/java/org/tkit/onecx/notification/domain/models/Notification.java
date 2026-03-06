package org.tkit.onecx.notification.domain.models;

import static jakarta.persistence.EnumType.STRING;

import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NOTIFICATION")
public class Notification extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "SENT_AT")
    private String sentAt;

    @Column(name = "DELIVERED_AT")
    private String deliveredAt;

    @Column(name = "READ_AT")
    private String readAt;

    @Column(name = "SENDER_ID")
    private String senderId;

    @Column(name = "RECEIVER_ID")
    private String receiverId;

    @Column(name = "DELIVERED")
    private boolean delivered;

    @Column(name = "ISSUED_BY")
    @Enumerated(STRING)
    private Issuer issuedBy;

    @Column(name = "SEVERITY")
    @Enumerated(STRING)
    private Severity severity;

    @Column(name = "MEDIA")
    @Enumerated(STRING)
    private Media media;

    @Embedded
    private Content content;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentMeta> contentMeta;

    public enum Issuer {
        SYSTEM,
        USER
    }

    public enum Severity {
        LOW,
        NORMAL,
        CRITICAL
    }

    public enum Media {
        EMAIL,
        ONECX_UI
    }
}
