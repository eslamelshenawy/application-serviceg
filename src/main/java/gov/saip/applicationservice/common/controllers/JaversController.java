package gov.saip.applicationservice.common.controllers;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JaversController {
    private final static Logger logger = LoggerFactory.getLogger(JaversController.class);
//    private final Javers javers;
//
//    public JaversController(Javers javers) {
//        this.javers = javers;
//    }

//    @GetMapping("changes/{className}/{instanceId}")
//    public String getEntityChangesById(@PathVariable String className,@PathVariable Long instanceId) {
//        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(instanceId,className);
//        Changes changes = javers.findChanges(jqlQuery.build());
////        System.out.println(changes.prettyPrint());
//        return javers.getJsonConverter().toJson(changes);
//    }
//
//    @GetMapping("changes/{className}")
//    public String getEntityChanges(@PathVariable String className) throws ClassNotFoundException{
//        Class clazz = Class.forName(className);
//
//        QueryBuilder jqlQuery = QueryBuilder.byClass(clazz);
//        Changes changes = javers.findChanges(jqlQuery.build());
////        System.out.println(changes.prettyPrint());
//        return javers.getJsonConverter().toJson(changes);
//    }

}
