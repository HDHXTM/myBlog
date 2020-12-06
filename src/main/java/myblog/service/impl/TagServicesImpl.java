package myblog.service.impl;

import myblog.bean.Tag;
import myblog.dao.TagDao;
import myblog.service.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServicesImpl implements TagServices {
    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> findBlogTags(int id) {
        return tagDao.findBlogTags(id);
    }

    @Override
    public void updateBlogTags(Integer id, Integer[] tagsId) {
//        暂时没好办法，只能先删再加
        tagDao.deleteTagsByBlogId(id);
        for (Integer tagId : tagsId) {
            tagDao.addBlogTag(id,tagId);
        }
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }
}
