package com.wodder.product;

import java.io.File;
import javax.ws.rs.ApplicationPath;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    LOGGER.info("Starting up application.");
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
    tomcat.addServlet("", "jax-rs", new ServletContainer(new Product()));
    ctx.addServletMappingDecoded("/*", "jax-rs");
    ctx.addApplicationListener(Listener.class.getName());

    tomcat.start();
    LOGGER.info("Running...listening for requests on port 8080");
    tomcat.getServer().await();
  }

  @ApplicationPath("/")
  public static class Product extends ResourceConfig {
    public Product() {
      packages("com.wodder.product");
    }
  }
}
