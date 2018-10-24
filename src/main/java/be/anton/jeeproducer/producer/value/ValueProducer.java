package be.anton.jeeproducer.producer.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author Anton Hajdinaj
 */
public class ValueProducer {

    private static final Logger logger = LoggerFactory.getLogger(ValueProducer.class);

    private Properties properties;

    @PostConstruct
    public void loadProperties() {
        this.properties = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(is);
        } catch (IOException e) {
            logger.error("Error while loading app.properties file !");
        }
    }

    @Value
    @Produces
    public String produceString(final InjectionPoint injectionPoint) {
        return getValue(injectionPoint);
    }

    @Value
    @Produces
    public int produceInt(final InjectionPoint injectionPoint) {
        return Integer.valueOf(getValue(injectionPoint));
    }

    @Value
    @Produces
    public boolean produceBoolean(final InjectionPoint injectionPoint) {
        return Boolean.valueOf(getValue(injectionPoint));
    }

    private String getValue(final InjectionPoint injectionPoint) {

        if (injectionPoint.getAnnotated().isAnnotationPresent(Value.class)) {
            Value annotation = injectionPoint.getAnnotated().getAnnotation(Value.class);
            return isNotEmpty(annotation.value()) ? properties.getProperty(annotation.value()) : annotation.defaultValue();
        }
        return injectionPoint.getMember().getName();
    }


}
