package com.smartboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private Date created;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private String updatedBy;

    @JsonIgnore
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isPersistent() {
        return id != null;
    }

    @JsonIgnore
    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = created;
    }

    @JsonIgnore
    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }


    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreated() {
        return created;
    }

    @JsonIgnore
    public String getCreatedFormat() {
        DateFormat sdfFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return sdfFormat.format(created);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    @JsonIgnore
    public String getUpdatedFormat() {
        DateFormat sdfFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return sdfFormat.format(updated);
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @JsonIgnore
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(this.id == null) {
            return false;
        }

        if (obj instanceof AbstractEntity && obj.getClass().equals(getClass())) {
            return this.id.equals(((AbstractEntity) obj).id);
        }

        return false;
    }

    @JsonIgnore
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
