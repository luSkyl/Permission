package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysRoleUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleUserMapper {
    @Delete("delete from sys_role_user where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert(" insert into sys_role_user (id, role_id, user_id, \n" +
            "      operator, operate_time, operate_ip\n" +
            "      )\n" +
            "    values (#{id,jdbcType=INTEGER}, #{roleId,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, \n" +
            "      #{operator,jdbcType=VARCHAR}, #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR}\n" +
            "      )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysRoleUser record);

    @Insert("<script>"
            + " insert into sys_role_user"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test='roleId != null'>"
            + "        role_id,"
            + "     </if>"
            + "     <if test='userId != null'>"
            + "         user_id,"
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
            + "<if test='userId != null'>"
            + "#{userId,jdbcType=INTEGER},"
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
    int insertSelective(SysRoleUser record);

    @Select("select *   from sys_role_user where id = #{id,jdbcType=INTEGER}")
    SysRoleUser selectByPrimaryKey(Integer id);

    @Update("<script>"
            + " update sys_role_user"
            + " <set>"
            + "     <if test='roleId != null'>"
            + "        role_id = #{roleId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='userId != null'>"
            + "      user_id = #{userId,jdbcType=INTEGER},"
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
    int updateByPrimaryKeySelective(SysRoleUser record);

    @Update("update sys_role_user\n" +
            "    set role_id = #{roleId,jdbcType=INTEGER},\n" +
            "      user_id = #{userId,jdbcType=INTEGER},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysRoleUser record);

    @Select("SELECT role_id FROM sys_role_user WHERE user_id = #{userId}")
    List<Integer> getRoleIdListByUserId(@Param("userId") int userId);

    @Select("<script>" +
            "SELECT user_id\n" +
            "    FROM sys_role_user\n" +
            "    WHERE role_id IN\n" +
            "    <foreach collection='roleIdList' item='roleId' open='(' close=')' separator=','>\n" +
            "      #{roleId}\n" +
            "    </foreach>" +
            "</script>")
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    @Select("SELECT user_id FROM sys_role_user WHERE role_id = #{roleId}")
    List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);

    @Delete("DELETE FROM sys_role_user WHERE role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") int roleId);

    @Insert("<script>" +
            "insert into sys_role_user (role_id, user_id, operator, operate_time, operate_ip) values" +
            " <foreach collection='roleUserList' item='roleUser' separator=','>\n" +
            "      ( #{roleUser.roleId}, #{roleUser.userId}, #{roleUser.operator}, #{roleUser.operateTime}, #{roleUser.operateIp})\n" +
            "</foreach>"+
            "</script>")
    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);
}