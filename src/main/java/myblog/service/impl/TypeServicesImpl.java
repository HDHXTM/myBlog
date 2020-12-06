package myblog.service.impl;

import myblog.bean.Type;
import myblog.dao.TypeDao;
import myblog.service.TypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServicesImpl implements TypeServices {
    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> findAll() {
        return typeDao.findAll();
    }

}
