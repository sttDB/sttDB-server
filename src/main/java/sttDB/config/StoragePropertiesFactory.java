package sttDB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sttDB.service.storageService.StorageProperties;

@Configuration
public class StoragePropertiesFactory {

    @Bean
    public StorageProperties storageProperties() {
        String location = System.getenv("FILES_DIR");
        if (location == null)
            throw new IllegalStateException("Environment variable <FILES_DIR> not set");
        else
            return new StorageProperties("./files");
    }

}
