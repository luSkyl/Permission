package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysAclModule;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclModuleMapper {

    @Delete("delete from sys_acl_module  where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert(" insert into sys_acl_module (id, name, parent_id, \n" +
            "      level, seq, status, \n" +
            "      remark, operator, operate_time, \n" +
            "      operate_ip)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{parentId,jdbcType=INTEGER}, \n" +
            "      #{level,jdbcType=VARCHAR}, #{seq,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, \n" +
            "      #{remark,jdbcType=VARCHAR}, #{operator,jdbcType=VARCHAR}, #{operateTime,jdbcType=TIMESTAMP}, \n" +
            "      #{operateIp,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysAclModule record);

    @Insert("<script>"
            + " insert into sys_acl_module"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test='name != null'>"
            + "        name,"
            + "     </if>"
            + "     <if test='parentId != null'>"
            + "         parent_id,"
            + "     </if>"
            + "     <if test='level != null'>"
            + "        level,"
            + "     </if>"
            + "     <if test='seq != null'>"
            + "        seq,"
            + "     </if>"
            + "     <if test='status != null'>"
            + "        status,"
            + "     </if>"
            + "     <if test='remark != null'>"
            + "        remark,"
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
            + "<if test='name != null'>"
            + "#{name,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='parentId != null'>"
            + " #{parentId,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='level != null'>"
            + "  #{level,jdbcType=VARCHAR},"
            + " </if>"
            + "<if test='seq != null'>"
            + " #{seq,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='status != null'>"
            + " #{status,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='remark != null'>"
            + " #{remark,jdbcType=VARCHAR},"
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
    int insertSelective(SysAclModule record);

    @Select("select * from sys_acl_module where id = #{id,jdbcType=INTEGER}")
    SysAclModule selectByPrimaryKey(Integer id);

    @Update("<script>"
            + " update sys_acl_module"
            + " <set>"
            + "     <if test='name != null'>"
            + "       name = #{name,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='parentId != null'>"
            + "        parent_id = #{parentId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='level != null'>"
            + "       level = #{level,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='seq != null'>"
            + "         seq = #{seq,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='status != null'>"
            + "      status = #{status,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='remark != null'>"
            + "      remark = #{remark,jdbcType=VARCHAR},"
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
    int updateByPrimaryKeySelective(SysAclModule record);

    @Update(" update sys_acl_module\n" +
            "    set name = #{name,jdbcType=VARCHAR},\n" +
            "      parent_id = #{parentId,jdbcType=INTEGER},\n" +
            "      level = #{level,jdbcType=VARCHAR},\n" +
            "      seq = #{seq,jdbcType=INTEGER},\n" +
            "      status = #{status,jdbcType=INTEGER},\n" +
            "      remark = #{remark,jdbcType=VARCHAR},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysAclModule record);

    @Select("select * from sys_acl_module WHERE level like #{level} || '.%'")
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);

    @Update("<script>" +
            "<foreach collection='sysAclModuleList' item='sysAclModule' separator=';'>" +
            "      UPDATE sys_acl_module\n" +
            "      SET level = #{sysAclModule.level}\n" +
            "      WHERE id = #{sysAclModule.id}\n" +
            "    </foreach>\n" +
            "</script>")
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    @Select("<script>" +
            "SELECT count(1) FROM sys_acl_module" +
            " <where>" +
            "     name = #{name}" +
            "    <if test='parentId != null'>" +
            "      AND parent_id = #{parentId}" +
            "    </if>" +
            "    <if test='id != null'>" +
            "      AND id != #{id}" +
            "    </if>" +
            "</where>" +
            "</script>")
    int countByNameAndParentId(@Param("parentId") int parentId, @Param("name") String name, @Param("id") Integer id);

    @Select("SELECT * FROM sys_acl_module")
    List<SysAclModule> getAllAclModule();

    @Select("SELECT count(1) FROM sys_acl_module WHERE parent_id = #{aclModuleId}")
    int countByParentId(@Param("aclModuleId") Integer aclModuleId);
}