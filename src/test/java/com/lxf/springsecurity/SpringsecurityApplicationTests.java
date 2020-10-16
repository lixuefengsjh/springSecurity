package com.lxf.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class SpringsecurityApplicationTests {
    @Autowired
    private ProcessRuntime processRuntime;

    /** 流程运行时相关的服务 */
    @Autowired
    private RuntimeService runtimeService;

    /** 节点任务相关操作接口 */
    @Autowired
    private TaskService taskService;

    /** 流程定义和部署相关的存储服务 */
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Test
    void contextLoads() {
    }
    //测试流程部署
    @Test
    public void  deploy(){
        /**
         * 部署流程受影响得表为：
         * act_ge_property  流程属性表----一直有数据
         *  act_ge_bytearray 讲绘制对的二进制文件录入
         *  act_re_deployment  流程部署信息：流程什么时候部署
         *  act_re_procedef    流程部署执行信息：流程部署和运行相关得信息
         */
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/fincalReportProcessbpmn.bpmn20.bpmn")
                .addClasspathResource("processes/fincalReportProcess.png")
                .name("财务报销流程")
                .disableSchemaValidation()
                .deploy();
        log.info("流程对应的名字为:---"+deployment.getName());
        log.info("流程对应的id为:---"+deployment.getId());
    }
    //
    //

    /**
     * 测试启动流程
     * 涉及到得表为：
     * act_hi_actinst :历史节点表，流程执行已经执行(包含未执行完毕得节点)得所有得节点，执行人，任务到达时间，执行完成时间，节点经历得时间
     * act_hi_detail :历史详情表，存放经执行得所有节点各个参数信息
     *  act_hi_varinst ：历史任务参数相关信息表
     * act_hi_identitylink：历史执行流程人员信息：存放任务节点执行人信息
     * act_hi_procinst ：历史流程实列表，最要记录流程走到那个节点了
     * act_hi_taskinst： 历史任务实列表，主要记录流程得任务节点得信息
     * act_ru_execution
     * act_ru_identitylink
     * act_ru_task
     * act_ru_variable

      */
    @Test
    public void start(){
        String processKey="finacalProcess";
        //启动任务时设置第一个任务节点得处理人,这个通常从session中获取
        Map<String ,Object > varis= new HashMap<>();
        varis.put("reporter","宋金桧");
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(processKey,varis);
        log.info("流程启动，对应的流程实列id为:"+processInstance.getId());
    }
    //根据用户组获取任务
    @Test
    public  void queryTasker(){
        String assigned ="lxf";
       List<Task> tasks=taskService.createTaskQuery().taskCandidateOrAssigned(assigned).orderByTaskCreateTime().desc().list();
        for(Task task:tasks ){
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务的创建时间:" + task.getCreateTime());
            System.out.println("任务的办理人:" + task.getAssignee());
            System.out.println("流程实例ID：" + task.getProcessInstanceId());
            System.out.println("执行对象ID:" + task.getExecutionId());
            System.out.println("流程定义ID:" + task.getProcessDefinitionId());
        };
    }
    //用户执行下一步

    /**
     *（1）变量定义#{sum}和${sum}都可以
     * (2)执行任务涉及到得数据变化为：
     * act_hi_comment 历史意见表
     */
    @Test
    public void excuteTask(){
        String taskId="57af711c-0f83-11eb-b092-005056c00008";
        taskService.addComment(taskId,"57ab0446-0f83-11eb-b092-005056c00008","宋金桧商报评估完毕----------");
        Map<String,Object> num=new HashMap<>();
        num.put("num",10);
        taskService.complete(taskId,num);
        taskService.complete(taskId);
    }
    //查询流程是否执行完毕
    @Test
    public void iscomplate(){
        String processInstanceId="05171cca-0f6b-11eb-b83b-005056c00008";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(null!=processInstance){
            System.out.println("processInstanceId :"+processInstanceId +",流程还未执行完毕");
        }else {
            System.out.println("processInstanceId :"+processInstanceId +",流程已经完毕");
        }
    }
    //流程回退功能

}
