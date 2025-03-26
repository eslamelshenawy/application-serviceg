package gov.saip.applicationservice.common.service.protectionElementsMigration;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OldElement {
    int id;
    String description;
    String createdByUserId;
    Date createdDate;
    List<String> childElements;

    public OldElement(int id, String description, String createdByUserId, Date createdDate) {
        this.id = id;
        this.description = description;
        this.createdByUserId = createdByUserId;
        this.createdDate = createdDate;
        this.childElements = new ArrayList<>();
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getChildElements() {
        return childElements;
    }

    public void setChildElements(List<String> childElements) {
        this.childElements = childElements;
    }


}
