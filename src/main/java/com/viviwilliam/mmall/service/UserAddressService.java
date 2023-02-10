package com.viviwilliam.mmall.service;

import com.viviwilliam.mmall.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.viviwilliam.mmall.vo.orderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
public interface UserAddressService extends IService<UserAddress> {

    public List<UserAddress> findMyAddress(Integer id);

}
