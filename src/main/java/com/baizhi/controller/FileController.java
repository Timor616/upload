package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.entity.UserFile;
import com.baizhi.service.UserFileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: timor
 * @date: 2020/7/15 22:41
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UserFileService userFileService;


    /**
     * 返回当前文件列表JSON
     */
    @GetMapping("/findAllJSON")
    @ResponseBody
    public List<UserFile> findAllJSON(HttpSession session) {

        //登录的session中获取用户的id
        User user = (User) session.getAttribute("user");
        // 根据id 查询文件信息
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        // 存入作用域中

        return userFiles;
    }



    /**
     * 删除文件信息
     */
    @GetMapping("/delete")
    public String delete(String id) throws FileNotFoundException {
        UserFile userFile = userFileService.findById(id);
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static"+userFile.getPath();
        File file = new File(realPath, userFile.getNewFileName());
        if (file.exists()) {
            file.delete();
        }
        userFileService.delete(id);
        return "redirect:/file/showAll";
    }


    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(String openStyle, String id, HttpServletResponse response) throws IOException {
        openStyle = openStyle == null ? "attachment":openStyle;
        UserFile userFile = userFileService.findById(id);
        if("attachment".equals(openStyle)) {
            userFile.setDowncounts(userFile.getDowncounts()+1);
            userFileService.update(userFile);
        }

        String realpath = ResourceUtils.getURL("classpath:").getPath()+"/static" +userFile.getPath();
        FileInputStream is = new FileInputStream(new File(realpath, userFile.getNewFileName()));
        response.setHeader("content-disposition", openStyle+";fileName="
                + URLEncoder.encode(userFile.getOldFileName(), "UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);

    }

    /**
     * 上传文件
     */

    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        // 获取文件的原始名称
        String oldFileName = file.getOriginalFilename();
        // 获取文件的后缀
        String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-", "")+extension;
        long size = file.getSize();
        String type = file.getContentType();
        String realPath = ResourceUtils.getURL("classpath:").getPath()+ "/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + dateFormat;
        File dateDir = new File(dateDirPath);
        if (!dateDir.exists()) {
            dateDir.mkdirs();
        }
        file.transferTo(new File(dateDir, newFileName));
        //将文件信息保存在数据库中
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName).setNewFileName(newFileName).setExt(extension).
                setSize(String.valueOf(size)).setType(type).setPath("/files/"+dateFormat).setUserId(user.getId());
        userFileService.save(userFile);


        return "redirect:/file/showAll";

    }


    @GetMapping("/showAll")
    public String findAll(HttpSession session, Model model) {

        //登录的session中获取用户的id
        User user = (User) session.getAttribute("user");
        // 根据id 查询文件信息
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        // 存入作用域中
        model.addAttribute("files", userFiles);
        return "showAll";
    }
}
