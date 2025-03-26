package gov.saip.applicationservice.common.service.patent.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ProtectionElementCounts;
import gov.saip.applicationservice.common.dto.patent.ProjectionElementsRequestDTO;
import gov.saip.applicationservice.common.dto.patent.ProjectionElementsResponseDTO;
import gov.saip.applicationservice.common.dto.patent.ProtectionElementsDTO;
import gov.saip.applicationservice.common.mapper.patent.ProtectionElementMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.patent.ProtectionElements;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.patent.ProtectionElementRepository;
import gov.saip.applicationservice.common.service.impl.ApplicationServiceImpl;
import gov.saip.applicationservice.common.service.patent.ProtectionElementService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProtectionElementServiceImpl extends BaseServiceImpl<ProtectionElements, Long> implements ProtectionElementService {
    private final ProtectionElementRepository protectionElementRepository;
    private final ProtectionElementMapper protectionElementMapper;
    private final ApplicationServiceImpl applicationService;
    private final ApplicationInfoRepository applicationInfoRepository;
    @Override
    protected BaseRepository<ProtectionElements, Long> getRepository() {
        return protectionElementRepository;
    }

    @Override
    public List<ProjectionElementsResponseDTO> getProtectionElements(Long applicationId, Boolean isEnglish) {

        List<ProtectionElements> parents =  protectionElementRepository.getParentProtectionElements(applicationId, isEnglish);
        List<ProjectionElementsResponseDTO> projectionElementsResponseDTOS = new ArrayList<>();

        for(ProtectionElements parent : parents){
            ProjectionElementsResponseDTO projectionElementsResponseDTO = new ProjectionElementsResponseDTO();

            ProtectionElementsDTO independentElementDto = protectionElementMapper.map(parent);

            List<ProtectionElements> dependents =  protectionElementRepository.getChildrenProtectionElements(applicationId, parent.getId());
            List<ProtectionElementsDTO> childrenDTO = new ArrayList<>();

            for(ProtectionElements child : dependents){
                ProtectionElementsDTO dependentElementsDto = protectionElementMapper.map(child);
                childrenDTO.add(dependentElementsDto);
            }

            projectionElementsResponseDTO.setParent(independentElementDto);
            projectionElementsResponseDTO.setDependents(childrenDTO);
            projectionElementsResponseDTOS.add(projectionElementsResponseDTO);
        }

        return projectionElementsResponseDTOS;
    }

    public List<ProjectionElementsResponseDTO> getProtectionElementsByApplicationId(Long applicationId) {
        List<ProtectionElements> parents =  protectionElementRepository.getParentProtectionElementsByApplicationId(applicationId);
        List<ProjectionElementsResponseDTO> projectionElementsResponseDTOS = new ArrayList<>();
        for(ProtectionElements parent : parents){
            ProjectionElementsResponseDTO projectionElementsResponseDTO = new ProjectionElementsResponseDTO();
            ProtectionElementsDTO independentElementDto = protectionElementMapper.map(parent);
            List<ProtectionElements> dependents =  protectionElementRepository.getChildrenProtectionElements(applicationId, parent.getId());
            List<ProtectionElementsDTO> childrenDTO = new ArrayList<>();
            for(ProtectionElements child : dependents){
                ProtectionElementsDTO dependentElementsDto = protectionElementMapper.map(child);
                childrenDTO.add(dependentElementsDto);
            }
            projectionElementsResponseDTO.setParent(independentElementDto);
            projectionElementsResponseDTO.setDependents(childrenDTO);
            projectionElementsResponseDTOS.add(projectionElementsResponseDTO);
        }
        return projectionElementsResponseDTOS;
    }

    @Override
    @Transactional
    public void addProtectionElements(ProjectionElementsRequestDTO requestDTO) {
        if(Objects.isNull(requestDTO.getId())){
            saveParent(requestDTO.getDescription(), requestDTO.getApplicationId(), requestDTO.getIsEnglish());
        }else {
            ProtectionElements myParent = super.findById(requestDTO.getId());
            if(myParent.getIsDeleted() == 1){
                throw new BusinessException("EXCEPTION_DELETED_PROTECTION_ELEMENT", HttpStatus.NOT_FOUND, new String[]{requestDTO.getId().toString()});
            }
            myParent.setDescription(requestDTO.getDescription());
            if(Objects.nonNull(requestDTO.getDependents())){
                myParent.setDependentElements(addDependentsFlow(requestDTO));
            }
            protectionElementRepository.save(myParent);
        }
       updateApplicationInfo(requestDTO);
    }

    private void updateApplicationInfo(ProjectionElementsRequestDTO requestDTO) {
        ProtectionElementCounts protectionElementCounts = protectionElementRepository.getProtectionElementCountsByApplicationId(requestDTO.getApplicationId());
        ApplicationInfo appInfo = applicationService.findById(requestDTO.getApplicationId());
        appInfo.setParentElementsCount(protectionElementCounts.getCountParentElements());
        appInfo.setChildrenElementsCount(protectionElementCounts.getCountChildrenElements());
        appInfo.setTotalPagesNumber(0L);
        applicationInfoRepository.save(appInfo);
    }

    @Override
    public void deleteProtectionElements(Long protectionElementId) {
        ProtectionElements elementToDelete = this.findById(protectionElementId);
        if(elementToDelete.getIsDeleted() == 1){
            throw new BusinessException("EXCEPTION_DELETED_PROTECTION_ELEMENT", HttpStatus.NOT_FOUND, new String[]{protectionElementId.toString()});
        }
        elementToDelete.setIsDeleted(1);
        if(Objects.nonNull(elementToDelete.getDependentElements())){
            elementToDelete.setDependentElements(deleteDependents(elementToDelete.getDependentElements()));
        }
        protectionElementRepository.save(elementToDelete);
    }

    @Override
    public Long getCountParentProtectionElementsByApplicationId(Long applicationId) {
        return protectionElementRepository.getCountParentProtectionElementsByApplicationId(applicationId);
    }

    @Override
    public Long getCountChildrenProtectionElementsByApplicationId(Long applicationId) {
        return protectionElementRepository.getCountChildrenProtectionElementsByApplicationId(applicationId);
    }

    @Override
    public ProtectionElementCounts getProtectionElementCountsByApplicationId(Long applicationId) {
        ProtectionElementCounts protectionElementCounts = protectionElementRepository.getProtectionElementCountsByApplicationId(applicationId);
        ApplicationInfo appInfo = applicationService.findById(applicationId);
        appInfo.setParentElementsCount(protectionElementCounts.getCountParentElements());
        appInfo.setChildrenElementsCount(protectionElementCounts.getCountChildrenElements());
        appInfo.setTotalPagesNumber(0L);
        applicationInfoRepository.save(appInfo);
        return protectionElementCounts;
        
    }

    private Set<ProtectionElements> deleteDependents(Set<ProtectionElements> dependentElements) {
        Set<ProtectionElements> dependentsEntities = new HashSet<>();
        for(ProtectionElements dependent : dependentElements){
            ProtectionElements elementToDelete = this.findById(dependent.getId());
            elementToDelete.setIsDeleted(1);
            dependentsEntities.add(protectionElementRepository.save(dependent));
        }
        return dependentsEntities;
    }

    public Set<ProtectionElements> addDependentsFlow(ProjectionElementsRequestDTO requestDTO){
        Set<ProtectionElements> dependentsEntities = new HashSet<>();
        for(ProjectionElementsRequestDTO dependent : requestDTO.getDependents()){
            if(Objects.isNull(dependent.getId())){
                dependentsEntities.add(saveDependent(dependent, requestDTO.getApplicationId(), requestDTO.getId(), requestDTO.getIsEnglish()));
            }else{
                ProtectionElements myDependent = super.findById(dependent.getId());
                if(myDependent.getIsDeleted() == 1){
                    throw new BusinessException("EXCEPTION_DELETED_PROTECTION_ELEMENT", HttpStatus.NOT_FOUND, new String[]{dependent.getId().toString()});
                }
                myDependent.setDescription(dependent.getDescription());
                dependentsEntities.add(protectionElementRepository.save(myDependent));
            }

        }
        return dependentsEntities;
    }
    public ProtectionElements saveDependent(ProjectionElementsRequestDTO requestDTO, Long applicationId, Long parentId, Boolean isEnglish){
        ProtectionElements newDependentEntity = new ProtectionElements();
        newDependentEntity.setDescription(requestDTO.getDescription());
        newDependentEntity.setApplication(applicationService.findById(applicationId));
        newDependentEntity.setParentElement(super.findById(parentId));
        newDependentEntity.setIsEnglish(isEnglish);
        return protectionElementRepository.save(newDependentEntity);
    }

    public void saveParent(String description, Long applicationId, Boolean isEnglish){
        ProtectionElements newParentEntity = new ProtectionElements();
        newParentEntity.setDescription(description);
        newParentEntity.setIsEnglish(isEnglish);
        newParentEntity.setApplication(applicationService.findById(applicationId));
        protectionElementRepository.save(newParentEntity);
    }

    @Override
    public List<String> getProtectionElementsDescByApplicationId(Long applicationId) {
        return protectionElementRepository.getProtectionElementsDescByApplicationId(applicationId);
    }

}
