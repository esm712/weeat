package com.websocket.chat.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.websocket.chat.dto.user_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public interface UserMapper {
    user_info getProfile(@Param("user_id") String user_id);
}
