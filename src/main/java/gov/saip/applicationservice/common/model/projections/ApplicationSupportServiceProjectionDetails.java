package gov.saip.applicationservice.common.model.projections;

import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;

public interface ApplicationSupportServiceProjectionDetails<E> {
    
    E getRequest();
    
    String getTitleAr();
    
    String getTitleEn();
    
    String getOwnerNameAr();
    
    String getOwnerNameEn();
    
    String getApplicationNumber();
    
    String getOldDescription();
    
    LkTagTypeDesc getMarkTag();
    
    LkMarkType getMarkType();
    
    
    
    
    
}
