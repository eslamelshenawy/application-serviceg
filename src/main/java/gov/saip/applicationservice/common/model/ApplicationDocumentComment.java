package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_document_comments")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationDocumentComment extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    @ManyToMany
    @JoinTable(name = "document_comment_attaches",
            uniqueConstraints = @UniqueConstraint(columnNames={"document_comment_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "document_comment_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> commentDocuments = new ArrayList<>();
    private long pageNumber;
    private long paragraphNumber;
    @Column(columnDefinition = "TEXT")
    private String comment;


}
