package gov.saip.applicationservice.common.service.industrial.impl;


import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleDrawingsReqDto;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import gov.saip.applicationservice.common.model.industrial.DesignSampleDrawings;
import gov.saip.applicationservice.common.model.industrial.LkShapeType;
import gov.saip.applicationservice.common.repository.industrial.DesignSampleDrawingsRepository;
import gov.saip.applicationservice.common.service.industrial.DesignSampleDrawingsService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.TSIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DesignSampleDrawingsServiceImpl extends BaseServiceImpl<DesignSampleDrawings, Long> implements DesignSampleDrawingsService {

    private final static Logger logger = LoggerFactory.getLogger(DesignSampleServiceImpl.class);

    private final DesignSampleDrawingsRepository designSampleDrawingsRepository;
    @Override
    protected BaseRepository<DesignSampleDrawings, Long> getRepository() {
        return designSampleDrawingsRepository;
    }

    public DesignSampleDrawings createDesignSampleDrawings(DesignSampleDrawingsReqDto drawingsReqDto, DesignSample designSample) {

        try {
            DesignSampleDrawings designSampleDrawings = null;
            if (Objects.nonNull(drawingsReqDto.getId())) {
                designSampleDrawings = findById(drawingsReqDto.getId());
            } else {
                designSampleDrawings = new DesignSampleDrawings();
                designSampleDrawings.setId(TSIDUtils.next());
            }
            designSampleDrawings.setMain(drawingsReqDto.isMain());
            LkShapeType lkShapeType = new LkShapeType();
            if (drawingsReqDto.getShape() != null && drawingsReqDto.getShape().getId() != null) {
                lkShapeType.setId(drawingsReqDto.getShape().getId());
                designSampleDrawings.setShape(lkShapeType);
            }else{
                designSampleDrawings.setShape(null);
            }
            designSampleDrawings.setDocId(drawingsReqDto.getDocId());
            designSampleDrawings.setDesignSampleId(designSample.getId());
            designSampleDrawings.setDoc3d(drawingsReqDto.isDoc3d());
            return designSampleDrawings;
        } catch (
                BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public DesignSampleDrawings save(DesignSampleDrawingsReqDto drawingsReqDto, DesignSample designSample) {
        try {
            DesignSampleDrawings designSampleDrawings = createDesignSampleDrawings(drawingsReqDto, designSample);
            DesignSampleDrawings result = insert(designSampleDrawings);
            return result;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }

    }

    public void deleteDrawing(Long id) {
        designSampleDrawingsRepository.updateIsDeleted(id, 1);
    }
}
