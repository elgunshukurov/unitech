package web.app.unitech.payment.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl {

    @CacheEvict(cacheNames = "getCurrenciesCache")
    public String evictCache() {
        return "getCurrenciesCache cache evicted";
    }
}
