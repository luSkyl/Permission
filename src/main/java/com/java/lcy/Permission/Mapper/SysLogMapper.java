package com.java.lcy.Permission.Mapper;


import com.java.lcy.Permission.Dto.SearchLogDto;
import com.java.lcy.Permission.Entity.SysLog;
import com.java.lcy.Permission.Entity.SysLogWithBLOBs;
import com.java.lcy.Permission.Param.PageQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysLogMapper {
    @Delete("delete from sys_log where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into sys_log (id, type, target_id, \n" +
            "      operator, operate_time, operate_ip, \n" +
            "      status, old_value, new_value\n" +
            "      )\n" +
            "    values (#{id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{targetId,jdbcType=INTEGER}, \n" +
            "      #{operator,jdbcType=VARCHAR}, #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR}, \n" +
            "      #{status,jdbcType=INTEGER}, #{oldValue,jdbcType=LONGVARCHAR}, #{newValue,jdbcType=LONGVARCHAR}\n" +
            "      )")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysLogWithBLOBs record);

    @Insert("<script>"
            + " insert into sys_log"
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + " 	<if test='id != null' > "
            + "	       id,"
            + " 	</if>"
            + "     <if test='type != null'>"
            + "        type,"
            + "     </if>"
            + "     <if test='targetId != null'>"
            + "         target_id,"
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
            + "     <if test='status != null'>"
            + "        status,"
            + "     </if>"
            + "     <if test='oldValue != null'>"
            + "        old_value,"
            + "     </if>"
            + "     <if test='newValue != null'>"
            + "        new_value,"
            + "     </if>"
            + "</trim>"
            + "<trim prefix='values (' suffix=')' suffixOverrides=','>"
            + "<if test='id != null'>"
            + "#{id,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='type != null'>"
            + "#{type,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='targetId != null'>"
            + "#{targetId,jdbcType=INTEGER},"
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
            + "<if test='status != null'>"
            + " #{status,jdbcType=INTEGER},"
            + "</if>"
            + "<if test='oldValue != null'>"
            + "  #{oldValue,jdbcType=LONGVARCHAR},"
            + " </if>"
            + "<if test='newValue != null'>"
            + "  #{newValue,jdbcType=LONGVARCHAR},"
            + "</if>"
            + "</trim>"
            + "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective(SysLogWithBLOBs record);

    @Select("select * from  sys_log where id = #{id,jdbcType=INTEGER}")
    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    @Update("<script>"
            + " update sys_log"
            + " <set>"
            + "     <if test='type != null'>"
            + "        type = #{type,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='targetId != null'>"
            + "      target_id = #{targetId,jdbcType=INTEGER},"
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
            + "     <if test='status != null'>"
            + "      status = #{status,jdbcType=INTEGER},"
            + "     </if>"
            + "     <if test='oldValue != null'>"
            + "         old_value = #{oldValue,jdbcType=LONGVARCHAR},"
            + "     </if>"
            + "     <if test='newValue != null'>"
            + "       new_value = #{newValue,jdbcType=LONGVARCHAR},"
            + "     </if>"
            + "</set>"
            + " where id = #{id,jdbcType=INTEGER}"
            + "</script>")
    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    @Update("    update sys_log\n" +
            "    set type = #{type,jdbcType=INTEGER},\n" +
            "      target_id = #{targetId,jdbcType=INTEGER},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR},\n" +
            "      status = #{status,jdbcType=INTEGER},\n" +
            "      old_value = #{oldValue,jdbcType=LONGVARCHAR},\n" +
            "      new_value = #{newValue,jdbcType=LONGVARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    @Update(" update sys_log\n" +
            "    set type = #{type,jdbcType=INTEGER},\n" +
            "      target_id = #{targetId,jdbcType=INTEGER},\n" +
            "      operator = #{operator,jdbcType=VARCHAR},\n" +
            "      operate_time = #{operateTime,jdbcType=TIMESTAMP},\n" +
            "      operate_ip = #{operateIp,jdbcType=VARCHAR},\n" +
            "      status = #{status,jdbcType=INTEGER}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(SysLog record);

    @Select("<script>" +
            "SELECT count(1) FROM sys_log" +
            "      <where>\n" +
            "      <if test='dto.type != null'>\n" +
            "        AND type = #{dto.type}\n" +
            "      </if>\n" +
            "      <if test=\"dto.beforeSeg != null and dto.beforeSeg != ''\">\n" +
            "        AND old_value like #{dto.beforeSeg}\n" +
            "      </if>\n" +
            "      <if test=\"dto.afterSeg != null and dto.afterSeg != ''\">\n" +
            "        AND new_value like #{dto.afterSeg}\n" +
            "      </if>\n" +
            "      <if test=\"dto.operator != null and dto.operator != ''\">\n" +
            "        AND operator like #{dto.operator}\n" +
            "      </if>\n" +
            "      <if test=\"dto.fromTime != null\">\n" +
            "        AND operate_time &gt;= #{dto.fromTime}\n" +
            "      </if>\n" +
            "      <if test=\"dto.toTime != null\">\n" +
            "        AND operate_time &lt;= #{dto.toTime}\n" +
            "      </if>\n" +
            "    </where>" +
            "</script>")
    int countBySearchDto(@Param("dto") SearchLogDto dto);

    @Select("<script>" +
            "select *  FROM sys_log" +
            " <where>\n" +
            "      <if test=\"dto.type != null\">\n" +
            "        AND type = #{dto.type}\n" +
            "      </if>\n" +
            "      <if test=\"dto.beforeSeg != null and dto.beforeSeg != ''\">\n" +
            "        AND old_value like #{dto.beforeSeg}\n" +
            "      </if>\n" +
            "      <if test=\"dto.afterSeg != null and dto.afterSeg != ''\">\n" +
            "        AND new_value like #{dto.afterSeg}\n" +
            "      </if>\n" +
            "      <if test=\"dto.operator != null and dto.operator != ''\">\n" +
            "        AND operator like #{dto.operator}\n" +
            "      </if>\n" +
            "      <if test=\"dto.fromTime != null\">\n" +
            "        AND operate_time &gt;= #{dto.fromTime}\n" +
            "      </if>\n" +
            "      <if test=\"dto.toTime != null\">\n" +
            "        AND operate_time &lt;= #{dto.toTime}\n" +
            "      </if>\n" +
            "    </where>" +
            "   ORDER BY operate_time DESC\n" +
            "    limit #{page.offset}, #{page.pageSize}" +
            "</script>")
    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);
}