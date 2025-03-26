package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "task_eqm")
@Setter
@Getter
public class TaskEqm extends BaseEntity<Long> {

    private String taskId;
    private String taskKey;
    private double average;
    private boolean isEnough;
    private String comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;
    @OneToMany(mappedBy = "taskEqm", cascade = CascadeType.ALL)
    private List<TaskEqmRatingItem> taskEqmRatingItems;

    @ManyToOne
    @JoinColumn(name = "lk_task_eqm_type_id")
    private LkTaskEqmType taskEqmType;

    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "lk_task_eqm_status_id")
    private LkTaskEqmStatus taskEqmStatus;


    public void addTaskEqmRatingItems(TaskEqmRatingItem t) {
        if (taskEqmRatingItems == null) {
            taskEqmRatingItems = new ArrayList<>();
        }
        t.setTaskEqm(this);
        taskEqmRatingItems.add(t);
    }

}
