package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysAcl;
import com.java.lcy.Permission.Param.PageQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclMapper {

    @Delete(" delete from sys_acl where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(@Param("id") Integer id);

    @Insert("insert into sys_acl (id, code, name, acl_module_id, url, type, \n" +
            "status, seq, remark, operator, operate_time, operate_ip)\n" +
            "values (#{id,jdbcType=INTEGER}, #{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, \n" +
            "#{aclModuleId,jdbcType=INTEGER}, #{url,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, \n" +
            "#{status,jdbcType=INTEGER}, #{seq,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, \n" +
            "#{operator,jdbcType=VARCHAR}, #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR}\n" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysAcl record);

    @Insert("<script>"
            + " insert into sys_acl"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test = 'code != null' >"
            + "       code,"
            + "     </if>"
            + "     <if test='name != null'>"
            + "        name,"
            + "     </if>"
            + "     <if test='aclModuleId != null'>"
            + "        acl_module_id,"
            + "     </if>"
            + "     <if test='url != null'>"
            + "        url,"
            + "     </if>"
            + "     <if test='type != null'>"
            + "        type,"
            + "     </if>"
            + "     <if test='status != null'>"
            + "        status,"
            + "     </if>"
            + "     <if test='seq != null'>"
            + "        seq,"
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
            + "<if test='code != null'>"
            + "#{code,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='name != null'>"
            + " #{name,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='aclModuleId != null'>"
            + "#{aclModuleId,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='url != null'>"
            + "  #{url,jdbcType=VARCHAR},"
            + " </if>"
            + " <if test='type != null'>"
            + "#{type,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='status != null'>"
            + " #{status,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='seq != null'>"
            + " #{seq,jdbcType=INTEGER},"
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
    int insertSelective(SysAcl record);

    @Select("select * from sys_acl where id = #{id,jdbcType=INTEGER}")
    SysAcl selectByPrimaryKey(@Param("id") Integer id);

    @Update("<script>"
            + " update sys_acl"
            + " <set>"
            + "     <if test = 'code != null' >"
            + "       code = #{code,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='name != null'>"
            + "       name = #{name,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='aclModuleId != null'>"
            + "        acl_module_id = #{aclModuleId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='url != null'>"
            + "       url = #{url,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='type != null'>"
            + "         type = #{type,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='status != null'>"
            + "      status = #{status,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='seq != null'>"
            + "         seq = #{seq,jdbcType=INTEGER},"
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
    int updateByPrimaryKeySelective(SysAcl record);

    @Update(" update sys_acl\n" +
            "    set code = #{code,jdbcType=VARCHAR},\n" +
            "      name = #{name,jdbcType=VARCHAR},\n" +
            "      acl_module_id = #{aclModuleId,jdbcType=INTEGER},\n" +
            "      url = #{url,jdbcType=VARCHAR},\n" +
            "      type = #{type,jdbcType=INTEGER},\n" +
            "      status = #{status,jdbcType=INTEGER},\n" +
            "      seq = #{seq,jdbcType=INTEGER},\n" +
            "      remark = #{remark,jdbcType=VARCHAR},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysAcl record);

    @Select(" SELECT count(1) FROM sys_acl WHERE acl_module_id = #{aclModuleId}")
    int countByAclModuleId(@Param("aclModuleId") Integer aclModuleId);

    @Select("<script>" +
            "    SELECT count(1) FROM sys_acl WHERE acl_module_id = #{aclModuleId} AND name = #{name}\n" +
            "    <when test='id != null'>\n" +
            "      AND id != #{id}\n" +
            "    </when>\n" +
            "</script>")
    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    @Select("SELECT * FROM sys_acl WHERE acl_module_id = #{aclModuleId} ORDER BY seq ASC, name ASC limit #{page.offset}, #{page.pageSize}")
    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);

    @Select("select * from sys_acl")
    List<SysAcl> getAll();

    @Select("<script>" +
            "SELECT * FROM sys_acl WHERE id IN" +
            "<foreach collection='idList' item='id' open='(' close=')' separator=','>" +
            " #{id}" +
            " </foreach>" +
            "</script>")
    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

    @Select("select * FROM sys_acl WHERE url = #{url}")
    List<SysAcl> getByUrl(String url);
}