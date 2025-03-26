package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.lookup.LkSupportServiceRequestStatusMapper;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.repository.LKSupportServicesRepository;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class LKSupportServicesServiceImpl extends BaseServiceImpl<LKSupportServices, Long> implements LKSupportServicesService {

    @Value("${spring.profiles.active}")
    public String activeProfile;

    private final LKSupportServicesRepository lKSupportServicesRepository;
    private final LkSupportServiceRequestStatusMapper lkSupportServiceRequestStatusMapper;
    @Override
    protected BaseRepository<LKSupportServices, Long> getRepository() {
        return lKSupportServicesRepository;
    }

    @Override
    public LKSupportServices findByCode(SupportServiceType supportServiceType) {
        List<LKSupportServices> supportServices = lKSupportServicesRepository.findByCode(supportServiceType);
        return supportServices.isEmpty() ? null : supportServices.get(0);
    }

    @Override
    public List<LKSupportServices> findByCodeAndCategory(SupportServiceType code, String saipCode) {
        return lKSupportServicesRepository.findByCodeAndCategory(code, saipCode);
    }

    @Override
    public List<LKSupportServices> findServicesByCategoryId(Long categoryId) {

        List<String> allowedAssistivelistString  = Arrays.asList("OWNERSHIP_CHANGE",
                "LICENSING_REGISTRATION" , "RENEWAL_FEES_PAY" ,"REVOKE_PRODUCTS" , "EDIT_TRADEMARK_NAME_ADDRESS");
        List<String> allowedAssistivelistStringPatent  = Arrays.asList("EVICTION",
                "PETITION_RECOVERY" ,"PATENT_PRIORITY_REQUEST" , "PATENT_PRIORITY_MODIFY");
        List<LKSupportServices>   AllowdSupportServicesAll = new ArrayList<>();

        if ("quality".equalsIgnoreCase(activeProfile) || "production".equalsIgnoreCase(activeProfile) || "staging".equalsIgnoreCase(activeProfile) ) {
           if ( categoryId == 5){
            List<LKSupportServices>   LKSupportServicesAll=  lKSupportServicesRepository.findServicesByCategoryId(categoryId);
            for (LKSupportServices element : LKSupportServicesAll ){
                if(allowedAssistivelistString.contains(element.getCode().toString())){
                    AllowdSupportServicesAll.add(element);
                }
            }
           }else  if ( categoryId == 1){
               List<LKSupportServices>   LKSupportServicesAll=  lKSupportServicesRepository.findServicesByCategoryId(categoryId);
               for (LKSupportServices element : LKSupportServicesAll ){
                   if(allowedAssistivelistStringPatent.contains(element.getCode().toString())){
                       AllowdSupportServicesAll.add(element);
                   }
               }
           }

            return  AllowdSupportServicesAll;
        }else {
            return lKSupportServicesRepository.findServicesByCategoryId(categoryId);
        }
    }

    @Override
    public List<LKSupportServices> getServicesByIds(List<Long> ids) {
        return lKSupportServicesRepository.getServicesByIds(ids);
    }
    
    
    @Override
    public List<LkSupportServiceRequestStatusDto> getSupportServiceTypeStatuses(Long id) {
        LKSupportServices lKSupportServices = lKSupportServicesRepository.findSupportServiceTypeStatusesByRequestType(id);
        if(lKSupportServices == null || lKSupportServices.getSupportServiceRequestStatuses() == null || lKSupportServices.getSupportServiceRequestStatuses().isEmpty())
            return new ArrayList<>();
        List<LKSupportServiceRequestStatus> lkSupportServiceRequestStatuses =  lKSupportServices.getSupportServiceRequestStatuses();
        return lkSupportServiceRequestStatusMapper.map(lkSupportServiceRequestStatuses);
    }

}
