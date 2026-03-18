package org.tkit.onecx.notification.domain.config;

import io.quarkus.runtime.annotations.ConfigDocFilename;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

/**
 * Notification svc configuration
 */
@ConfigDocFilename("onecx-notification-svc.adoc")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
@ConfigMapping(prefix = "onecx.notification")
public interface NotificationConfig {

    /**
     * Notification publisher scheduler configurations
     */
    @WithName("scheduler")
    NotificationPublishScheduler notificationPublishScheduler();

    interface NotificationPublishScheduler {

        /**
         * Scheduler expression
         * default: every hour
         */
        @WithDefault("0 0 * * * ?")
        @WithName("expression")
        String expression();
    }
}