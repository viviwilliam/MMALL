package com.viviwilliam.mmall.service.impl;

import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.mapper.UserMapper;
import com.viviwilliam.mmall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
