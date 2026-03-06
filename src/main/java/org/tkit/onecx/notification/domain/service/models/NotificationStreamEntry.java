package org.tkit.onecx.notification.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationStreamEntry {
  private String id;
  private String title;
  private String body;
  private String icon;
}