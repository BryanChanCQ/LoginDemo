package com.cqns.demo.web.listener;

import com.google.common.collect.Lists;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Author BryanChan
 * @Date 2019/5/27 10:15
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public class deptListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.addCandidateUsers(Lists.newArrayList("kjyw"));
    }
}
