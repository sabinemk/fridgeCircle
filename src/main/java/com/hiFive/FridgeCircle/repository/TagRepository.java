package com.hiFive.FridgeCircle.repository;

import com.hiFive.FridgeCircle.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag,Long> {
}
