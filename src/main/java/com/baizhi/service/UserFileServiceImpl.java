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
       // userFile.setIsImg();
        userFile.setDowncounts(0);
        userFile.setUploadTime(new Date());
        userFileDAO.save(userFile);
    }
}
