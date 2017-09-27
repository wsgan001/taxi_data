package com.nwu.data.taxi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.TimeZone;

@Controller
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/")
    public String index() {
        logger.info(TimeZone.getDefault().toString());
        return "index";
    }
}
