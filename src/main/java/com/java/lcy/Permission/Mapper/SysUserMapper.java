package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Param.PageQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SysUserMapper {

    @Delete(" delete from sys_user where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert(" insert into sys_user (id, username, telephone, \n" +
            "      mail, password, dept_id, \n" +
            "      status, remark, operator, \n" +
            "      operate_time, operate_ip)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, #{telephone,jdbcType=VARCHAR}, \n" +
            "      #{mail,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{deptId,jdbcType=INTEGER}, \n" +
            "      #{status,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, #{operator,jdbcType=VARCHAR}, \n" +
            "      #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR})")
    int insert(SysUser record);

    @Insert("<script>"
            + " insert into sys_user"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test='username != null'>"
            + "        username,"
            + "     </if>"
            + "     <if test='telephone != null'>"
            + "         telephone,"
            + "     </if>"
            + "     <if test='mail != null'>"
            + "        mail,"
            + "     </if>"
            + "     <if test='password != null'>"
            + "        password,"
            + "     </if>"
            + "     <if test='deptId != null'>"
            + "        dept_id,"
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
            + "<if test='username != null'>"
            + "#{username,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='telephone != null'>"
            + "  #{telephone,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='mail != null'>"
            + "  #{mail,jdbcType=VARCHAR},"
            + " </if>"
            + "<if test='password != null'>"
            + "#{password,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='deptId != null'>"
            + "#{deptId,jdbcType=INTEGER},"
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
    int insertSelective(SysUser record);

    @Select("select * from sys_user where id = #{id,jdbcType=INTEGER}")
    SysUser selectByPrimaryKey(Integer id);

    @Update("<script>"
            + " update sys_user"
            + " <set>"
            + "     <if test='username != null'>"
            + "       username = #{username,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='telephone != null'>"
            + "        telephone = #{telephone,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='mail != null'>"
            + "         mail = #{mail,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='password != null'>"
            + "         password = #{password,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='deptId != null'>"
            + "       dept_id = #{deptId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='status != null'>"
            + "      status = #{status,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='remark != null'>"
            + "       remark = #{remark,jdbcType=VARCHAR},"
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
    int updateByPrimaryKeySelective(SysUser record);

    @Update(" update sys_user\n" +
            "    set username = #{username,jdbcType=VARCHAR},\n" +
            "      telephone = #{telephone,jdbcType=VARCHAR},\n" +
            "      mail = #{mail,jdbcType=VARCHAR},\n" +
            "      password = #{password,jdbcType=VARCHAR},\n" +
            "      dept_id = #{deptId,jdbcType=INTEGER},\n" +
            "      status = #{status,jdbcType=INTEGER},\n" +
            "      remark = #{remark,jdbcType=VARCHAR},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysUser record);

    @Select("<script>" +
            "SELECT count(1)\n" +
            "    FROM sys_user\n" +
            "    WHERE telephone = #{telephone}\n" +
            "    <when test='id != null'>\n" +
            "      AND id != #{id}\n" +
            "    </when>" +
            "</script>")
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    @Select("<script>" +
            "    SELECT count(1)\n" +
            "    FROM sys_user\n" +
            "    WHERE mail = #{mail}\n" +
            "    <if test='id != null'>\n" +
            "      AND id != #{id}\n" +
            "    </if>" +
            "</script>")
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    @Select("SELECT count(1) FROM sys_user WHERE dept_id = #{deptId}")
    int countByDeptId(@Param("deptId") int deptId);

    @Select("select *  FROM sys_user WHERE telephone = #{keyword} OR mail = #{keyword}")
    SysUser findByKeyword(@Param("keyword") String keyword);

    @Select("select *  FROM sys_user WHERE dept_id = #{deptId}  ORDER BY username ASC LIMIT #{page.offset}, #{page.pageSize}")
    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);

    @Select("<script>" +
            "select * FROM sys_user  WHERE id IN\n" +
            "<foreach collection='idList' item='id' open='(' close=')' separator=','>\n" +
            "      #{id}\n" +
            " </foreach>\n" +
            "</script>")
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    @Select("select * from sys_user")
    List<SysUser> getAll();
}