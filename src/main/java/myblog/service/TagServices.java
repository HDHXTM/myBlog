package myblog.service;

import myblog.bean.Tag;

import java.util.List;

public interface TagServices {

    List<Tag> findAll();

    List<Tag> findBlogTags(int id);

    void updateBlogTags(Integer id, Integer[] tagsId);
}