package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-09-19 09:14:55
* @Entity com.cwj.reggie.entity.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




