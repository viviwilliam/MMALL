package com.viviwilliam.mmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 登录名
     */
      private String loginName;

      /**
     * 用户名
     */
      private String userName;

      /**
     * 密码
     */
      private String password;

      /**
     * 性别(1:男 0：女)
     */
      private Integer gender;

    private Integer type;

      /**
     * 身份证号
     */
      private String identityCode;

      /**
     * 邮箱
     */
      private String email;

      /**
     * 手机
     */
      private String mobile;

    private String fileName;

      @TableField(fill = FieldFill.INSERT)
      private LocalDateTime createTime;

     @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime updateTime;


}
