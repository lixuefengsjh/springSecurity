package com.lxf.springsecurity.activitiListner;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author: lxf
 * @create: 2020-10-14 14:30
 * @description: 动态设置候选人
 */
@Component
public class MyLeaderAssigee  implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("lxf");
    }
}
