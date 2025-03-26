package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceActors;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.*;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.AGENT;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.MAIN_OWNER;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;
import static gov.saip.applicationservice.common.enums.LicenceTypeEnum.CANCEL_LICENCE;
import static gov.saip.applicationservice.common.enums.PublicationType.*;
import static gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum.DRAFT;
import static gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum.*;
import static gov.saip.applicationservice.common.enums.SupportServiceType.AGENT_SUBSTITUTION;
import static gov.saip.applicationservice.common.enums.SupportServiceType.EDIT_TRADEMARK_IMAGE;
import static gov.saip.applicationservice.common.enums.SupportServiceType.*;
import static gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum.*;
import static gov.saip.applicationservice.common.enums.customers.UserGroup.*;

public class Constants {
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DOCUMENT_TYPE = "Trademark Image/voice";
    public static final String PRIORITY_CLAIM_DAYS_CODE ="PRIORITY_CLAIM";
    public static final String PASSED = "PASSED";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String PATENT_CERTIFICATE_TEMPLATE = "patentCertificateReport";
    public static final Long PATENT_CATEGORY_ID = 1L;
    public static final String REQUEST_CORRELATION_ID = "traceId";


    public static final Map<Long, String> MAP_CATEGORY_ID_TO_ACCEPTANCE_STATUS = new HashMap<>();
    static {
        MAP_CATEGORY_ID_TO_ACCEPTANCE_STATUS.put(1L,"ACCEPTANCE");
        MAP_CATEGORY_ID_TO_ACCEPTANCE_STATUS.put(2L, "ACCEPTANCE");
        MAP_CATEGORY_ID_TO_ACCEPTANCE_STATUS.put(5L, "THE_TRADEMARK_IS_REGISTERED");
    }

    public static final List<String> tasksToBeIgnoredFromCamundaHistory = List.of(
            "UPDATE_AGENCY_REQUEST_AGSR",
            "FILE_NEW_APPLICATION"
    );

    public static final List<String> applicationInternalTasksPrefix = List.of(
            "DEPARTMENT_REPLY_TO_COORDINATOR_TMAPPREQ",
            "DEPARTMENT_REPLY_TO_HEAD_TMAPPREQ",
            "ACTION_APPROVE_ACCEPTANCE",
            "SEND_OFFICE_ACTION_CHECKER",
            "SEND_OFFICE_ACTION_2",
            "CHECK_OUTSOURCE_CLASSIFICATION",
            "CONFIRM_TEMPORARY_CLASSIFCATION",
            "REVIEW_CLASSIFICATION",
            "COMMITTEE_SUBSTANTIVE_EXAMINATION",
            "SEND_OFFICE_ACTION_SUB",
            "SEND_OFFICE_ACTION_AFTER_CORRECTION_1",
            "SEND_OFFICE_ACTION_AFTER_CORRECTION_2",
            "PUBLICATION_AUDITOR",
            "ASSIGN_COMMTTIEE_MEMBERS",
            "COMMITTEE_SUBSTANTIVE_EXAMINATION",
            "PENDING_FOR_APPEAL_REQUEST_1",
            "SUBSTANTIVE_EXAMINATION",
            "HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
            "SECOND_HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
            "THIRD_HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
            "CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
            "SECOND_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
            "THIRD_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC",
            "REASSIGNATION"
    );
    public static final List<String> applicationTasksHasUnderProcedureStatus = List.of(
            "LOG_DECISION_COORDINATOR_REVIEW_REQUEST_TMAPPREQ",
            "LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ",
            "APP_REJ_REVIEW_AUTOMATED_CHECK",
            "REVIEW_CHECKER_1",
            "REVIEW_CHECKER_2",
            "EXAM_SUPPLEMENT_STATMENT_1",
            "EXAM_SUPPLEMENT_STATMENT_2",
            "PUBLICATION_SPECIALIST",
            // support services -> application_edit_tm_image_process
            "LOG_DECISION_PREFIX_SUBSTANTIVE_EXAMINATION_AETIM",
            "LOG_DECISION_PREFIX_REVIEW_REQUEST_DOCUMENTS_AETIM",
            "LOG_DECISION_PREFIX_SEND_OFFICE_ACTION_SUB_AETIM"
    );
    public static final List<String> taskDefinitionKeysOfOurActivityLogs = List.of(
            "PAIED_REGESITRATION_INVOICE",
            "EXAMINER_REPORT_2",
            "EXAMINER_REPORT_1",
            "FIRST_NOTICE_OF_FORMAL_CHECKER_REPORT",
            "SECOND_NOTICE_OF_FORMAL_CHECKER_REPORT",
            "DROPPED_REPORT",
            "OBJECTION_REPORT",
            "FILE_NEW_APPLICATION"
    );
    public static final Map<String, String> mapApplicationTasksNames = new HashMap<>();
    static{
        mapApplicationTasksNames.put("GRANTS_FEE","دفع فاتورة التسجيل");
        mapApplicationTasksNames.put("APPEAL_PROCESS_RESULT","متظلم لدى لجنة التظلمات");
        mapApplicationTasksNames.put("APP_REJ_AMED_APP_1","الرد علي تقرير الفحص الشكلي 1");
        mapApplicationTasksNames.put("APP_REJ_AMED_APP_2","الرد علي تقرير الفحص الشكلي 2");
        mapApplicationTasksNames.put("SUBMIT_SUPPLEMENT_STATMENT_PAT","الرد علي تقرير الفحص الموضوعي 1");
        mapApplicationTasksNames.put("SUBMIT_SECOND_SUPPLEMENT_STATMENT_PAT","الرد علي تقرير الفحص الموضوعي 2");
        mapApplicationTasksNames.put("APPLICATION_CORRECTION_EXTERNALUSERTASK_IC","إستكمال بيانات");
        mapApplicationTasksNames.put("SECOND_APPLICATION_CORRECTION_EXTERNALUSERTASK_IC","إستكمال بيانات");
        mapApplicationTasksNames.put("PAY_PUBLICATION_GRANT_FEES_EXTERNALUSERTASK_IC","دفع فاتورة التسجيل والنشر");
    }
    public static final Map<String, String> mapApplicationTasksToItsSendBackActions = new HashMap<>();
    static{
        mapApplicationTasksToItsSendBackActions.put("APP_REJ_REVIEW_AUTOMATED_CHECK", "EDIT,UPDATE");
        mapApplicationTasksToItsSendBackActions.put("LOG_DECISION_COORDINATOR_REVIEW_REQUEST_TMAPPREQ", "SEND_BACK");
        mapApplicationTasksToItsSendBackActions.put("LOG_DECISION_HEAD_REVIEW_REQUEST_TMAPPREQ", "SEND_BACK");
        mapApplicationTasksToItsSendBackActions.put("REVIEW_CHECKER_1", "EDIT,NO");
        mapApplicationTasksToItsSendBackActions.put("REVIEW_CHECKER_2", "EDIT");
        mapApplicationTasksToItsSendBackActions.put("ACTION_APPROVE_ACCEPTANCE_TM", "UPDATE,OBJECTION");
        mapApplicationTasksToItsSendBackActions.put("ACTION_APPROVE_ACCEPTANCE_PAT", "UPDATE,YES");
        mapApplicationTasksToItsSendBackActions.put("SEND_OFFICE_ACTION_CHECKER", "YES");
        mapApplicationTasksToItsSendBackActions.put("SEND_OFFICE_ACTION_2", "YES");
        mapApplicationTasksToItsSendBackActions.put("APPROVE_OBJECTION", "NO");
        mapApplicationTasksToItsSendBackActions.put("REVIEW_FAST_TRACK_0", "YES");
        mapApplicationTasksToItsSendBackActions.put("REVIEW_FAST_TRACK_1", "YES");
        mapApplicationTasksToItsSendBackActions.put("REVIEW_FAST_TRACK_2", "YES");
        mapApplicationTasksToItsSendBackActions.put("EXAM_SUPPLEMENT_STATMENT_1", "UPDATE");
        mapApplicationTasksToItsSendBackActions.put("SEND_OFFICE_ACTION_SUB", "UPDATE,GRANT,GRANT_CONDITION,OBJECTION");
        mapApplicationTasksToItsSendBackActions.put("SEND_OFFICE_ACTION_AFTER_CORRECTION_1", "OBJECTION,GRANT");
        mapApplicationTasksToItsSendBackActions.put("SEND_OFFICE_ACTION_AFTER_CORRECTION_2", "GRANT,OBJECTION");
        mapApplicationTasksToItsSendBackActions.put("COMMITTEE_SUBSTANTIVE_EXAMINATION", "OBJECTION,GRANT");

        mapApplicationTasksToItsSendBackActions.put("HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC", "UPDATE,YES,NO");
        mapApplicationTasksToItsSendBackActions.put("SECOND_HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC", "UPDATE,YES,NO");
        mapApplicationTasksToItsSendBackActions.put("THIRD_HEAD_OF_CHECKER_FORMAL_EXAMINATION_INTERNALUSERTASK_IC", "YES,NO");

        // support services -> application_edit_tm_image_process

        mapApplicationTasksToItsSendBackActions.put("LOG_DECISION_PREFIX_REVIEW_REQUEST_DOCUMENTS_AETIM", "SEND_BACK,APPROVE_MODIFICATION");
        mapApplicationTasksToItsSendBackActions.put("LOG_DECISION_PREFIX_SEND_OFFICE_ACTION_SUB_AETIM", "SEND_BACK,EXAMINER_APPROVAL,EXAMINER_REJECTION");

    }

    public static final Map<CertificateTypeEnum, List<ApplicationCustomerType>> CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE = new HashMap<>();
    static {
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(ISSUE_CERTIFICATE, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(PATENT_ISSUE_CERTIFICATE, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(SECRET_DESIGN_DOCUMENT, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(PROOF_ISSUANCE_APPLICATION, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(CERTIFIED_REGISTER_COPY, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(FINAL_SPECIFICATION_DOCUMENT, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(CERTIFIED_PRIORITY_COPY, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(EXACT_COPY, List.of(MAIN_OWNER, AGENT));
        CUSTOMER_TYPES_VALID_FOR_CERTIFICATES_BY_TYPE.put(REVOKE_VOLUNTARY_TRADEMARK_CERTIFICATE, List.of(MAIN_OWNER, AGENT));
        
        
    }

    public static enum SupportServiceValidationMapKeys {
        ACTORS, PARENT_SERVICE_VALID_STATUSES
    }
    @AllArgsConstructor
    @Getter

    public  enum tradeMarkRelease {
        EDIT("TradeMark Release");

        private String actionNote;
    }

    // release
    public static final String DEFAULT_SORT_COLUMN = "id";
    public static final String SAUDI_ARABIA_TIMEZONE = "Asia/Riyadh";
    public static final String MODIFIED_PUBLICATION_DATE = "Publication Date Modified";
    
    public static final String DECISION = "decision";
    public static final String EQM = "eqm";
    public static final String EQM_OPPOSITION = "eqm_opposition";

    public static final String AppName = "application-service";
    public static final String SERVICE_TYPE = "EXTERNAL-SERVICE";
    public static final String USER_NAME_PREFIX = "sap";
    public static final List<String> EXAMINER_STATUSES = List.of("UNDER_OBJECTIVE_PROCESS");
    
    public static final Map<String, List<String>> MAP_EQM_APPLICATION_STATUSES = new HashMap<>();
    
    static {
        MAP_EQM_APPLICATION_STATUSES.put(DECISION, List.of("IN_COMMITTEE"));
        MAP_EQM_APPLICATION_STATUSES.put(EQM, List.of("ACCEPTANCE", "FORMAL_REJECTION", "OBJECTIVE_REJECTION"));
        MAP_EQM_APPLICATION_STATUSES.put(EQM_OPPOSITION, List.of("OBJECTED"));
    }
    
    public static final Map<String, String> FREE_CERTIFICATES_MAP_WITH_DOCUMENTS_TYPES = new HashMap<>();
    static {
        FREE_CERTIFICATES_MAP_WITH_DOCUMENTS_TYPES.put(REVOKE_VOLUNTARY_TRADEMARK_CERTIFICATE.name(), "Revoke Voluntary Trademark Certificate");
    }
    
    public static final Map<String, String> MAP_SUPPORT_SERVICES_TO_PUBLICATION_TYPE = new HashMap<>();
    
    static {
        MAP_SUPPORT_SERVICES_TO_PUBLICATION_TYPE.put(CANCEL_LICENCE.name(), LICENCE_CANCELLATION.name());
    }



    
    public static final Map<String, String> MAP_CATEGORY_TO_LICENCE_USE_PUBLICATION_TYPE = new HashMap<>();
    
    static {
        MAP_CATEGORY_TO_LICENCE_USE_PUBLICATION_TYPE.put(TRADEMARK.name(), TRADEMARK_LICENCE_USE.name());
        MAP_CATEGORY_TO_LICENCE_USE_PUBLICATION_TYPE.put(PATENT.name(), PATENT_LICENCE_USE.name());
        MAP_CATEGORY_TO_LICENCE_USE_PUBLICATION_TYPE.put(INDUSTRIAL_DESIGN.name(), INDUSTRIAL_DESIGN_LICENCE_USE.name());
        
    }
    
    public static final List<String> GAZETTE_STATUSES = List.of(ACCEPTANCE.name(), THE_TRADEMARK_IS_REGISTERED.name(), REVOKED_PURSUANT_TO_COURT_RULING.name(), CROSSED_OUT_MARK.name());
    
    public static final List<String> USERS_GROUPS_HAVE_ESTABLISHMENT_ID = List.of(GOVERNMENT_AGENCY.name(), GOVERNMENT_AGENCY.name(), LEGAL_REPRESENTATIVE.name());
    public static final List<String> USERS_GROUPS_HAVE_IQAMA_ID = List.of(NATURAL_PERSON_WITH_NATIONALITY.name());
    public static final List<String> USERS_GROUPS_HAVENT_ACCESS_TO_EDIT_NAME_ADDRESS_AS_MAIN_OWNER = List.of(FOREIGN_CORPORATION.name(), NATURAL_PERSON_WITH_FOREIGN_NATIONALITY.name());
    
    public static final List<String> PUBLICATION_STATUSES = List.of("PUBLISHED_ELECTRONICALLY", "OBJECTOR");

    public static final List<String> VERIFICATION_PUBLICATION_ALLOWED_STATUS = List.of(
            AWAITING_FOR_UPDATE_XML.name(),
            PUBLISHED_ELECTRONICALLY.name(),
            AWAITING_VERIFICATION.name());

    public static final List<String> SUPPORT_SERVICES_HAVE_TASKS = List.of(LICENSING_REGISTRATION.name(),
            LICENSING_MODIFICATION.name(), OWNERSHIP_CHANGE.name(), AGENT_SUBSTITUTION.name(), EDIT_TRADEMARK_NAME_ADDRESS.name(), EDIT_TRADEMARK_IMAGE.name());
    
    
    public static final class TemplateType {
        private TemplateType() {

        }
        public static final String SMS = "sms.txt";
        public static final String REQUEST_REJECT ="request-rejected.html";
        public static final String REQUEST_SEND = "request-send.html";
        public static final String REQUEST_PENDING ="request-pending.html";


    }

    public static final class MessageType {
        private MessageType() {

        }

        public static final String EMAIL_TYPE_MESSAGE = "EMAIL";
        public static final String SMS_TYPE_MESSAGE = "SMS";

    }

    public static final String FILE_SIZE_EXCEEDED = "FILE_SIZE_EXCEEDED";

    public static final class ErrorKeys {
        public static  final String ALLOWANCE_DAYS_FOUND="ALLOWANCE_DAYS_FOUND";
        public static  final String TIME_OUT ="TIME_OUT";
        public static  final String LICENSE_IS_EXPIRED ="LICENSE_IS_EXPIRED";
        public static  final String FILLING_DATE_NOT_FOUND="FILLING_DATE_NOT_FOUND";
        public static  final String SSR_UNDER_REVIEW="SSR_UNDER_REVIEW";
        public static  final String SSR_REJECTED="SSR_REJECTED";
        public static  final String SSR_MODIFICATION_REJECTED="SSR_MODIFICATION_REJECTED";
        public static  final String SSR_NOT_FOUND="SSR_NOT_FOUND";
        public static  final String SSR_STATUS_NOT_VALID="SSR_STATUS_NOT_VALID";
        public static final String ADD_EXIST_CUSTOMER_CODE = "ADD_EXIST_CUSTOMER_CODE";
        public static final String EXTENSION_APPLIED = "EXTENSION_APPLIED";
        public static final String REVOKE_LICENSE_WITH_MAIN_OWNER_AND_AGENT="REVOKE_LICENSE_WITH_MAIN_OWNER_AND_AGENT";
        public static  final String INTERNAL_SERVER_ERROR="INTERNAL_SERVER_ERROR";
        public static final String MAIN_APPLICATION_STATUS_NOT_VALID = "MAIN_APPLICATION_STATUS_NOT_VALID";
        public static final String PRIORITY_DOCUMENTS_CONFIRMED = "PRIORITY_DOCUMENTS_CONFIRMED";
        public static final String TRADEMARK_TRANSFER_OWNERSHIP_FAIL = "TRADEMARK_TRANSFER_OWNERSHIP_FAIL";

        public static final String AGENCY_EXCEEDED_REQUESTS_THIS_YEAR = "AGENCY_EXCEEDED_REQUESTS_THIS_YEAR";

        public static final String COMMON_END_DATE_BEFORE_START_DATE = "COMMON_END_DATE_BEFORE_START_DATE";
        public static final String AGENCY_MAX_EXCEEDED_MAX_YEARS = "AGENCY_MAX_EXCEEDED_MAX_YEARS";
        public static final String AGENCY_SERVICES_CONTAINS_SERVICES_NOT_ALLOWED_FOR_NON_OWNER = "AGENCY_SERVICES_CONTAINS_SERVICES_NOT_ALLOWED_FOR_NON_OWNER";
        public static final String VALIDATION_NO_AGENCY_VALID_TO_REGISTER = "VALIDATION_NO_AGENCY_VALID_TO_REGISTER";
        public static final String SERVICE_NOT_AVAILABLE_TO_APP_OWNER = "SERVICE_NOT_AVAILABLE_TO_APP_OWNER";
        public static final String VALIDATION_APPLICANT_TYPE_IS_NOT_CORRECT = "VALIDATION_APPLICANT_TYPE_IS_NOT_CORRECT";

        public static final String APPLICATION_OWNER_CANT_BE_LICENSED_CUSTOMER = "APPLICATION_OWNER_CANT_BE_LICENSED_CUSTOMER";
        private ErrorKeys() {}
        public static final String PUBLICATION_SCHEDULING_CONFIG_NOT_FOUND = "PUBLICATION_SCHEDULING_CONFIG_NOT_FOUND";
        public static final String APPLICATION_ID_NOT_FOUND = "APPLICATION_ID_NOT_FOUND";
        public static final String ERROR_UNAUTHORIZED = "ERROR_UNAUTHORIZED";
        public static final String APP_ID_DOESNT_EXIST = "APP_ID_DOESNT_EXIST";
        public static final String APPLICATION_NOT_MEET_SERVICE_CRITERIA = "APPLICATION_NOT_MEET_SERVICE_CRITERIA";
        public static final String IDENTIFIER_REQUIRED = "IDENTIFIER_REQUIRED";
        public static final String EXCEPTION_RECORD_NOT_FOUND = "EXCEPTION_RECORD_NOT_FOUND";
        public static final String VALIDATION_CANNOT_APPEAL = "VALIDATION_CANNOT_APPEAL";
        public static final String APPLICATION_NUMBER_NOT_FOUND_FOR_THIS_CATEGORY = "APPLICATION_NUMBER_NOT_FOUND_FOR_THIS_CATEGORY";
        public static final String CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_LICESE_NUMBER = "CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_LICESE_NUMBER";
        public static final String CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_REQUEST_NUMBER= "CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_REQUEST_NUMBER";
        public static final String LICENSE_NUMBER_NOT_FOUND = "LICENSE_NUMBER_NOT_FOUND";
        public static final String APPLICANT_APPLY_FOR_NEW_REQUEST_AND_HAS_UNDER_PROCEDURE_REQUEST = "APPLICANT_APPLY_FOR_NEW_REQUEST_AND_HAS_UNDER_PROCEDURE_REQUEST";
        public static final String EXCEPTION_EXPLAIN_BLOCK_REASON_ِRENEWAL="EXCEPTION_EXPLAIN_BLOCK_REASON_ِRENEWAL";
        public static final String EXCEPTION_EXPLAIN_BLOCK_ِRENEWAL= "EXCEPTION_EXPLAIN_BLOCK_ِRENEWAL_TM";
        public static final String EXCEPTION_EXPLAIN_BLOCK_RENEWAL_INDUSTRIAL="EXCEPTION_EXPLAIN_BLOCK_RENEWAL_INDUSTRIAL";
        public static final String EXCEPTION_APPLICATION_CATEGORIES_CHANGED="EXCEPTION_APPLICATION_CATEGORIES_CHANGED";
        public static final String EXCEPTION_APPLICATION_STATUS_CHANGED="EXCEPTION_APPLICATION_STATUS_CHANGED";
        public static final String APP_INFO_ALREADY_EXISTS = "APP_INFO_ALREADY_EXISTS";
        public static final String Priority_Expired = "Priority_Expired";
        public static final String IDENTIFIER_NOT_FOUND = "IDENTIFIER_DUPLICATED";
        public static final String COMMON_DATA_NOT_FOUND = "COMMON_DATA_NOT_FOUND";
        public static final String COMMON_CODE_IS_DUPLICATED = "COMMON_CODE_IS_DUPLICATED";
        public static final String FILE_SIZE_EXCEEDED = "FILE_SIZE_EXCEEDED";
        public static final String FILE_EXTENSION_NOT_VALID = "FILE_EXTENSION_NOT_VALID";
        public static final String GENERAL_ERROR_MESSAGE = "GENERAL_ERROR_MESSAGE";
        public static final String DOCUMENT_ID_NOT_FOUND = "DOCUMENT_ID_NOT_FOUND";
        public static final String SOME_OF_CODES_NOT_RETURNED = "SOME_OF_CODES_NOT_RETURNED";
        public static final String CLASSIFICATION_NOT_FOUND = "CLASSIFICATION_NOT_FOUND";
        public static final String INCORRECT_DATE = "INCORRECT_DATE";
        public static final String AGENT_ALREADY_EXISTS = "AGENT_ALREADY_EXISTS";
        public static final String YOU_DONT_HAVE_ACCESS_IN_THIS_SUPPORT_SERVICE = "YOU_DONT_HAVE_ACCESS_IN_THIS_SUPPORT_SERVICE";
        public static final String VALIDATION_USER_ID_NOT_FOUND = "VALIDATION_USER_ID_NOT_FOUND";
        public static final String VALIDATION_MOBILE_CODE_NOT_FOUND = "VALIDATION_MOBILE_CODE_NOT_FOUND";
        public static final String VALIDATION_MOBILE_NUMBER_NOT_FOUND = "VALIDATION_MOBILE_NUMBER_NOT_FOUND";
        public static final String FILLING_DATE_GR_REQUIRED = "FILLING_DATE_GR_REQUIRED";
        public static final String PATENT_DETAILS_NOT_FOUND = "PATENT_DETAILS_NOT_FOUND";
        public static final String PCT_NOT_FOUND = "PCT_NOT_FOUND";
        public static final String PCT_DATE_NOT_CORRECT = "PCT_DATE_NOT_CORRECT";
        public static final String PCT_PETITION_NOT_CORRECT = "PCT_PETITION_NOT_CORRECT";
        public static final String PITITION_NUMBER_EXISTING = "PITITION_NUMBER_EXISTING";
        public static final String PITITION_NUMBER_NOT_EXISTING = "PITITION_NUMBER_NOT_EXISTING";
        public static final String APPLICATION_ID_DUPLICATED = "APPLICATION_ID_DUPLICATED";
        public static final String PATENT_ID_DUPLICATED = "PATENT_ID_DUPLICATED";
        public static final String EXACTLY_ONE_MAIN_ATTACHMENT_REQUIRED = "EXACTLY_ONE_MAIN_ATTACHMENT_REQUIRED";
        public static final String INDUSTRIAL_DESIGN_DETAILS_NOT_FOUND = "INDUSTRIAL_DESIGN_DETAILS_NOT_FOUND";
        public static final String NUMBER_OF_ATTACHMENT_EXCEEDED_ATTACHE_ONLY_SEVEN = "NUMBER_OF_ATTACHMENT_EXCEEDED_ATTACHE_ONLY_SEVEN";
        public static final String GENERATING_APPLICATION_INFO_XML_ERROR = "GENERATING_APPLICATION_INFO_XML_ERROR";
        public static final String READING_DISK_FILE_ERROR = "READING_DISK_FILE_ERROR";
        public static final String GENERATING_JASPER_REPORT_ERROR = "GENERATING_JASPER_REPORT_ERROR";
        public static final String EXCEPTION_NO_COMMENTS_FOUND_FOR_THIS_APPLICATION_SUPPORT_SERVICE_TYPE = "EXCEPTION_NO_COMMENTS_FOUND_FOR_THIS_APPLICATION_SUPPORT_SERVICE_TYPE";
        public static final String APPLICATION_CATEGORY_IS_NOT_VALID = "APPLICATION_CATEGORY_IS_NOT_VALID";
        public static final String VALIDATION_SUPPORT_SERVICE_ACTOR = "VALIDATION_SUPPORT_SERVICE_ACTOR";
        public static final String SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR = "SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR_";
        public static final String SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR_DEFAULT = "SUPPORT_SERVICE_SEARCH_APPLICATION_VALIDATION_SUPPORT_SERVICE_ACTOR_DEFAULT";
        public static final String APPLICATION_STATUS_IS_NOT_VALID = "APPLICATION_STATUS_IS_NOT_VALID";
        public static final String SERVICE_CANNOT_BE_USED = "SERVICE_CANNOT_BE_USED";
        public static final String APPLICATION_ALREADY_ASSIGNED_TO_HEAD_OF_CHECKER = "APPLICATION_ALREADY_ASSIGNED_TO_HEAD_OF_CHECKER";
        public static final String APPLICATION_HAS_UNDER_PROCEDURE_REQUEST = "APPLICATION_HAS_UNDER_PROCEDURE_REQUEST";
        public static final String TRADEMARK_NOT_DELETED = "TRADEMARK_NOT_DELETED";
        public static final String RENEWAL_FEE_NOT_DUE= "RENEWAL_FEE_NOT_DUE";
        public static final String RENEWAL_FEE_EXPIRED= "RENEWAL_FEE_EXPIRED";
        public static final String VALIDATION_INSTALLMENT_NOT_FOUND= "VALIDATION.INSTALLMENT.NOT_FOUND";
        public static final String VALIDATION_INSTALLMENT_CAN_NOT_POSTPONED = "VALIDATION.INSTALLMENT.CAN.NOT.POSTPONED";
        public static final String INVALID_CERTIFICATE= "INVALID_CERTIFICATE";

        public static final String CERTIFICATE_NOT_FOUND= "CERTIFICATE_NOT_FOUND";
        public static final String INVALID_SUPPORT_SERVICE = "INVALID_SUPPORT_SERVICE";
        public static final String GENERAL_DO_NOT_HAVE_PERMISSION = "GENERAL_DO_NOT_HAVE_PERMISSION";
        public static final String APPLICATION_END_OF_PROTECTION_DATE_PAST = "APPLICATION_END_OF_PROTECTION_DATE_PAST";
        public static final String MAIN_DRAW_IS_EXISTS = "MAIN_DRAW_IS_EXISTS";
        public static final String REQUEST_STATUS_IS_NOT_VALID = "REQUEST_STATUS_IS_NOT_VALID";
        public static final String SIMILAR_APPLICATION_NOT_EXISTED = "SIMILAR_APPLICATION_NOT_EXISTED";
        public static final String IDENTIFIER_NOT_FOUND_PLT = "IDENTIFIER_NOT_FOUND_PLT";
        public static final String PCT_DOCUMENT_NOT_FOUND = "PCT_DOCUMENT_NOT_FOUND";
        public static final String PATENT_PLT_DATA_INVALID = "PATENT_PLT_DATA_INVALID";
        public static final String PRIORITIES_DOCUMENTS_NOT_FOUND = "PRIORITIES_DOCUMENTS_NOT_FOUND";
        public static final String INVALID_CLASSIFICATION_ARABIC_DATA = "INVALID_CLASSIFICATION_ARABIC_DATA";
        public static final String DOCUMENT_NOT_UPLOADED_TO_NEXUO = "DOCUMENT_NOT_UPLOADED_TO_NEXUO";
        public static final String CLASSIFICATION_UNIT_CANNOT_BE_DELETED = "CLASSIFICATION_UNIT_CANNOT_BE_DELETED";
        
        public static final String DATE_FORMAT_EXCEPTION = "DATE_FORMAT_EXCEPTION";
        public static final String AGENCY_SERVICES_CONTAINS_SERVICES_NOT_ALLOWED_FOR_OWNER = "AGENCY_SERVICES_CONTAINS_SERVICES_NOT_ALLOWED_FOR_OWNER";

        public static final String APPLICATION_ALREADY_ASSIGNED_ON_AGENT= "APPLICATION_ALREADY_ASSIGNED_ON_AGENT";
        public static final String DONT_ABLE_DELETE_THIS_UNIT= "DONT_ABLE_DELETE_THIS_UNIT";

        public static final String COMMON_START_DATE_EXCEEDED_MAX_YEARS_IN_PAST = "COMMON_START_DATE_EXCEEDED_MAX_YEARS_IN_PAST";
        public static final String COMMON_START_DATE_EQUAL_END_DATE = "COMMON_START_DATE_EQUAL_END_DATE";
        public static final String COMMERCIAL_EXPLOITATION_DATE = "COMMERCIAL_EXPLOITATION_DATE";
        public static final String NULL_COUNTRY_ID = "NULL_COUNTRY_ID";
        public static final String NULL_APPLICANT_INVENTOR = "NULL_APPLICANT_INVENTOR";
        public static final String LK_NOTES_ID = "CAN_NOT_DELETE_NOTE_RELATED_TO_APPLICATION_SECTION_NOTES";
        public static final String EXCEPTION_CANNOT_APPLY_FOR_RETRACTION_SERVICE = "EXCEPTION_CANNOT_APPLY_FOR_RETRACTION_SERVICE";
        public static final String APPEAL_REQUEST_VALIDATION = "APPEAL_REQUEST_VALIDATION";
    }

    public static final class SuccessMessages {
        private SuccessMessages() {

        }

        public static final String ATTORNEY_VALID_DATE_MESSAGE_ENGLISH = "Valid attorney number";
        public static final String ATTORNEY_VALID_DATE_MESSAGE_ARABIC = "تم التأكد من رقم التفويض";
        public static final String ATTORNEY_EXPIRED_DATE_MESSAGE_ENGLISH = "Expired attorney number";
        public static final String ATTORNEY_EXPIRED_DATE_MESSAGE_ARABIC = "رقم التوفيض منتهي الصلاحية";
    }

    public static final class AttorneyMessages {
        private AttorneyMessages() {

        }

        public static final Map<String, String> map = new HashMap<>();

        static {
            map.put("MOJINT1001", "Success");
            map.put("MOJINT1002", "Success With No Data");
            map.put("MOJINT1003", "Success with Comments");
            map.put("MOJINT2001", "Input Validation Issue");
            map.put("MOJINT2002", "Retrieval Input Validation Issue");
            map.put("MOJINT2003", "Header Input Validation Issue");
            map.put("MOJINT2004", "Conflict Business Input Validation Issue");
            map.put("MOJINT3001", "Security Failure");
            map.put("MOJINT3002", "Authentication Failure");
            map.put("MOJINT3003", "Authorization Issue");
            map.put("MOJINT4001", "Failure in Retrieval");
            map.put("MOJINT4002", "Failure in DB Save");
            map.put("MOJINT4003", "Failure of web service call");
            map.put("MOJINT5001", "Exception");
            map.put("MOJINT5002", "Internal System Exception");
            map.put("MOJINT7001", "Internal Issue");
            map.put("MOJINT7002", "Internal service Unresponsive");
        }
    }
    public static final class SupportServicesApplicationListing {
        private SupportServicesApplicationListing() {

        }
        // TODo  to use supportServiceCode As Key And Get All Valid Status
        public static final Map<String, Map<String,List<String> > > supportServiceCodesApplicationCategoriesStatus = new HashMap<>();
        public static final List<String> supportServicesCodes = new ArrayList<>();
        public static final Map<String, List<String>  > innerMapREVOKE_BY_COURT_ORDER = new HashMap<>();
        public static final Map<String, List<String>  > innerMapEXTENSION = new HashMap<>();
        public static final Map<String, List<String>  > innerMapEVICTION = new HashMap<>();
        public static final Map<String, List<String>  > innerMapRETRACTION = new HashMap<>();
        public static final Map<String, List<String>  > innerMapOPPOSITION = new HashMap<>();
        public static final Map<String, List<String>  > innerMapINITIAL_MODIFICATION = new HashMap<>();
        public static final Map<String, List<String>  > innerMapINITIAL_PETITION_RECOVERY = new HashMap<>();
        public static final Map<String, List<String>  > innerMapLICENSING_MODIFICATION = new HashMap<>();

        public static final Map<String, List<String>  > innerMapLICENSING_REGISTRATION = new HashMap<>();
        public static final Map<String, List<String>  > innerMapAPPEAL = new HashMap<>();
        public static final Map<String, List<String>  > innerMapOWNERSHIPCHANGE = new HashMap<>();
        public static final Map<String, List<String>  > innerMapPROTECTIONEXTEND = new HashMap<>();
        public static final Map<String, List<String>  > innerMapRENEWALFEESPAY = new HashMap<>();
        public static final Map<String, List<String>  > innerMapANNUALFEESPAY = new HashMap<>();

        public static final Map<String, List<String>  > innerMapREVOKERODUCTS = new HashMap<>();
        public static final Map<String , List<String> > innerMapREVOKEVOLUNTRY = new HashMap<>();
  public static final Map<String , List<String> > innerMapTRADEMARKAPPLICATIONSEARCH = new HashMap<>();
  public static final Map<String , List<String> > innerMapREVOKELICENCEREQUEST = new HashMap<>();
  public static final Map<String , List<String> > innerMapOPPOSITIONREVOKELICENCEREQUEST = new HashMap<>();

        public static final Map<String , List<String> > innerMapEDITTRADEMARKNAMEADDRESS = new HashMap<>();
        
        public static final Map<String , List<String> > INNER_MAP_EDIT_TRADEMARK_IMAGE = new HashMap<>();
        
        public static final Map<String, List<String>>  INNER_MAP_TM_APPEAL_REQUEST = new HashMap<>();

        public static final Map<String, List<String>>  innerMapPETITIONREQUESTNATIONALSTAGE = new HashMap<>();

        public static final Map<String, String  > categoryToDesEn = new HashMap<>();
        public static final Map<String, String  > descEnToCategory = new HashMap<>();

        public static final Map<String, List<String>> INNER_MAP_PATENT_PRIORITY_REQUEST = new HashMap<>();
        public static final Map<String, List<String>> INNER_MAP_PATENT_PRIORITY_MODIFY = new HashMap<>();
        public static final Map<String, List<String>> INNER_MAP_AGENT_SUBSTITUTION = new HashMap<>();



        static {

            descEnToCategory.put("Patent","PATENT");
            descEnToCategory.put("Industrial Design","INDUSTRIAL_DESIGN");
            descEnToCategory.put("Trademark","TRADEMARK");

            categoryToDesEn.put("PATENT","Patent") ;
            categoryToDesEn.put("INDUSTRIAL_DESIGN","Industrial Design") ;
            categoryToDesEn.put("TRADEMARK","Trademark") ;
/************************************************************************************************************************************/
            INNER_MAP_PATENT_PRIORITY_REQUEST.put("STATUS", List.of(
                    ApplicationStatusEnum.DRAFT.name(),
                    ApplicationStatusEnum.NEW.name(),
                    ApplicationStatusEnum.WAITING_FOR_APPLICATION_FEE_PAYMENT.name(),
                    ApplicationStatusEnum.ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.name(),
                    ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name(),
                    ApplicationStatusEnum.UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL.name(),
                    ApplicationStatusEnum.RETURNED_TO_THE_CLASSIFICATION_OFFICER.name(),
                    ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name(),
                    UNDER_OBJECTIVE_PROCESS.name(),
                    UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR.name(),
                    UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.name(),
                    UNDER_ACCELERATED_TRACK_STUDY.name(),
                    DENIED_AS_EXPEDITED_APPLICATION.name(),
                    PENDING_PAYMENT_OF_ACCELERATED_TRACK_PUBLICATION_FEE.name(),
                    RESPOND_TO_THE_FORMAL_EXAMINATION_REPORT.name(),
                    PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS.name(),
                    SUBMIT_THE_COSTS_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION.name(),
                    RETURN_TO_THE_FORMAL_EXAMINER.name(),
                    UNDER_CLASSIFICATION.name(),
                    RETURNED_TO_THE_APPLICANT.name(),
                    SEND_FORMAL_EXAMINATION_REPORT.name()
                    ));
            INNER_MAP_PATENT_PRIORITY_REQUEST.put("CATEGORY",List.of(PATENT.toString()));

            INNER_MAP_PATENT_PRIORITY_REQUEST.put(SupportServiceValidationMapKeys.ACTORS.name(), List.of(
                    SupportServiceActors.MAIN_OWNER.name(),
                    SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(PATENT_PRIORITY_REQUEST.toString(), INNER_MAP_PATENT_PRIORITY_REQUEST);
/************************************************************************************************************************************/
/************************************************************************************************************************************/
            INNER_MAP_PATENT_PRIORITY_MODIFY.put("STATUS", List.of(
                    ApplicationStatusEnum.DRAFT.name(),
                    ApplicationStatusEnum.NEW.name(),
                    ApplicationStatusEnum.WAITING_FOR_APPLICATION_FEE_PAYMENT.name(),
                    ApplicationStatusEnum.ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.name(),
                    ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name(),
                    ApplicationStatusEnum.UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL.name(),
                    ApplicationStatusEnum.RETURNED_TO_THE_CLASSIFICATION_OFFICER.name(),
                    ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name(),
                    UNDER_FORMAL_PROCESS.name(),
                    UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.name(),
                    UNDER_ACCELERATED_TRACK_STUDY.name(),
                    DENIED_AS_EXPEDITED_APPLICATION.name(),
                    PENDING_PAYMENT_OF_ACCELERATED_TRACK_PUBLICATION_FEE.name(),
                    RESPOND_TO_THE_FORMAL_EXAMINATION_REPORT.name(),
                    RETURN_TO_THE_FORMAL_EXAMINER.name(),
                    PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS.name(),
                    UNDER_CLASSIFICATION.name(),
                    SEND_FORMAL_EXAMINATION_REPORT.name(),
                    REQUEST_CORRECTION.name(),
                    RETURNED_TO_THE_APPLICANT.name()
            ));
            INNER_MAP_PATENT_PRIORITY_MODIFY.put("CATEGORY",List.of(PATENT.toString()));

            INNER_MAP_PATENT_PRIORITY_MODIFY.put(SupportServiceValidationMapKeys.ACTORS.name(), List.of(
                    SupportServiceActors.MAIN_OWNER.name(),
                    SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(PATENT_PRIORITY_MODIFY.toString(), INNER_MAP_PATENT_PRIORITY_MODIFY);
/************************************************************************************************************************************/

            innerMapREVOKE_BY_COURT_ORDER.put("STATUS",List.of(
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name()

            ));
            innerMapREVOKE_BY_COURT_ORDER.put("CATEGORY",List.of(
                    TRADEMARK.toString()

            ));

            innerMapREVOKE_BY_COURT_ORDER.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.OTHER.name()
            ));

            supportServiceCodesApplicationCategoriesStatus.put(REVOKE_BY_COURT_ORDER.toString(),innerMapREVOKE_BY_COURT_ORDER);

            INNER_MAP_AGENT_SUBSTITUTION.put("CATEGORY",List.of(PATENT.toString(), TRADEMARK.toString(), INDUSTRIAL_DESIGN.toString()));
            supportServiceCodesApplicationCategoriesStatus.put(AGENT_SUBSTITUTION.toString(),INNER_MAP_AGENT_SUBSTITUTION);

            innerMapEXTENSION.put("STATUS",List.of(
                    UNDER_OBJECTIVE_PROCESS.toString(),
                    WAITING_FOR_APPLICATION_FEE_PAYMENT.toString(),
                    PAYMENT_OF_MODIFICATION_FEES_IS_PENDING.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE.toString(),
                    UNDER_FORMAL_PROCESS.toString(),
                    ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),
                    SEND_FORMAL_EXAMINATION_REPORT.name(),
                    PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS.name(),
                    PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS_IND.name(),
                    RETURNED_TO_THE_APPLICANT.name()



            ));
            innerMapEXTENSION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString()

            ));
            innerMapEXTENSION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));

            supportServiceCodesApplicationCategoriesStatus.put(EXTENSION.toString(),innerMapEXTENSION);

            innerMapTRADEMARKAPPLICATIONSEARCH.put("CATEGORY",List.of(
                    TRADEMARK.toString()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(TRADEMARK_APPLICATION_SEARCH.toString(),innerMapTRADEMARKAPPLICATIONSEARCH);

            innerMapEVICTION.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
            innerMapEVICTION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString()

            ));

            innerMapEVICTION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(EVICTION.toString(),innerMapEVICTION);


            INNER_MAP_TM_APPEAL_REQUEST.put("STATUS",List.of(OBJECTIVE_REJECTION.toString()));
            INNER_MAP_TM_APPEAL_REQUEST.put("CATEGORY",List.of(TRADEMARK.toString()));
            INNER_MAP_TM_APPEAL_REQUEST.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()));

            INNER_MAP_TM_APPEAL_REQUEST.put(SupportServiceValidationMapKeys.PARENT_SERVICE_VALID_STATUSES.name(),List.of(REJECTED.name()));
            supportServiceCodesApplicationCategoriesStatus.put(TRADEMARK_APPEAL_REQUEST.toString(), INNER_MAP_TM_APPEAL_REQUEST);


            innerMapREVOKERODUCTS.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
            innerMapREVOKERODUCTS.put("CATEGORY",List.of(
                    TRADEMARK.toString()

            ));
            innerMapREVOKERODUCTS.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(),
                    SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(REVOKE_PRODUCTS.toString(),innerMapREVOKERODUCTS);

            innerMapRETRACTION.put("STATUS",List.of(
                    NEW.toString(),
                    INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),
                    UNDER_FORMAL_PROCESS.toString(),
                    OBJECTIVE_REJECTION.toString(),
                    AWAITING_FOR_UPDATE_XML.toString(),
                    RETURNED_TO_THE_APPLICANT.toString(),
                    COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE.toString(),
                    AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE.toString(),
                    PAYMENT_OF_MODIFICATION_FEES_IS_PENDING.toString(),
                    RETURNED_TO_THE_CLASSIFICATION_OFFICER.toString(),
                    AWAITING_VERIFICATION.toString(),
                    FORMAL_REJECTION.toString(),
                    WAITING_FOR_APPLICATION_FEE_PAYMENT.toString(),
                    ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    APPLICANT_SENDING_UPDATES.toString(),
                    UNDER_CLASSIFICATION.name(),
                    UNDER_ACCELERATED_TRACK_STUDY.toString(),
                    DENIED_AS_EXPEDITED_APPLICATION.toString(),
                    RESPOND_TO_THE_FORMAL_EXAMINATION_REPORT.toString(),
                    RETURN_TO_THE_FORMAL_EXAMINER.toString(),
                    UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.toString(),
                    SEND_FORMAL_EXAMINATION_REPORT.toString(),
                    UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR.name(),
                    RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL.name(),
                    UNDER_OBJECTIVE_PROCESS.name()
            ));

            innerMapRETRACTION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString()

            ));

            innerMapRETRACTION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(),SupportServiceActors.AGENT.name()
            ));


            supportServiceCodesApplicationCategoriesStatus.put(RETRACTION.toString(),innerMapRETRACTION);
//
//            // ToDo  - need to complete PETITION RECOVERY Pre Conditions -> appStatus->Down ->categories PATENT-> INDUSTRIAL_DESIGN
            innerMapOPPOSITION.put("STATUS",List.of(
                    OBJECTOR.toString(),
                    PUBLISHED_ELECTRONICALLY.toString()

            ));
            innerMapOPPOSITION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString(),
                    TRADEMARK.name()

            ));
            innerMapOPPOSITION.put(PATENT.toString(),List.of(
                    OBJECTOR.toString(),

                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    PUBLISHED_ELECTRONICALLY.toString()


            ));
            innerMapOPPOSITION.put(INDUSTRIAL_DESIGN.toString(),List.of(
                    OBJECTOR.toString(),

                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    PUBLISHED_ELECTRONICALLY.toString()


            ));
            innerMapOPPOSITION.put(TRADEMARK.name(),List.of(
                    OBJECTOR.toString(),

                    PUBLISHED_ELECTRONICALLY.toString()
            ));

            innerMapOPPOSITION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.OTHER.name()
            ));

            supportServiceCodesApplicationCategoriesStatus.put(OPPOSITION_REQUEST.toString(),innerMapOPPOSITION);

            innerMapINITIAL_MODIFICATION.put("STATUS",List.of(
                    UNDER_FORMAL_PROCESS.toString(),
                    NEW.toString()
            ));
            innerMapINITIAL_MODIFICATION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString()


            ));

            innerMapINITIAL_MODIFICATION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(INITIAL_MODIFICATION.toString(),innerMapINITIAL_MODIFICATION);
            innerMapLICENSING_REGISTRATION.put("STATUS",List.of(
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name()
            ));
            innerMapLICENSING_REGISTRATION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString(),
                    TRADEMARK.toString()
            ));

            innerMapLICENSING_REGISTRATION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.ALL.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(LICENSING_REGISTRATION.toString(),innerMapLICENSING_REGISTRATION);







            innerMapINITIAL_PETITION_RECOVERY.put("STATUS",List.of(
                    ApplicationStatusEnum.WAIVED.toString()
            ));
            innerMapINITIAL_PETITION_RECOVERY.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString()


            ));

            innerMapINITIAL_PETITION_RECOVERY.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));


            supportServiceCodesApplicationCategoriesStatus.put(PETITION_RECOVERY.toString(),innerMapINITIAL_PETITION_RECOVERY);
            innerMapOWNERSHIPCHANGE.put("STATUS",List.of(
                    INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    UNDER_OBJECTIVE_PROCESS.toString(),
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    PUBLISHED_ELECTRONICALLY.toString(),
                    RETURNED_TO_THE_APPLICANT.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),
                    UNDER_FORMAL_PROCESS.toString()

            ));
            innerMapOWNERSHIPCHANGE.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString(),
                    TRADEMARK.toString()


            ));

            innerMapOWNERSHIPCHANGE.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.OTHER.name()
            ));


            innerMapOWNERSHIPCHANGE.put(PATENT.toString(),List.of(
                    INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    UNDER_OBJECTIVE_PROCESS.toString(),
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    PUBLISHED_ELECTRONICALLY.toString(),
                    RETURNED_TO_THE_APPLICANT.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),
                    UNDER_FORMAL_PROCESS.toString()


            ));
            innerMapOWNERSHIPCHANGE.put(INDUSTRIAL_DESIGN.toString(),List.of(
                    INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    UNDER_OBJECTIVE_PROCESS.toString(),
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    PUBLISHED_ELECTRONICALLY.toString(),
                    RETURNED_TO_THE_APPLICANT.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),
                    UNDER_FORMAL_PROCESS.toString()


            ));
            innerMapOWNERSHIPCHANGE.put(TRADEMARK.name(),List.of(
                    INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    UNDER_OBJECTIVE_PROCESS.toString(),
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    PUBLISHED_ELECTRONICALLY.toString(),
                    RETURNED_TO_THE_APPLICANT.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),
                    UNDER_FORMAL_PROCESS.toString()

                    ));


            supportServiceCodesApplicationCategoriesStatus.put(OWNERSHIP_CHANGE.toString(),innerMapOWNERSHIPCHANGE);


            innerMapLICENSING_MODIFICATION.put("STATUS",List.of(
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name()

            ));
            innerMapLICENSING_MODIFICATION.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString(),
                    TRADEMARK.toString()


            ));

            innerMapLICENSING_MODIFICATION.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));

            supportServiceCodesApplicationCategoriesStatus.put(LICENSING_MODIFICATION.toString(),innerMapLICENSING_MODIFICATION);
            innerMapAPPEAL.put("STATUS",List.of(
                    REJECT_THE_OBJECTION.toString(),
                    FORMAL_REJECTION.toString(),
                    OBJECTIVE_REJECTION.toString()

            ));
            innerMapAPPEAL.put("CATEGORY",List.of(
                    INDUSTRIAL_DESIGN.toString(),
                    PATENT.toString(),
                    TRADEMARK.toString()


            ));
            innerMapAPPEAL.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));

            supportServiceCodesApplicationCategoriesStatus.put(APPEAL_REQUEST.toString(),innerMapAPPEAL);
            innerMapPROTECTIONEXTEND.put("STATUS",List.of(
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name()


                    ));
            innerMapPROTECTIONEXTEND.put("CATEGORY",List.of(
                    PATENT.toString()



            ));

            innerMapPROTECTIONEXTEND.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));


            supportServiceCodesApplicationCategoriesStatus.put(PROTECTION_PERIOD_EXTENSION_REQUEST.toString(), innerMapPROTECTIONEXTEND);
            innerMapRENEWALFEESPAY.put("STATUS",List.of(
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name()

                    ));
            innerMapRENEWALFEESPAY.put("CATEGORY",List.of(
                    TRADEMARK.toString(),
                    INDUSTRIAL_DESIGN.toString()
            ));

            innerMapRENEWALFEESPAY.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(RENEWAL_FEES_PAY.toString(), innerMapRENEWALFEESPAY);

            innerMapANNUALFEESPAY.put("STATUS",List.of(
                    ACCEPTANCE.toString(),
                    THE_TRADEMARK_IS_REGISTERED.name(),
                    IN_COMMITTEE.toString(),
                    AWAITING_VERIFICATION.toString(),
                    WAITING_FOR_APPLICATION_FEE_PAYMENT.toString(),
                    NEW.toString(),SENDBACK.toString(),INVITATION_FOR_OBJECTIVE_CORRECTION.toString(),
                    RETURNED_TO_THE_CLASSIFICATION_OFFICER.toString(),
                    PAYMENT_OF_MODIFICATION_FEES_IS_PENDING.toString(),
                    PUBLICATION_FEES_ARE_PENDING.toString(),
                    PUBLISHED_ELECTRONICALLY.toString(),
                    AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE.toString(),
                    COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE.toString(),
                    RETURNED_TO_THE_APPLICANT.toString(),OBJECTOR.toString(),
                    ACCEPT_THE_OBJECTION.toString(),REJECT_THE_OBJECTION.toString(),
                    INVITATION_FOR_FORMAL_CORRECTION.toString(),UNDER_FORMAL_PROCESS.toString(),
                    FORMAL_REJECTION.toString(),OBJECTIVE_REJECTION.toString(),UNDER_OBJECTIVE_PROCESS.toString(),
                    AWAITING_FOR_UPDATE_XML.toString(),ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION.toString(),
                    COMPLETE_REQUIREMENTS.toString(),
                    RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL.toString(),
                    UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR.toString(),
                    ACCEPTED_BY_THE_GRIEVANCES_COMMITTEE.toString(),
                    UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL.toString(),
                    THE_APPLICANT_IS_INVITED_TO_ACCEPT_FORMAL_AMENDMENTS.toString(),
                    UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.toString(),
                    UNDER_ACCELERATED_TRACK_STUDY.toString(),
                    DENIED_AS_EXPEDITED_APPLICATION.toString(),
                    ACCEPTED_AS_AN_EXPEDITED_APPLICATION.toString(),
                    PENDING_PAYMENT_OF_ACCELERATED_TRACK_PUBLICATION_FEE.toString(),
                    RESPOND_TO_THE_FORMAL_EXAMINATION_REPORT.toString(),
                    SEND_FORMAL_EXAMINATION_REPORT.toString(),
                    PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS.toString(),
                    ACCEPTANCE_OF_FORMAL_EXAMINATION_UNDER_CLASSIFICATION.toString(),
                    SUBMIT_THE_COSTS_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION.toString(),
                    RETURN_TO_THE_FORMAL_EXAMINER.toString(),
                    UNDER_CLASSIFICATION.toString(),
                    PUBLICATION_A_FEES_ARE_PENDING.toString()

                    )
            );
            innerMapANNUALFEESPAY.put("CATEGORY",List.of(
                    PATENT.toString())
            );

            innerMapANNUALFEESPAY.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));

            innerMapREVOKEVOLUNTRY.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
            innerMapREVOKEVOLUNTRY.put("CATEGORY",List.of(
                    TRADEMARK.toString()

            ));
            innerMapREVOKEVOLUNTRY.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(VOLUNTARY_REVOKE.toString(),innerMapREVOKEVOLUNTRY);

            /// new REVOKE_LICENSE_REQUEST
            innerMapREVOKELICENCEREQUEST.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
   innerMapREVOKELICENCEREQUEST.put("CATEGORY",List.of(
                    TRADEMARK.toString()

            ));
            innerMapREVOKELICENCEREQUEST.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.LICENSED_CUSTOMER.name(), SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name())
            );
            innerMapREVOKELICENCEREQUEST.put(SupportServiceValidationMapKeys.PARENT_SERVICE_VALID_STATUSES.name(),List.of(
                    LICENSED.name())
            );
            supportServiceCodesApplicationCategoriesStatus.put(REVOKE_LICENSE_REQUEST.toString(),innerMapREVOKELICENCEREQUEST);
            innerMapOPPOSITIONREVOKELICENCEREQUEST.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
            innerMapOPPOSITIONREVOKELICENCEREQUEST.put("CATEGORY",List.of(
                    TRADEMARK.toString()

            ));
            innerMapOPPOSITIONREVOKELICENCEREQUEST.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.LICENSED_CUSTOMER.name(), SupportServiceActors.LICENSED_CUSTOMER_AGENT.name(), SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name())
            );
            innerMapOPPOSITIONREVOKELICENCEREQUEST.put(SupportServiceValidationMapKeys.PARENT_SERVICE_VALID_STATUSES.name(),List.of(
                    OPPOSITION_WATING.name())
            );
            supportServiceCodesApplicationCategoriesStatus.put(OPPOSITION_REVOKE_LICENCE_REQUEST.toString(),innerMapOPPOSITIONREVOKELICENCEREQUEST);


            innerMapEDITTRADEMARKNAMEADDRESS.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
            innerMapEDITTRADEMARKNAMEADDRESS.put("CATEGORY",List.of(
                    TRADEMARK.toString()

            ));
            innerMapEDITTRADEMARKNAMEADDRESS.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));
            
            INNER_MAP_EDIT_TRADEMARK_IMAGE.put("STATUS",List.of(ACCEPTANCE.toString(), THE_TRADEMARK_IS_REGISTERED.name()));
            INNER_MAP_EDIT_TRADEMARK_IMAGE.put("CATEGORY",List.of(
                    TRADEMARK.toString()
            
            ));
            INNER_MAP_EDIT_TRADEMARK_IMAGE.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));

            innerMapPETITIONREQUESTNATIONALSTAGE.put("CATEGORY",List.of(
                    PATENT.toString()
            ));

            innerMapPETITIONREQUESTNATIONALSTAGE.put(SupportServiceValidationMapKeys.ACTORS.name(),List.of(
                    SupportServiceActors.MAIN_OWNER.name(), SupportServiceActors.AGENT.name()
            ));
            supportServiceCodesApplicationCategoriesStatus.put(PETITION_REQUEST_NATIONAL_STAGE.toString(),innerMapPETITIONREQUESTNATIONALSTAGE);


            supportServiceCodesApplicationCategoriesStatus.put(EDIT_TRADEMARK_NAME_ADDRESS.toString(),innerMapEDITTRADEMARKNAMEADDRESS);
            supportServiceCodesApplicationCategoriesStatus.put(EDIT_TRADEMARK_IMAGE.toString(),INNER_MAP_EDIT_TRADEMARK_IMAGE);
            supportServiceCodesApplicationCategoriesStatus.put(ANNUAL_FEES_PAY.toString(), innerMapANNUALFEESPAY);
            supportServicesCodes.add(EVICTION.toString());
            supportServicesCodes.add(EXTENSION.toString());
            supportServicesCodes.add(RETRACTION.toString());
            supportServicesCodes.add(APPEAL_REQUEST.toString());
            supportServicesCodes.add(PROTECTION_PERIOD_EXTENSION_REQUEST.toString());
            supportServicesCodes.add(INITIAL_MODIFICATION.toString());
            supportServicesCodes.add(LICENSING_REGISTRATION.toString());
            supportServicesCodes.add(REVOKE_LICENSE_REQUEST.toString());
            supportServicesCodes.add(OWNERSHIP_CHANGE.toString());
            supportServicesCodes.add(LICENSING_MODIFICATION.toString());
            supportServicesCodes.add(PETITION_RECOVERY.toString());
            supportServicesCodes.add(OPPOSITION_REQUEST.toString());
            supportServicesCodes.add(ANNUAL_FEES_PAY.toString());
            supportServicesCodes.add(RENEWAL_FEES_PAY.toString());
            supportServicesCodes.add(REVOKE_PRODUCTS.toString());


            supportServicesCodes.add(VOLUNTARY_REVOKE.toString());
            supportServicesCodes.add(EDIT_TRADEMARK_NAME_ADDRESS.toString());
            supportServicesCodes.add(EDIT_TRADEMARK_IMAGE.toString());

        }
    }
    
    public static final Map<String, String> MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE = new HashMap<>();
    
    static {
        MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE.put("TRADEMARK", TRADEMARK_REGISTERATION.name());
        MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE.put("PATENT", PATENT_REGISTRATION.name());
        MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE.put("INDUSTRIAL_DESIGN", INDUSTRIAL_DESIGN_REGISTRATION.name());
    }
    public static final class EventDriven {
        private EventDriven() {

        }

        public static final String QUEUE_NAME = "notification";

    }

    private Constants() {

    }



    public static final class MailSubject {


        private MailSubject() {


        }


        public static final String ACCEPTANCE_SUBJECT = "طلب مقبول";


        public static final String REJECTION_SUBJECT = "طلب مرفوض";

        public static final String PENDING_SUBJECT = "طلب معلق";

    }

    public static final class MailMessage {


        private MailMessage() {


        }

        public static final String ACCEPTANCE_MESSAGE = "ACCEPTANCE_MESSAGE";


        public static final String REJECTION_MESSAGE = "REJECTION_MESSAGE";

        public static final String PENDING_MESSAGE = "PENDING_MESSAGE";


    }


    public static final String ACTIVATION_ALLOWANCE_DAYS_COUNT = "ACTIVATION_ALLOWANCE_DAYS_COUNT";



    @AllArgsConstructor
    @Getter
    public enum AppRequestHeaders {
        CUSTOMER_ID("X-Customer-Id", null),
        CUSTOMER_CODE("X-Customer-Code", null),
        MAIN_SERVICE_NAME("X-MAIN-SERVICE-NAME", Constants.AppName),
        REQUEST_CORRELATION_ID("X-B3-TraceId", null),
        MAIN_SERVICE_TYPE("X-MAIN-SERVICE-TYPE", Constants.SERVICE_TYPE);
        private String key;
        private String value;
    }


    @AllArgsConstructor
    @Getter
    public enum PatenChangeLogAttributeNames {

        GRAPHICS("enGraphics", "arGraphics"),
        PROTECTION("enProtection", "arProtection"),
        SUMMARY("enSummary", "arSummary"),
        DESCRIPTION("enDescription", "arDescription");

        private String nameEn;
        private String nameAr;
    }
    @AllArgsConstructor
    @Getter
    public enum TradeMarkApplicationModificationAction {

        ACTION(" mark modifications from formal checker", "تعديلات بيانات العلامة من قبل للفاحص الشكلي");


        private String nameEn;
        private String nameAr;
    }



    public static final List<String> APPLICATIONS_UNDER_STUDY = List.of(
            INVITATION_FOR_OBJECTIVE_CORRECTION.name(),
            RETURNED_TO_THE_CLASSIFICATION_OFFICER.name(),
            PAYMENT_OF_MODIFICATION_FEES_IS_PENDING.name(),
            PUBLICATION_FEES_ARE_PENDING.name(),
            INVITATION_FOR_FORMAL_CORRECTION.name(),
            UNDER_FORMAL_PROCESS.name(),
            UNDER_OBJECTIVE_PROCESS.name(),
            UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.name(),
            UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL.name(),
            UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR.name(),
            RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL.name(),
            UNDER_REVIEW_BY_AN_CHECKER_AUDITOR.name(),
            RETURN_TO_THE_FORMAL_EXAMINER.name(),
            INVITATION_FOR_FORMAL_CORRECTION.name(),
            PAYMENT_OF_FINANCIAL_GRANTS_FEES_IS_PENDING.name(),
            INVITATION_FOR_FORMAL_CORRECTION_2.name()
    );

    public static final List<String> REFUSED_APPLICATIONS = List.of(
            FORMAL_REJECTION.name(),
            OBJECTIVE_REJECTION.name(),
            REJECTED_BECAUSE_THE_OPPOSITION_ACCEPTED.name(),
            CROSSED_OUT_MARK.name(),
            REJECTED_BY_THE_GRIEVANCES_COMMITTEE.name(),
            LACK_RESPONSE_REJECTION.name(),
            LACK_RESPONSE_REJECTION_2.name()
    );


    public static final List<String> GRANTED_APPLICATIONS = List.of(
            THE_TRADEMARK_IS_REGISTERED.name(),
            ACCEPTANCE.name(),
            REVOKED_PURSUANT_TO_COURT_RULING.name(),
            DISMISSED_PAYMENT_OF_ANNUAL_FEES.name()
    );

    public static final String NEW_APPLICATIONS = NEW.name();

    public static final List<SupportServiceType> SERVICES_CAN_BE_APPLIED_BY_MANY_CUSTOMER_IN_THE_SAME_TIME = List.of(
            OPPOSITION_REQUEST
    );

    public static final List<String> VALIDATE_SUPPORT_SERVICE_REQUEST_STATUSES = List.of(
            UNDER_PROCEDURE.name(),
            REQUEST_CORRECTION.name(),
            UNDER_OPPOSITION.name(),
            OPPOSITION_WATING.name(),
            COURT_DOCUMENTS_CORRECTION.name(),
            PAY_PUBLICATION_FEES.name(),
            DRAFT.name(),
            UNDER_REVIEW_AUDITOR.name(),
            UNDER_REVIEW_SUBJECTIVE.name(),
            UNDER_REVIEW_VISUAL.name(),
            PENDING_IMG_FEES_MOD_REQ.name(),
            PENDING.name(),
            REOPENED.name(),
            APPEAL_RETURNED_TO_DEPARTMENT.name(),
            COMPLAINANT_TO_COMMITTEE.name(),
            PENDING_FEES_COMPLAINT.name(),
            PENDING_IMG_FEES_MOD_PUB.name(),
            RETURNED_TO_THE_APPLICANT.name(),
            PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS.name(),
            INVITATION_FOR_OBJECTIVE_CORRECTION.name(),
            SEND_FORMAL_EXAMINATION_REPORT.name(),
            PUBLICATION_FEES_ARE_PENDING.name(),
            RETURNED_TO_THE_APPLICANT.name(),
            PUBLICATION_A_FEES_ARE_PENDING.name()
    );
    
    public static final List<SupportServiceType> SERVICES_EXCLUDED_FROM_SUPPORT_SERVICES_UNDER_PROCEDURE = List.of(
            REVOKE_LICENSE_REQUEST
    );

    @AllArgsConstructor
    @Getter
    public enum DateFormats {
        DATE_FORMAT_WITH_X_OFFSET("yyyy-MM-dd'T'HH:mm:ss.SSSX"),
        DATE_FORMAT_WITH_Z_OFFSET("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        private String format;
    }


    public static final List<String> NON_OWNER_SUPPORT_SERVICE_STRING_CODES = List.of(
            OWNERSHIP_CHANGE.name(),
            LICENSING_REGISTRATION.name(),
            OPPOSITION_REVOKE_LICENCE_REQUEST.name(),
            REVOKE_LICENSE_REQUEST.name(),
            REVOKE_BY_COURT_ORDER.name(),
            OPPOSITION_REQUEST.name()
    );

}
