package com.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.entity.SysUser;
import com.security.mapper.SysUserMapper;
import com.security.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

}
