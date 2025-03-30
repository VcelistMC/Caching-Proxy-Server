package com.peteratef.caching_proxy.repo;

import com.peteratef.caching_proxy.entity.CachedResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyRepository extends JpaRepository<CachedResponseEntity, String> {
}
