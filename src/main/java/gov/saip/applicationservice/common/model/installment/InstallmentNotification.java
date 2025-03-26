package gov.saip.applicationservice.common.model.installment;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_installment_notifications")
@Setter
@Getter
@NoArgsConstructor
public class InstallmentNotification extends BaseEntity<Long> {

    public InstallmentNotification(ApplicationInstallment applicationInstallment, InstallmentNotificationType notificationType,
                                   InstallmentNotificationStatus notificationStatus) {
        this.applicationInstallment = applicationInstallment;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_installment_id")
    private ApplicationInstallment applicationInstallment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "notification_status")
    @Enumerated(EnumType.STRING)
    private InstallmentNotificationStatus notificationStatus;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private InstallmentNotificationType notificationType;


    @Column(name = "notification_template_code")
    @Enumerated(EnumType.STRING)
    private NotificationTemplateCode notificationTemplateCode;

    private String email;
    private String mobile;
    private Long customerId;
    private String userName;
    private String exceptionMessage;

}
