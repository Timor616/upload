package com.baizhi.dao;

import com.baizhi.entity.UserFile;
import java.util.List;

/**
 * @author: timor
 * @date: 2020/7/16 10:00
 */
public interface UserFileDAO {

    List<UserFile> findByUserId(Integer id);

    //保存用户的文件记录
    void save(UserFile userFile);

    UserFile findById(String id);

    void update(UserFile userFile);

    void delete(String id);
}
