package com.lxf.springsecurity.entity;

import com.lxf.springsecurity.base.BaseEntity;
import lombok.Data;

/**
 * 字典类型表 sys_dict_type
 * 
 * @author ruoyi
 */
@Data
public class SysDictType extends BaseEntity
{

    /** 字典主键 */
    private Long dictId;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 *
    private String dictType;

    /** 状态（0正常 1停用） */
    private String status;

}
