package com.baizhi.service;

import com.baizhi.dao.UserFileDAO;
import com.baizhi.entity.UserFile;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: timor
 * @date: 2020/7/16 10:07
 */
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileDAO userFileDAO;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDAO.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
        // 当类型中含有image时
        String isImg = userFile.getType().startsWith("image")?"yes":"no";
        userFile.setIsImg(isImg);
        userFile.setDowncounts(0);
        userFile.setUploadTime(new Date());
        userFileDAO.save(userFile);
    }

    @Override
    public void delete(String id) {
        userFileDAO.delete(id);
    }

    @Override
    public void update(UserFile userFile) {
        userFileDAO.update(userFile);
    }

    @Override
    public UserFile findById(String id) {
        UserFile userFile = userFileDAO.findById(id);
        return userFile;
    }
}
