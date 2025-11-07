package com.sky.Service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.Mapper.UserMapper;
import com.sky.Service.RoleService;
import com.sky.Service.UserService;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.UserLoginDto;
import com.sky.dto.UserPageDto;
import com.sky.entity.User;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.result.PageResult;
import com.sky.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserServiceIml implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    /**
     * 用户登录
     * @param userDto
     * @return
     */
    @Override
    public User login( UserLoginDto userDto) {
        log.info("用户登录：{}",userDto);
        String loginAccount = userDto.getLoginAccount();
        String password = userDto.getPassword();

        //1、根据用户名查询数据库中的数据
       User user=userMapper.getByUsername(loginAccount);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if ( user== null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getIsActive() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @CacheEvict(value = "users", allEntries = true)
    @Override
    public int addUser( User user) {
        log.info("添加用户：{}",user);
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        user1.setIsActive(StatusConstant.ENABLE);
        user1.setPassword(DigestUtils.md5DigestAsHex(user1.getPassword().getBytes()));
        return userMapper.insertUser(user1);
    }

    /**
     * 分页查询会议室
     * @return
     */
    @Override
    public PageResult pageSelect( UserPageDto userPageDto) {
        log.info("分页查询用户");
        PageHelper.startPage(userPageDto.getPage(),userPageDto.getPageSize());
        Page<UserVo> page = userMapper.limitSelect(userPageDto);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @CacheEvict(value = "users", allEntries = true)
    @Override
    public int update( User user) {
        log.info("修改用户信息");
        //向删除后增加
        List<Integer> ids = new ArrayList<>() ;
        ids.add(user.getId());
        int i = userMapper.deleteByIds(ids);
        if (i>0){
            return userMapper.insertUser(user);
        }
        return 0;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @CacheEvict(value = "users", allEntries = true)
    @Override
    public int delete(List<Integer> id) {
        log.info("删除用户");
        return userMapper.deleteByIds(id);
    }

    /**
     * 设置用户状态
     * @param status
     * @return
     */
    @CacheEvict(value = "users", allEntries = true)
    @Override
    public int setActive(Integer status ,Integer id) {
       log.info("设置用户状态");
       User user = new User();
       user.setId(id);
       user.setIsActive(status);
       return userMapper.update(user);
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Cacheable(value = "users", key = "#id")
    @Override
    public User getById(Integer id) {
        log.info("根据id查询用户信息");
        return userMapper.getById(id);
    }

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public void assignRole(Integer userId, List<Long> roleIds) {
        log.info("分配角色");
        roleService.assignRoles(userId, roleIds);
    }
}
