package org.example.singleton;

// Thread-Safe Singleton
public class ResourceManager {
    private static volatile ResourceManager instance;
    private ResourceManager() {
    }

    public static ResourceManager getInstance() {
        if (instance == null) { // to not lock every call to getInstance(), check if instance is null first
            synchronized (ResourceManager.class) { // if instance is null, lock in order to avoid other threads initializing in parallel
                if (instance == null) {
                    instance = new ResourceManager();
                }
            }
        }
        return instance;
    }
}