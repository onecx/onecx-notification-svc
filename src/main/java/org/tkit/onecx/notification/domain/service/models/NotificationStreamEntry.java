package org.tkit.onecx.notification.domain.service.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationStreamEntry {
    private String applicationId;
    private String senderId;
    private String receiverId;
    private String title;
    private String body;
    private String icon;
    private String severity;
}