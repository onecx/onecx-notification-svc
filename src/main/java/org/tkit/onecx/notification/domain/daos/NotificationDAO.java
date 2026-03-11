package org.tkit.onecx.notification.domain.daos;

import static org.tkit.onecx.notification.domain.models.Notification_.DELIVERED;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.tkit.onecx.notification.domain.models.Notification;
import org.tkit.quarkus.jpa.daos.AbstractDAO;
import org.tkit.quarkus.jpa.models.TraceableEntity_;

@ApplicationScoped
public class NotificationDAO extends AbstractDAO<Notification> {

    @Transactional
    public void markAsDelivered(String notificationId) {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createCriteriaUpdate(Notification.class);
            var root = cq.from(Notification.class);
            cq.set(root.get(DELIVERED), true);
            cq.where(cb.equal(root.get(TraceableEntity_.ID), notificationId));
            this.getEntityManager().createQuery(cq).executeUpdate();
        } catch (Exception ex) {
            throw handleConstraint(ex, ErrorKeys.ERROR_FIND_ALL_NOT_DELIVERED);
        }
    }

    @Transactional
    public List<Notification> findAllNotDelivered() {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(Notification.class);
            var root = cq.from(Notification.class);
            cq.where(cb.equal(root.get(DELIVERED), false));
            return this.getEntityManager().createQuery(cq).setHint(HINT_LOAD_GRAPH, Notification.NOTIFICATION_FULL)
                    .getResultList();
        } catch (Exception ex) {
            throw handleConstraint(ex, ErrorKeys.ERROR_FIND_ALL_NOT_DELIVERED);
        }
    }

    public enum ErrorKeys {
        ERROR_FIND_ALL_NOT_DELIVERED,
    }
}
