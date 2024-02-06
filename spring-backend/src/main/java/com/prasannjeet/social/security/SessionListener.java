//package com.prasannjeet.social.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//@WebListener
//public class SessionListener implements HttpSessionListener {
//    private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        LOG.info("OPEN AI => CLEANING Session Thread Infos !!");
//
//        se.getSession().setAttribute("openaiAPIThread", null);
//    }
//}
