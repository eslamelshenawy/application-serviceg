package gov.saip.applicationservice.common.service.lookup.impl;


import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.common.model.LkDatabase;
import gov.saip.applicationservice.common.service.lookup.LkDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LKDatabaseServiceImpl extends BaseLkServiceImpl<LkDatabase, Integer> implements LkDatabaseService {


}
