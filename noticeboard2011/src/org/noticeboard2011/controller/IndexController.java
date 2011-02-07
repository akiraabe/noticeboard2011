package org.noticeboard2011.controller;

import java.util.logging.Logger;

import org.slim3.controller.Navigation;

public class IndexController extends AbstractJsonController {

    static Logger logger = Logger.getLogger(IndexController.class.getName());

    @Override
    protected Navigation run() throws Exception {

        logger.fine("IndexController#run start.");

        return forward("index.jsp");
    }
}
