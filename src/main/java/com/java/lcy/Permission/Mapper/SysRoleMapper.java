package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper {
    @Delete("delete from sys_role where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into sys_role (id, name, type, \n" +
            "      status, remark, operator, \n" +
            "      operate_time, operate_ip)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, \n" +
            "      #{status,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, #{operator,jdbcType=VARCHAR}, \n" +
            "      #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysRole record);

    @Insert("<script>"
            + " insert into sys_role"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test='name != null'>"
            + "        name,"
            + "     </if>"
            + "     <if test='type != null'>"
            + "         type,"
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
            + "<if test='type != null'>"
            + " #{type,jdbcType=INTEGER},"
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
    int insertSelective(SysRole record);

    @Select("select *  from sys_role  where id = #{id,jdbcType=INTEGER}")
    SysRole selectByPrimaryKey(Integer id);

    @Update("<script>"
            + " update sys_role"
            + " <set>"
            + "     <if test='name != null'>"
            + "       name = #{name,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='type != null'>"
            + "        type = #{type,jdbcType=INTEGER},"
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
    int updateByPrimaryKeySelective(SysRole record);

    @Update("  update sys_role\n" +
            "    set name = #{name,jdbcType=VARCHAR},\n" +
            "      type = #{type,jdbcType=INTEGER},\n" +
            "      status = #{status,jdbcType=INTEGER},\n" +
            "      remark = #{remark,jdbcType=VARCHAR},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysRole record);

    @Select("<script>" +
            " SELECT count(1)\n" +
            "    FROM sys_role\n" +
            "    WHERE name = #{name}\n" +
            "    <if test=\"id != null\">\n" +
            "      AND id != #{id}\n" +
            "    </if>\n" +
            "</script>")
    int countByName(@Param("name") String name, @Param("id") Integer id);


    @Select("select * from sys_role")
    List<SysRole> getAll();

    @Select("<script>" +
            " select * FROM sys_role  WHERE id IN " +
            "    <foreach collection='idList' item='id' open='(' close=')' separator=','>\n" +
            "      #{id}\n" +
            "    </foreach>\n"+
            "</script>")
    List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}