package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Entity.SysDept;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDeptMapper {

    @Delete("delete from sys_dept where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(@Param("id") Integer id);

    @Insert("insert into sys_dept (id, name, parent_id, \n" +
            "      level, seq, remark, \n" +
            "      operator, operate_time, operate_ip\n" +
            "      )\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{parentId,jdbcType=INTEGER}, \n" +
            "      #{level,jdbcType=VARCHAR}, #{seq,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, \n" +
            "      #{operator,jdbcType=VARCHAR}, #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR}\n" +
            "      )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysDept record);

    @Insert("<script>"
            + " insert into sys_dept"
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
            + " #{parentId,jdbcType=VARCHAR},"
            + "</if>"
            + "<if test='level != null'>"
            + "  #{level,jdbcType=VARCHAR},"
            + " </if>"
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
    int insertSelective(SysDept record);

    @Select("select *  from sys_dept where id = #{id,jdbcType=INTEGER}")
    SysDept selectByPrimaryKey(@Param("id") Integer id);

    @Update("<script>"
            + " update sys_dept"
            + " <set>"
            + "     <if test='name != null'>"
            + "       name = #{name,jdbcType=VARCHAR},"
            + "     </if>"
            + "     <if test='parentId != null'>"
            + "        parent_id = #{parentId,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='level != null'>"
            + "       url = #{level,jdbcType=VARCHAR},"
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
    int updateByPrimaryKeySelective(SysDept record);

    @Update(" update sys_dept\n" +
            "    set name = #{name,jdbcType=VARCHAR},\n" +
            "      parent_id = #{parentId,jdbcType=INTEGER},\n" +
            "      level = #{level,jdbcType=VARCHAR},\n" +
            "      seq = #{seq,jdbcType=INTEGER},\n" +
            "      remark = #{remark,jdbcType=VARCHAR},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysDept record);

    @Select("select *  from sys_dept")
    List<SysDept> getAllDept();

    @Select("select * from sys_dept where level like #{level} || '.%'")
    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    @Update("<script>"
            + "<foreach collection='sysDeptList' item='sysDept' separator=';'>"
            + "update sys_dept"
            + " SET level = #{dept.level} "
            + " WHERE id = #{dept.id}"
            + "</foreach>"
            + "</script>")
    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);

    @Select("<script>" +
            "SELECT count(1) FROM sys_dept" +
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

    @Select("SELECT count(1) FROM sys_dept WHERE parent_id = #{deptId}")
    int countByParentId(@Param("deptId") int deptId);


}