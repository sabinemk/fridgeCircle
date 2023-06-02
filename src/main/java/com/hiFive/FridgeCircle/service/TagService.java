package com.hiFive.FridgeCircle.service;

import com.hiFive.FridgeCircle.entity.Tag;
import com.hiFive.FridgeCircle.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository=tagRepository;
    }

    public void createTag(Tag tag){
        this.tagRepository.save(tag);
    }
}
