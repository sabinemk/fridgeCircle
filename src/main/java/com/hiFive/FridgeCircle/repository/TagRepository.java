package com.hiFive.FridgeCircle.repository;

import com.hiFive.FridgeCircle.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag,Long> {

    List<Tag> findByNameContainsIgnoreCase(String namePart);
}
