package com.keanu.cloud.auth.mapper;

import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.keanu.cloud.auth.domain.User;

public interface UserMapper extends BaseMapper<User> {

	@Select("select id, username, password from users where username = #{username}")
	User selectByUsername(String username);
}
