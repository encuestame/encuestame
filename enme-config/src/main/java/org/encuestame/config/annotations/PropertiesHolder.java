package org.encuestame.config.annotations;

import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by jpicado on 25/06/16.
 */
public abstract class PropertiesHolder {

    protected static final Resource PROD_PROPERTIES =  new ClassPathResource(
            "/org/encuestame/config/encuestame-config.properties");

    protected static final Resource TEST_PROPERTIES =  new ClassPathResource(
            "properties-test/encuestame-test-config.properties");

    protected static final Resource CUSTOM_PROPERTIES = new ClassPathResource(
            "encuestame-config-custom.properties");

    protected static final Resource VERSION_PROPERTIES =  new ClassPathResource(
            "/org/encuestame/core/config/version.properties");

    protected static PropertyPlaceholderConfigurer createPropertyHolder(Resource resource) {
        final PropertyPlaceholderConfigurer pspc = new EnMePlaceHolderConfigurer();
        pspc.setIgnoreResourceNotFound(true);
        //pspc.setLocalOverride(true);
        pspc.setLocations(new Resource[] {PROD_PROPERTIES, VERSION_PROPERTIES, resource});
        return pspc;
    }
}
