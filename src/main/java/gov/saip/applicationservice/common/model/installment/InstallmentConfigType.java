package gov.saip.applicationservice.common.model.installment;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_installment_config_types")
@Setter
@Getter
@NoArgsConstructor
public class InstallmentConfigType extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_installment_config_id")
    private ApplicationInstallmentConfig installmentConfig;

    @Column(name = "notification_template_type")
    @Enumerated(EnumType.STRING)
    private NotificationTemplateCode notificationTemplate;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private InstallmentNotificationType notificationType;

}
