package org.tkit.onecx.notification.domain.models;

import static jakarta.persistence.EnumType.STRING;

import java.util.List;

import jakarta.persistence.*;

import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "NOTIFICATION")
@NamedEntityGraph(name = Notification.NOTIFICATION_FULL, includeAllAttributes = true)
public class Notification extends TraceableEntity {
    public static final String NOTIFICATION_FULL = "Notification.full";

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "SENT_AT")
    private String sentAt;

    @Column(name = "DELIVERED_AT")
    private String deliveredAt;

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

}
