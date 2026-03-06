package org.tkit.onecx.notification.domain.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Content implements Serializable {

    @Column(name = "CONTENT_TITLE")
    private String title;

    @Column(name = "CONTENT_BODY", columnDefinition = "varchar(3000)")
    private String body;

    @Column(name = "CONTENT_ICON")
    private String icon;

}
