package com.viviwilliam.mmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.UserAddress;
import com.viviwilliam.mmall.mapper.ProductMapper;
import com.viviwilliam.mmall.mapper.UserAddressMapper;
import com.viviwilliam.mmall.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;


    @Override
    public List<UserAddress> findMyAddress(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);
        List<UserAddress> addresses = userAddressMapper.selectList(wrapper);
        return addresses;
    }
}
