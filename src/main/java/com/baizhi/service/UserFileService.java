package com.baizhi.service;

import com.baizhi.entity.UserFile;
import java.util.List;

/**
 * @author: timor
 * @date: 2020/7/16 10:07
 */
public interface UserFileService {

    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);
}
