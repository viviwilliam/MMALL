package com.viviwilliam.mmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.*;
import com.viviwilliam.mmall.mapper.OrderDetailMapper;
import com.viviwilliam.mmall.mapper.OrderMapper;
import com.viviwilliam.mmall.mapper.ProductMapper;
import com.viviwilliam.mmall.service.OrderDetailService;
import com.viviwilliam.mmall.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viviwilliam.mmall.vo.ProductCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<Product> findByCategoryId(String type, Integer categoryId) {
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",categoryId);
        return productMapper.selectByMap(map);
    }
    @Override
    public List<Product> findAllProduct() {
        List<Product> products = productMapper.selectList(null);
        return products;
    }



    @Override
    public List<Product> getRecommendProductCategoryVO(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();

        //获取所有用户的订单信息,根据订单信息将商品与用户联系起来
        List<Orders> orderList = orderMapper.selectList(null);
        //首先需要获得用户以及购买的所有的商品，这是一个List<user>,一个List<List<productsVO>>
        List<Integer> allUser=new ArrayList<>();
        List<List<Integer>> allUserOrder = new ArrayList<>();
        int a=0;
        for(int i=0;i<orderList.size();i++){
            a = 0;
            for(int j=0;j<allUser.size();j++){
                if(orderList.get(i).getUserId()==allUser.get(j)){
                    a=1;
                    break;
                }
            }
            if(a==0){
                allUser.add(orderList.get(i).getUserId());
            }
        }
        int c=0;
        //检查加入的id有没有订单
        for(int i=0;i<allUser.size();i++){
            if(allUser.get(i) == id){
                c=1;
                allUser.set(i,allUser.get(0));
                allUser.set(0,id);
                break;
            }
        }
        //如果是新用户，就直接返回前排的三个
        if(c==0){
            wrapper = new QueryWrapper();
            wrapper.le("id",735);
            List<Product> recommend = productMapper.selectList(wrapper);
            return recommend;
        }


        for(int i=0;i<allUser.size();i++){
            allUserOrder.add(new ArrayList<Integer>());
        }
        //订单都加到了链表中
        for(int i=0;i<orderList.size();i++){
            for(int j=0;j<allUser.size();j++){
                if(orderList.get(i).getUserId()==allUser.get(j)){
                    allUserOrder.get(j).add(orderList.get(i).getId());
                    break;
                }
            }
        }

        //把每个人的所有订单详细信息取出了
        List<List<OrderDetail>> userDetail = new ArrayList<>();
        //每个用户
        for(int i=0;i<allUserOrder.size();i++){
            List<OrderDetail> orderDetails = new ArrayList<>();

            for(int j=0;j<allUserOrder.get(i).size();j++){
                wrapper = new QueryWrapper();
                wrapper.eq("order_Id",allUserOrder.get(i).get(j));

                OrderDetail orderDetail = (OrderDetail)orderDetailMapper.selectList(wrapper).get(0);
                orderDetails.add(orderDetail);
            }
            userDetail.add(orderDetails);
        }

        //设置每个人的用户商品表
        int b=0,p=0;
        List<List<Integer>> userProducts = new ArrayList<>();



        for(int i=0;i<userDetail.size();i++){
            List<Integer> list= new ArrayList<>();
            userProducts.add(list);
            for(int j=0;j<userDetail.get(i).size();j++){
                b=0;
                if(p!=0){
                    for(int z=0;z<userProducts.get(i).size();z++){


                        if(userProducts.get(i).get(z)==userDetail.get(i).get(j).getProductId()){
                            b=1;
                            break;
                        }
                    }
                }
                else {
                    p++;
                }

                if(b==0){
                    userProducts.get(i).add(userDetail.get(i).get(j).getProductId());
                }
            }
        }
        //所有的商品
        List<Integer> productId = new ArrayList<>();
        for(int i=0;i<userProducts.size();i++){
            for(int j=0;j<userProducts.get(i).size();j++){
                b=0;
                for(int z=0;z<productId.size();z++){
                    if(productId.get(z).equals(userProducts.get(i).get(j))){
                        b=1;
                        break;
                    }
                }
                if(b==0){
                    productId.add(userProducts.get(i).get(j));
                }
            }
        }
        //建立商品倒排索引表
        int[][] product_User = new int[productId.size()][allUser.size()];
        for(int i=0;i<productId.size();i++){
            for(int j=0;j<allUser.size();j++){
                product_User[i][j] = 0;
            }
        }
        for(int i=0;i<productId.size();i++){
            for(int j=0;j<allUser.size();j++){
                for(int z=0;z<userProducts.get(j).size();z++){
                    if(userProducts.get(j).get(z).equals(productId.get(i))){
                        product_User[i][j]=1;
                        break;
                    }
                }
            }
        }
        //建立用户相似度表
        int[] UserLike = new int[allUser.size()];
        for(int i=0;i<allUser.size();i++){
            UserLike[i] = 0;
        }

        //把相似度标上
        for(int i=1;i<allUser.size();i++){
            for(int j=0;j<productId.size();j++){
                if(product_User[j][i]==1&&product_User[j][0]==1){
                    UserLike[i]++;
                }
            }
        }

        int max=0;
        int site = 0;
        for(int i=0;i<allUser.size();i++){
            if(UserLike[i]>max){
                max = UserLike[i];
                site = i;
            }
        }

        //如果是新用户，就直接返回前排的三个
        if(max==0){
            wrapper = new QueryWrapper();
            wrapper.le("id",735);
            List<Product> recommend = productMapper.selectList(wrapper);
            return recommend;
        }


        List<Product> recommend = new ArrayList<>();
        int e=0;
        int num = 0;
        for(int i=0;i<userProducts.get(site).size();i++){
            e=0;
            for(int j=0;j<userProducts.get(0).size();j++){
                if(userProducts.get(site).get(i).equals(userProducts.get(0).get(j))){
                    e=1;
                    break;
                }
            }
            if(e==0){
                wrapper = new QueryWrapper();
                wrapper.eq("id",userProducts.get(site).get(i));
                recommend.add((Product) productMapper.selectList(wrapper).get(0));
                num++;
                if(num==3){
                    break;
                }
            }
        }


        //        List<>
        int[] nums={733,734,735};
        //for(int i=0;i<3;i++)

        return recommend;

        //建立商品-用户的倒排索引表以及当前用户-所有用户表，每当有相同的商品，就在用户表中+1
        //计算两个用户的相似度（相同的物品/根号下a*b的物品数），存到一个数组中,根据相似度对用户进行排序
        //取出前2/n个用户，获取到他们购买过的商品，构建商品-用户倒排索引，相似度相加。

    }
}
