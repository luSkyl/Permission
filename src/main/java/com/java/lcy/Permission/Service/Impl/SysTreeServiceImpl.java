package com.java.lcy.Permission.Service.Impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.java.lcy.Permission.Dto.AclDto;
import com.java.lcy.Permission.Dto.AclModuleLevelDto;
import com.java.lcy.Permission.Dto.DeptLevelDto;
import com.java.lcy.Permission.Entity.SysAcl;
import com.java.lcy.Permission.Entity.SysAclModule;
import com.java.lcy.Permission.Entity.SysDept;
import com.java.lcy.Permission.Mapper.SysAclMapper;
import com.java.lcy.Permission.Mapper.SysAclModuleMapper;
import com.java.lcy.Permission.Mapper.SysDeptMapper;
import com.java.lcy.Permission.Service.SysCoreServcie;
import com.java.lcy.Permission.Service.SysTreeService;
import com.java.lcy.Permission.Util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysTreeServiceImpl implements SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysCoreServcie sysCoreServcie;
    @Resource
    private SysAclMapper sysAclMapper;



    @Override
    public List<DeptLevelDto> deptTree() {
        ArrayList<DeptLevelDto> deptLevelDtoArrayList = Lists.newArrayList();
        sysDeptMapper.getAllDept().stream().forEach(e -> deptLevelDtoArrayList.add(DeptLevelDto.adapt(e)));
        return deptListToTree(deptLevelDtoArrayList);
    }

    @Override
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> allAclModuleList = sysAclModuleMapper.getAllAclModule();
        ArrayList<AclModuleLevelDto> aclModuleLevelDtoList = Lists.newArrayList();
        for (SysAclModule sysAclModule : allAclModuleList) {
            aclModuleLevelDtoList.add(AclModuleLevelDto.adapt(sysAclModule));
        }

        return aclModuleListToTree(aclModuleLevelDtoList);
    }

    @Override
    public List<AclModuleLevelDto> roleTree(int roleId) {
        //当前用户已分配的权限点
        List<SysAcl> currentUserAclList = sysCoreServcie.getCurrentUserAclList();
        //当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreServcie.getRoleAclList(roleId);
          //当前系统所有的权限点
        List<SysAcl> allAclList = sysAclMapper.getAll();
        Set<Integer> userAclIdSet = currentUserAclList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclSet = roleAclList.stream().map(e -> e.getId()).collect(Collectors.toSet());


        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : allAclList) {
            AclDto aclDto = AclDto.adapt(sysAcl);
            if (userAclIdSet.contains(sysAcl.getId())) {
                aclDto.setHasAcl(true);
            }
            if (roleAclSet.contains(sysAcl.getId())) {
                aclDto.setChecked(true);
            }
            aclDtoList.add(aclDto);
        }
        return aclListToTree(aclDtoList);
    }

    @Override
    public List<AclModuleLevelDto> userAclTree(int userId) {
        List<SysAcl> userAclList = sysCoreServcie.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl acl : userAclList) {
            AclDto dto = AclDto.adapt(acl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    private List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            log.warn("【权限列表转化树形结构】 权限转化列表树形结构失败 权限列表为空 aclDtoList={}", aclDtoList);
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();
        //aclModuleId,AclDto
        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    private List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelDtoList) {
        if (CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
            log.warn("【权限模块列表转化树形结构】 权限模块转化列表树形结构失败 权限模块列表为空 aclModuleLevelDtoList={}", aclModuleLevelDtoList);
            return Lists.newArrayList();
        }
        ArrayListMultimap<String, AclModuleLevelDto> levelDtoArrayListMultimap = ArrayListMultimap.create();
        ArrayList<AclModuleLevelDto> rootList = Lists.newArrayList();
        for (AclModuleLevelDto aclModuleLevelDto : aclModuleLevelDtoList) {
            levelDtoArrayListMultimap.put(aclModuleLevelDto.getLevel(), aclModuleLevelDto);
            if (LevelUtil.ROOT.equals(aclModuleLevelDto.getLevel())) {
                rootList.add(aclModuleLevelDto);
            }
        }
        Collections.sort(rootList, aclModuleSeqComparator);
        transformAclMoudleTree(rootList, LevelUtil.ROOT, levelDtoArrayListMultimap);
        return rootList;
    }

    private void transformAclMoudleTree(List<AclModuleLevelDto> rootList, String level, ArrayListMultimap<String, AclModuleLevelDto> levelDtoArrayListMultimap) {
        for (AclModuleLevelDto aclModuleLevelDto : rootList) {
            String nextLevel = LevelUtil.calculateLevel(level, aclModuleLevelDto.getId());
            List<AclModuleLevelDto> aclModuleLevelDtos = levelDtoArrayListMultimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(aclModuleLevelDtos)) {
                Collections.sort(aclModuleLevelDtos, aclModuleSeqComparator);
                aclModuleLevelDto.setAclModuleList(aclModuleLevelDtos);
                transformAclMoudleTree(aclModuleLevelDtos, nextLevel, levelDtoArrayListMultimap);
            }
        }
    }

    private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (deptLevelDtoList.isEmpty()) {
            log.warn("【部门列表转化树形结构】 部门列表转化树形结构失败 部门列表为空  deptLevelDtoList={}", deptLevelDtoList);
            return Lists.newArrayList();
        }
        //level,[]d
        Multimap<String, DeptLevelDto> levelDtoMultimap = ArrayListMultimap.create();
        ArrayList<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            levelDtoMultimap.put(deptLevelDto.getLevel(), deptLevelDto);
            if (LevelUtil.ROOT.equals(deptLevelDto.getLevel())) {
                rootList.add(deptLevelDto);
            }
        }
        Collections.sort(rootList, deptSeqComparator);
        transformDeptTree(rootList, LevelUtil.ROOT, levelDtoMultimap);
        return rootList;
    }

    private void transformDeptTree(List<DeptLevelDto> deptLevelDtoList, String level, Multimap<String, DeptLevelDto> levelDtoMultimap) {

        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<DeptLevelDto> deptLevelDtos = (List<DeptLevelDto>) levelDtoMultimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(deptLevelDtos)) {
                Collections.sort(deptLevelDtos, deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(deptLevelDtos);
                //进入到下一层进行处理
                transformDeptTree(deptLevelDtos, nextLevel, levelDtoMultimap);
            }
        }
    }

    private void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, aclSeqComparator);
                dto.setAclDtoList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    private Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    private Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


}
