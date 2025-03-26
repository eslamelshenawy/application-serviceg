package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusGroupEnum;
import gov.saip.applicationservice.common.mapper.lookup.LkApplicationStatusMapper;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.duplicatedStatusRemoval.FormalRejectionStatusGrouping;
import gov.saip.applicationservice.util.duplicatedStatusRemoval.ReturnedToApplicantStatusGrouping;
import gov.saip.applicationservice.util.duplicatedStatusRemoval.StatusProcessor;
import gov.saip.applicationservice.util.duplicatedStatusRemoval.UnderProcessStatusGrouping;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LkApplicationStatusServiceImpl extends BaseServiceImpl<LkApplicationStatus, Long> implements LkApplicationStatusService {

    private final LkApplicationStatusRepository lkApplicationStatusRepository;

    private final LkApplicationStatusMapper lkApplicationStatusMapper;
    @Override
    protected BaseRepository<LkApplicationStatus, Long> getRepository() {
        return lkApplicationStatusRepository;
    }
    public LkApplicationStatus findByCode(String code) {
        String[] params = {code};
        return lkApplicationStatusRepository.findByCode(code).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND
                        , HttpStatus.NOT_FOUND, params));
    }

    @Override
//    @Cacheable(value = "findByCodeAndApplicationCategory", key = "#code + '-' + #categoryId")
    public LkApplicationStatus findByCodeAndApplicationCategory(String code, Long categoryId) {
        String[] params = {code , String.valueOf(categoryId)};
        return lkApplicationStatusRepository.findByCodeAndApplicationCategoryId(code , categoryId).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND
                        , HttpStatus.NOT_FOUND, params));
    }


    public LkApplicationStatus update(LkApplicationStatus lkApplicationStatus){
        LkApplicationStatus currentLkApplicationStatus = getReferenceById(lkApplicationStatus.getId());
        currentLkApplicationStatus.setIpsStatusDescAr(lkApplicationStatus.getIpsStatusDescAr());
        currentLkApplicationStatus.setIpsStatusDescEn(lkApplicationStatus.getIpsStatusDescEn());
        currentLkApplicationStatus.setIpsStatusDescEnExternal(lkApplicationStatus.getIpsStatusDescEnExternal());
        currentLkApplicationStatus.setIpsStatusDescArExternal(lkApplicationStatus.getIpsStatusDescArExternal());
        return super.update(currentLkApplicationStatus);
    }

    @Override
    public List<LkApplicationStatus> getStatusByCategory(String code) {
        return lkApplicationStatusRepository.getStatusByCategory(code);
    }

    @Override
    public LkApplicationStatus getStatusByApplicationId(Long id) {
        return lkApplicationStatusRepository.getStatusByApplicationId(id);
    }

    @Override
    public PaginationDto findAllApplicationStatusByAppCategory(Integer page, Integer limit, String sortableColumn, Long applicationCategoryId, String search) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortableColumn));
        Page<LkApplicationStatusDto> pageDto = lkApplicationStatusRepository.findAllApplicationStatusByCategory(applicationCategoryId, search.toLowerCase(), pageable)
                .map(lkApplicationStatusMapper::map);
        return PaginationDto
                .builder()
                .content(pageDto.getContent())
                .totalElements(pageDto.getTotalElements())
                .totalPages(pageDto.getTotalPages())
                .build();
    }

    @Override
    public LkApplicationStatus getStatusByCode(String code) {
        Optional<LkApplicationStatus> status = lkApplicationStatusRepository.findByCode(code);
        return status.isEmpty() ? null : status.get();
    }

    @Override
    public LkApplicationStatus getStatusByCodeAndApplicationId(String code, Long applicationId) {
        Optional<LkApplicationStatus> status = lkApplicationStatusRepository.findByCodeAndApplicationId(code, applicationId);
        return status.isEmpty() ? null : status.get();
    }

    @Override
    public List<LkApplicationStatusDto> getStatusGrouped(String categoryCode) {


        List<StatusProcessor> statusProcessors = List.of(
                new UnderProcessStatusGrouping(ApplicationStatusGroupEnum.UNDER_PROCESS),
                new ReturnedToApplicantStatusGrouping(ApplicationStatusGroupEnum.RETURNED_TO_APPLICANT),
                new FormalRejectionStatusGrouping(ApplicationStatusGroupEnum.FORMAL_REJECT)
        );


        List<LkApplicationStatusDto> statusDtos = lkApplicationStatusMapper.map(getStatusByCategory(categoryCode));
        setAllStatusListWithId(statusDtos);

        statusProcessors.forEach(statusProcessor -> {
                    statusProcessor.process(statusDtos);
                }

        );

        removeStatuses(statusDtos,List.of(
                ApplicationStatusEnum.DRAFT.name()
        ));
        return statusDtos;
    }

    @Override
    public List<LkApplicationStatusDto> getStatusGroupedInternal(String categoryCode) {

        List<LkApplicationStatusDto> statusDtos = lkApplicationStatusMapper.map(getStatusByCategory(categoryCode));
        setAllStatusListWithId(statusDtos);
        return statusDtos;
    }
    private void setAllStatusListWithId(List<LkApplicationStatusDto> statuses) {
        statuses.forEach(s->{
            s.setStatusIds(List.of(s.getId()));
        });
    }

    private void removeStatuses(List<LkApplicationStatusDto> statuses,List<String> statusCodes){

        statuses.removeAll(statuses.stream().filter(status->statusCodes.contains(status.getCode())).collect(Collectors.toList()));



    }





}
