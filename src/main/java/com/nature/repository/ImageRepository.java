package com.nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nature.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
