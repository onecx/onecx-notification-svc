package org.tkit.onecx.notification.domain.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.notification.domain.service.models.NotificationStreamEntry;

import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.stream.ReactiveStreamCommands;
import io.quarkus.redis.datasource.stream.XGroupCreateArgs;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class NotificationRedisService {

    //tenantId tba
    private static final String STREAM_KEY_PATTERN = "notifications:%s:%s";
    private static final String CONSUMER_GROUP = "notification-group";
    private static final String CONSUMER_NAME = "notification-consumer";
    private ReactiveRedisDataSource reactiveRedisDataSource;
    private ReactiveStreamCommands<String, String, String> reactiveStreamCommands;

    public NotificationRedisService(ReactiveRedisDataSource reactiveRedisDataSource) {
        this.reactiveRedisDataSource = reactiveRedisDataSource;
        reactiveStreamCommands = reactiveRedisDataSource.stream(String.class, String.class, String.class);
    }

    private String streamKey(String receiverId, String applicationId) {
        return String.format(STREAM_KEY_PATTERN, receiverId, applicationId);
    }

    private Uni<Void> ensureConsumerGroup(String streamKey) {
        return reactiveStreamCommands.xgroupCreate(streamKey, CONSUMER_GROUP, "0", new XGroupCreateArgs().mkstream())
                .onFailure().recoverWithNull()
                .replaceWithVoid();
    }

    public Response addStreamEntry(NotificationStreamEntry notificationEntry) {
        String streamKey = String.format("notifications:%s:%s",
                notificationEntry.getReceiverId(),
                notificationEntry.getApplicationId());
        Map<String, String> entryData = new LinkedHashMap<>();
        entryData.put("applicationId", notificationEntry.getApplicationId());
        entryData.put("senderId", notificationEntry.getSenderId());
        entryData.put("receiverId", notificationEntry.getReceiverId());
        entryData.put("title", notificationEntry.getTitle());
        entryData.put("body", notificationEntry.getBody());
        entryData.put("icon", notificationEntry.getIcon());

        reactiveStreamCommands.xadd(streamKey, entryData)
                .subscribe().with(
                        id -> Log.infof("Stream entry added: %s", id),
                        Throwable::printStackTrace);

        return Response.status(Response.Status.OK).build();
    }

    public Uni<List<NotificationStreamEntry>> readStreamEntries(String receiverId, String applicationId) {
        String streamKey = streamKey(receiverId, applicationId);
        return reactiveStreamCommands.xread(streamKey, "0")
                .map(stream -> {
                    List<NotificationStreamEntry> streamEntries = new ArrayList<>();
                    List<String> ids = new ArrayList<>();
                    stream.forEach(streamEntry -> {
                        ids.add(streamEntry.id());
                        NotificationStreamEntry entry = new NotificationStreamEntry();
                        entry.setApplicationId(streamEntry.payload().get("applicationId"));
                        entry.setSenderId(streamEntry.payload().get("senderId"));
                        entry.setReceiverId(streamEntry.payload().get("receiverId"));
                        entry.setTitle(streamEntry.payload().get("title"));
                        entry.setBody(streamEntry.payload().get("body"));
                        entry.setIcon(streamEntry.payload().get("icon"));
                        streamEntries.add(entry);
                    });
                    return Map.entry(ids, streamEntries);
                })
                .call(result -> {
                    List<String> ids = result.getKey();
                    if (!ids.isEmpty()) {
                        return reactiveStreamCommands.xdel(streamKey, ids.toArray(new String[0]))
                                .chain(() -> reactiveStreamCommands.xlen(streamKey))
                                .chain(remaining -> {
                                    if (remaining == 0) {
                                        return reactiveRedisDataSource.key(String.class).del(streamKey).replaceWithVoid();
                                    }
                                    return Uni.createFrom().voidItem();
                                });
                    }
                    return Uni.createFrom().voidItem();
                })
                .map(Map.Entry::getValue);
    }
}
