package com.java.lcy.Permission.Enum;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    SUCCESS(0,"成功"),
    PARAM_EXCEPTION(1,"参数异常"),
    DEPT_REPEATION(2,"同一层级下部门重复"),
    UP_DEPT_NOT_EXIST(3,"待更新的部门不存在"),
    DELETE_DEPT_NOT_EXIST(4,"待删除的部门不存在，无法删除"),
    TELEPHONE_REPEAT(5,"电话已被占用"),
    EMAIL_REPEAT(6,"邮箱已被占用"),
    CURRENT_DEPT_HAS_SUBDEPTS(7,"当前部门下面有子部门，无法删除"),
    CURRENT_DEPT_HAS_USERS(8,"当前部门下面有用户，无法删除"),
    USERNAME_CANNOT_BE_EMPTY(9, "用户名不可以为空"),
    PASSWORD_CANNOT_BE_EMPTY(10, "密码不可以为空"),
    USER_NOT_EXIST(11, "查询不到指定的用户"),
    USERNAME_OR_PASSWORD_ERROR(12,"用户名或密码错误"),
    USER_BE_FROZEN(13,"用户已被冻结，请联系管理员"),
    ACL_MOUDLE_REPEATION(14,"同一层级下存在相同名称的权限模块"),
    UP_ACL_MODULE_NOT_EXIST(15,"待更新的权限模块不存在"),
    DELETE_ACL_MODULE_NOT_EXIST(16,"待删除的权限模块不存在，无法删除"),
    CURRENT_ACL_MODULE_HAS_SUBMODULES(17,"当前模块下面有子模块，无法删除"),
    CURRENT_MODULE_HAS_USERS(18,"当前模块下面有用户，无法删除"),
    ACL_REPEATION(19,"当前权限模块下面存在相同名称的权限点"),
    UPDATE_ACL_NOT_EXIST(20,"待更新的权限点不存在"),
    ROLE_REPEATION(21,"角色名称已经存在"),
    UPDATE_ROLE_NOT_EXIST(22,"待更新的角色不存在"),
    RESTORE_RECORD_NOT_EXIST(23,"待还原的记录不存在"),
    RESTORE_DEPT_NOT_EXIST(24,"待还原的部门已经不存在了"),
    SAVE_AND_UPDATE_CANNOT_RESTORE(25,"新增和删除操作不做还原"),
    ROLE_NOT_EXIST(26,"角色已经不存在了"),
    RESTORE_USER_NOT_EXIST(27,"待还原的用户已经不存在了"),
    RESTORE_ACL_MODULE_NOT_EXIST(28,"待还原的权限模块已经不存在了"),
    RESTORE_ACL_NOT_EXIST(29,"待还原的权限点已经不存在了"),
    RESTORE_ROLE_NOT_EXIST(30,"待还原的角色已经不存在了"),
    DATE_FORMAT_ERROR(31,"传入的日期格式有问题，正确格式为：yyyy-MM-dd HH:mm:ss");
    ;



    private Integer code;
    private  String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
