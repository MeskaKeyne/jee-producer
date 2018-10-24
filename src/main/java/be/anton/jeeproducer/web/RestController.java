package be.anton.jeeproducer.web;

import be.anton.jeeproducer.producer.value.Value;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Anton Hajdinaj
 */
@Singleton
@Named
@Path("/")
public class RestController {

    @Inject
    @Value("app.title")
    private String title;


    @GET
    public String home(){
        return "Hello world !";
    }

    @GET
    @Path("title")
    public String getAppTitle() {
        return title;
    }

}
