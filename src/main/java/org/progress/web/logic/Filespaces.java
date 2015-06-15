package org.progress.web.logic;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Filespaces")
public class Filespaces implements Serializable {

    private int id;
    private String filespacesUUID;
    private Date creationDate;
    private String Name;
    private boolean deleted;

    public Filespaces() {
    }

    public Filespaces(String Name) {
        this.filespacesUUID = UUID.randomUUID().toString();
        this.creationDate = new Date();
        this.Name = Name;
        this.deleted = false;
    }

    @Column(name = "FilespacesUUID")
    public String getFilespacesUUID() {
        return filespacesUUID;
    }

    public void setFilespacesUUID(String FilespacesUUID) {
        this.filespacesUUID = FilespacesUUID;
    }

    @Column(name = "Name")
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
