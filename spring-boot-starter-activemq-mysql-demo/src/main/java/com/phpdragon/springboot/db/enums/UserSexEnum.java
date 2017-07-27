package com.phpdragon.springboot.db.enums;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

@MappedJdbcTypes(JdbcType.INTEGER)
public enum UserSexEnum{
    WOMAN,MAN
}