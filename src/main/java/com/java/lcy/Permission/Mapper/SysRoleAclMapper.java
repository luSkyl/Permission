package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysRoleAcl;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleAclMapper {
    @Delete("delete from sys_role_acl where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert(" insert into sys_role_acl (id, role_id, acl_id, \n" +
            "      operator, operate_time, operate_ip\n" +
            "      )\n" +
            "    values (#{id,jdbcType=INTEGER}, #{roleId,jdbcType=INTEGER}, #{aclId,jdbcType=INTEGER}, \n" +
            "      #{operator,jdbcType=VARCHAR}, #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR}\n" +
            "      )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysRoleAcl record);

    @Insert("<script>"
            + " insert into sys_role_acl"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test='roleId != null'>"
            + "        role_id,"
            + "     </if>"
            + "     <if test='aclId != null'>"
            + "         acl_id,"
            + "     </if>"
            + "     <if test='operator != null'>"
            + "        operator,"
            + "     </if>"
            + "     <if test='operateTime != null'>"
            + "        operate_time,"
            + "     </if>"
            + "     <if test='operateIp != null'>"
            + "        operate_ip,"
            + "     </if>"
            + "</trim>"
            + "<trim prefix='values (' suffix=')' suffixOverrides=','>"
            + "<if test='id != null'>"
            + "#{id,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='roleId != null'>"
            + "#{roleId,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='aclId != null'>"
            + "#{aclId,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='operator != null'>"
            + " #{operator,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='operateTime != null'>"
            + "  #{operateTime,jdbcType=TIMESTAMP},"
            + " </if>"
            + " <if test='operateIp != null'>"
            + " #{operateIp,jdbcType=VARCHAR},"
            + " </if>"
            + "</trim>"
            + "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective(SysRoleAcl record);

    @Select("select * from sys_role_acl where id = #{id,jdbcType=INTEGER}")
    SysRoleAcl selectByPrimaryKey(Integer id);

    @Update("<script>"
            + " update sys_role_acl"
            + " <set>"
            + "     <if test='roleId != null'>"
            + "         role_id = #{roleId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='aclId != null'>"
            + "       acl_id = #{aclId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='operator != null'>"
            + "       operator = #{operator,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='operateTime != null'>"
            + "         operate_time = #{operateTime,jdbcType=TIMESTAMP},"
            + "     </if>"
            + "     <if test='operateIp != null'>"
            + "       operate_ip = #{operateIp,jdbcType=VARCHAR},"
            + "     </if>"
            + "</set>"
            + " where id = #{id,jdbcType=INTEGER}"
            + "</script>")
    int updateByPrimaryKeySelective(SysRoleAcl record);

    @Update("update sys_role_acl\n" +
            "    set role_id = #{roleId,jdbcType=INTEGER},\n" +
            "      acl_id = #{aclId,jdbcType=INTEGER},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysRoleAcl record);

    @Select("SELECT role_id FROM sys_role_acl  WHERE acl_id = #{aclId}")
    List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);

    @Select("<script>" +
            "    SELECT acl_id\n" +
            "    FROM sys_role_acl\n" +
            "    WHERE role_id in\n" +
            "      <foreach collection='roleIdList' item='roleId' open='(' close=')' separator=','>\n" +
            "        #{roleId}\n" +
            "      </foreach>\n" +
            "</script>")
    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    @Delete("DELETE FROM sys_role_acl WHERE role_id = #{roleId}")
    void deleteByRoleId(int roleId);

    @Insert("<script>" +
            "insert into sys_role_acl (role_id, acl_id, operator, operate_time, operate_ip) values "+
            " <foreach collection='roleAclList' item='roleAcl' separator=','>\n" +
            " (#{roleAcl.roleId}, #{roleAcl.aclId}, #{roleAcl.operator}, #{roleAcl.operateTime}, #{roleAcl.operateIp})\n" +
            " </foreach>"+
            "</script>")
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);
}