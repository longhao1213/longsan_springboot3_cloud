package com.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ss_419
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-03-03 10:41:42
* @Entity com.security.mapper.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
