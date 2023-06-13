package com.puff.service.impl;

import com.puff.entity.Admin;
import com.puff.mapper.AdminMapper;
import com.puff.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author puff
 * @since 2023-06-13
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
