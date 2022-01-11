package eus.solaris.solaris.controller.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import eus.solaris.solaris.service.UserService;

public class DataRESTController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    
}
