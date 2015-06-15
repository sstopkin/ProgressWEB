package org.progress.web.logic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Calls")
public class Calls implements Serializable {

    private int id;
    private String objectUUID;
    private String incomingPhoneNumber;
    private Date date;
    private String description;
    private int idWorker;

    public Calls() {
    }

    public Calls(String objectUUID, Date date, String incomingPhoneNumber, String description, int idWorker) {
        this.objectUUID = objectUUID;
        this.date = date;
        this.incomingPhoneNumber = incomingPhoneNumber;
        this.description = description;
        this.idWorker = idWorker;
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

    @Column(name = "IncomingPhoneNumber")
    public String getIncomingPhoneNumber() {
        return incomingPhoneNumber;
    }

    public void setIncomingPhoneNumber(String incomingPhoneNumber) {
        this.incomingPhoneNumber = incomingPhoneNumber;
    }

    @Column(name = "idWorker")
    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "objectUUID")
    public String getObjectUUID() {
        return objectUUID;
    }

    public void setObjectUUID(String objectUUID) {
        this.objectUUID = objectUUID;
    }

    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
