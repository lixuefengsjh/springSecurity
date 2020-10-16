package com.lxf.springsecurity.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色和部门关联 sys_role_dept
 * 
 * @author ruoyi
 */
@Data
public class SysRoleDept implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 角色ID */
    private Long roleId;
    
    /** 部门ID */
    private Long deptId;

}
