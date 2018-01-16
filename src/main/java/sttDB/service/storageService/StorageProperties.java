package sttDB.service.storageService;

import org.springframework.stereotype.Service;

@Service
public class StorageProperties {

    private final String location;

    public StorageProperties(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

}