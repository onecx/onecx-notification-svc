package org.tkit.onecx.notification.domain.daos;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.onecx.notification.domain.models.Notification;
import org.tkit.quarkus.jpa.daos.AbstractDAO;

@ApplicationScoped
public class NotificationDAO extends AbstractDAO<Notification> {
}
