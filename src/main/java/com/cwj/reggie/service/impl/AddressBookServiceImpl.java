package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.AddressBook;
import com.cwj.reggie.service.AddressBookService;
import com.cwj.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-09-19 09:14:55
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




