package com.lombre.urlShortner.url.repository;

import com.lombre.urlShortner.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    boolean existsByOrigUrl(String origUrl);
    boolean existsByConvertUrl(String convertUrl);

    Url findByOrigUrl(String origUrl);
    Url findByConvertUrl(String convertUrl);


}
