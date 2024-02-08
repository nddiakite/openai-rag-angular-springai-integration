//package fr.dtek.lab.security;
//
//import SocialConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    private static final Logger LOG = LoggerFactory.getLogger(SocialConfig.class);
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        LOG.info("NDI => WebMvcConfigurer has been correctly launched !!");
//
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:4200")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}
