package myblog.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class UploadFileUtil {

    //存储文件上传失败的错误信息
    private static Map<String, String> error_result = new HashMap<>();
    //存储头像的上传结果信息
    private static Map<String, String> upload_result = new HashMap<>();


//    效验所上传图片的大小及格式等信息...
    private static Map<String, String> checkPhoto(MultipartFile photo, String path,String orginalName) {
        //限制头像大小(20M)
        int MAX_SIZE = 20971520;
        //如果保存文件的路径不存在,则创建该目录
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        //限制上传文件的大小
        if (photo.getSize() > MAX_SIZE) {
            error_result.put("success", "0");
            error_result.put("msg", "上传的图片大小不能超过20M!");
            return error_result;
        }
        // 限制上传的文件类型
        boolean flag=true;
        String[] suffixs = new String[]{".png", ".PNG", ".jpg", ".JPG", ".jpeg", ".JPEG", ".gif", ".GIF", ".bmp", ".BMP"};
        for (String suffix : suffixs) {
            if (orginalName.endsWith(suffix)) {
                flag = false;
                break;
            }
        }
        if (flag){
            error_result.put("success","0");
            error_result.put("msg", "禁止上传此类型文件!");
            return error_result;
        }


        return null;
    }


    public static Map<String, String> uploadPhoto(MultipartFile photo, String path) {
        if (!photo.isEmpty() && photo.getSize() > 0) {
            //获取图片的原始名称
            String orginalName = photo.getOriginalFilename();
            //上传图片,error_result:存储头像上传失败的错误信息
            Map<String, String> error_result=checkPhoto(photo, path,orginalName);
            if (error_result != null) {
                return error_result;
            }
            //使用UUID重命名图片名称(uuid__原始图片名称)
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String newPhotoName = uuid + "_" + orginalName;
            //将上传的文件保存到目标目录下
            try {
                photo.transferTo(new File(path, newPhotoName));
                upload_result.put("success", "1");
                upload_result.put("photoName", "\\userImg\\"+newPhotoName);//将存储头像的路径返回给页面
            } catch (IOException e) {
                e.printStackTrace();
                upload_result.put("success", "0");
                upload_result.put("msg", "上传文件失败! 服务器端发生异常!");
                return upload_result;
            }
        } else {
            upload_result.put("success", "1");
            upload_result.put("msg", "头像上传失败! 未找到指定图片!");
        }
        return upload_result;
    }
}
