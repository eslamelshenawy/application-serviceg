package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.BillReminderNotificationDetailsDto;


public interface BillReminderNotificationService {

    BillReminderNotificationDetailsDto getBillReminderDetails(Long id, String type);

}