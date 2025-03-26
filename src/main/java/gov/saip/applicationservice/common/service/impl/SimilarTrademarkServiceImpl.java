package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.SimilarTrademarkDto;
import gov.saip.applicationservice.common.mapper.SimilarTrademarkMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.SimilarTrademark;
import gov.saip.applicationservice.common.repository.SimilarTrademarkRepository;
import gov.saip.applicationservice.common.service.SimilarTrademarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimilarTrademarkServiceImpl extends BaseServiceImpl<SimilarTrademark, Long> implements SimilarTrademarkService {
    private final SimilarTrademarkMapper similarTrademarkMapper;
    private final SimilarTrademarkRepository similarTrademarkRepository;
    @Override
    protected BaseRepository<SimilarTrademark, Long> getRepository() {
        return similarTrademarkRepository;
    }

    @Override
    public List<SimilarTrademarkDto> getTaskInstanceId(String taskInstanceId) {
        return similarTrademarkMapper.map(similarTrademarkRepository.findByTaskInstanceId(taskInstanceId));
    }
    @Override
    public int softDelete(long id) {
        return similarTrademarkRepository.updateIsDeleted(id, 1);
    }
    @Override
    public List<SimilarTrademarkDto> findByApplicationInfoId(Long applicationInfo) {
        return similarTrademarkMapper.map(similarTrademarkRepository.findByApplicationInfoId(applicationInfo));
    }


}
