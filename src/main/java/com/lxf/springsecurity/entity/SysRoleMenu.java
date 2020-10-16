package com.lxf.springsecurity.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author ruoyi
 */
@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;


}
