package gov.saip.applicationservice.common.service.applicationSnapShot.Impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDropDownMenuResponseDto;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDto;
import gov.saip.applicationservice.common.mapper.applicationSnapShot.ApplicationSnapShotMapper;
import gov.saip.applicationservice.common.model.ApplicationSnapShot;
import gov.saip.applicationservice.common.repository.applicationSnapShot.ApplicationSnapShotRepository;
import gov.saip.applicationservice.common.service.applicationSnapShot.ApplicationSnapShotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ApplicationSnapShotServiceImpl extends BaseServiceImpl<ApplicationSnapShot, Long> implements ApplicationSnapShotService {


    private final ApplicationSnapShotRepository applicationSnapShotRepository;
    private final ApplicationSnapShotMapper applicationSnapShotMapper;
    @Override
    protected BaseRepository<ApplicationSnapShot, Long> getRepository() {
        return applicationSnapShotRepository;
    }

    @Override
    public ApplicationSnapShot insert(ApplicationSnapShot entity) {


        return super.insert(entity);
    }


    @Override
    public ApplicationSnapShotDto getApplicationByVersionNumber(Integer versionNumber, Long appId) {

        Optional<ApplicationSnapShot> applicationSnapShot = applicationSnapShotRepository.findByVersionNumberAndApplicationId(versionNumber, appId);
        if (applicationSnapShot.isPresent()) {
            return applicationSnapShotMapper.map(applicationSnapShot.get());
        }

        return null;
    }


    public List<ApplicationSnapShotDropDownMenuResponseDto> getApplicationVersionNumberAndCreatedDateHijri(Long appId) {
        Optional<List<ApplicationSnapShotDropDownMenuResponseDto>> applicationSnapShotDropDownMenuResponseDtos =
                applicationSnapShotRepository.getApplicationVersions(appId);

        if (applicationSnapShotDropDownMenuResponseDtos.isPresent()) {
            return applicationSnapShotDropDownMenuResponseDtos.get();
        }

        return null;
    }

    @Override
    public void takeSnapShot(ApplicationSnapShotDto dto) {
        ApplicationSnapShot entity = applicationSnapShotMapper.unMap(dto);
        Integer applicationVersion = applicationSnapShotRepository.getApplicationVersionNumber(entity.getApplication().getId());
        if (Objects.isNull(applicationVersion)) {
            applicationVersion = 1;
        } else {
            applicationVersion = applicationVersion + 1;
        }
        entity.setVersionNumber(applicationVersion);
        super.insert(entity);
    }



}
