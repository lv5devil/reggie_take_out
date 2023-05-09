package com.American.reggie.service.impl;

import com.American.reggie.entity.AddressBook;
import com.American.reggie.mapper.AddressBookMapper;
import com.American.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>implements AddressBookService {
}
