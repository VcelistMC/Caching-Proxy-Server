package com.peteratef.caching_proxy.entity;

import com.peteratef.caching_proxy.util.Hasher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CachedResponseEntity {
    @Id
    private String hashCode;

    @Column(columnDefinition = "TEXT")
    String response;

    Long timestamp;

    public static CachedResponseEntity createCachedResponseEntity(String uri, String response) {
        var cachedResponseEntity = new CachedResponseEntity();
        String hash = Hasher.generateMD5HashForURI(uri);
        cachedResponseEntity.setHashCode(hash);
        cachedResponseEntity.setResponse(response);
        cachedResponseEntity.setTimestamp(System.currentTimeMillis());

        return cachedResponseEntity;
    }
}
