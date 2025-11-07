package com.sky.Controller;

import com.sky.Service.UserService;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDto;
import com.sky.dto.UserPageDto;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userDto){
        log.info("用户登录：{}",userDto);
        User user = userService.login(userDto);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVo userLoginVo = UserLoginVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .loginAccount(user.getLoginAccount())
                .build();

        return Result.success(userLoginVo);
    }
    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping
    public Result addUser(@RequestBody User user){
        log.info("添加用户：{}",user);
        return userService.addUser(user)>0?Result.success():Result.error("添加用户失败");
    }
    /**
     * 为用户分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("/role")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') and hasAuthority('role:assign')")
    public Result assignRole(Integer userId, List<Long> roleIds) {
        log.info("为用户分配角色:{},{}",userId, roleIds);
        userService.assignRole(userId, roleIds);
        return Result.success();
    }
    /**
     * 分页查询用户
     * @param userPageDto
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> pageSelect(@RequestBody UserPageDto userPageDto){
        log.info("分页查询用户");
        PageResult pageResult = userService.pageSelect(userPageDto);
        return Result.success(pageResult);
    }
    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping
    public Result update(@RequestBody User user){
        log.info("修改用户:{}",user);
        return userService.update(user)>0?Result.success():Result.error("修改用户失败");
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    @DeleteMapping
    public Result delete(List<Integer> id){
        log.info("批量删除用户:{}",id);
        return userService.delete(id)>0?Result.success():Result.error("删除用户失败");
    }
    /**
     * 设置用户激活状态
     * @param status
     * @return
     */
    @PutMapping("/active/{status}")
    public Result setActive(@PathVariable Integer status ,Integer id){
        log.info("设置用户激活状态:{},{}",status, id);
        return userService.setActive(status,id)>0?Result.success():Result.error("设置失败");
    }
    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Integer id){
        log.info("查询用户信息:{}",id);
        return Result.success(userService.getById(id));
    }
}
