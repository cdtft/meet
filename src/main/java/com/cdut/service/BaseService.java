package com.cdut.service;

import com.cdut.util.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * service的基础类
 * Created by king on 2017/10/13.
 */
public abstract class BaseService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IdService idService;
}
