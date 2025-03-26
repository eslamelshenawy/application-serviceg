package gov.saip.applicationservice.common.dto.Suspcion;

import gov.saip.applicationservice.util.Utilities;

public interface  TrademarkDetailsProjection {
    String getTrademarkPhoto();
    Long getTrademarkId();
    String getTrademarkNameEn();
    String getClassificationNameEn();
    String getClassificationNameAr();
    String getTrademarkNameAr();
    String getLkNexuoUserName();
    String getTmoCustomerCode();
    String getTrademarkNumber();
    Long getTrademarkTotalSubClassifications();
    default String getFileReviewUrl(){
        return  Utilities.filerServiceURL + getTrademarkPhoto() + "/" + (getLkNexuoUserName() != null ? getLkNexuoUserName() : "") + "/";
    }
}