package com.sky.repository;

import com.sky.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

// 继承JpaRepository，自动实现实验2 User表的基础CRUD（无需手动写SQL）
public interface UserRepository extends JpaRepository<User, Integer> {
    // 实验2登录流程支撑：按登录账号查询用户（匹配user表login_account唯一约束）
    Optional<User> findByLoginAccount(String loginAccount);

    // 实验2管理员列表功能支撑：按角色筛选用户（匹配user表role字段）
    List<User> findByRole(User.RoleEnum role);

    // 实验2用户状态筛选：查询启用的用户（匹配user表is_active字段）
    List<User> findByIsActiveTrue();
}