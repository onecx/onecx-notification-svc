package org.tkit.onecx.notification.domain.daos;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.onecx.notification.domain.models.ContentMeta;
import org.tkit.quarkus.jpa.daos.AbstractDAO;

@ApplicationScoped
public class ContentMetaDAO extends AbstractDAO<ContentMeta> {
}
