import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.LeyouUploadApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;

@SpringBootTest(classes = LeyouUploadApplication.class)
public class FastDFSTest {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    private String filePath = "D:\\java\\IntelliJ IDEA\\source\\leyou\\leyou-upload\\src\\test\\java\\bg.jpg";

    @Test
    public void testUpload() throws FileNotFoundException {
        // 要上传的文件
        File file = new File(filePath);
        // 上传并保存图片，参数：1-上传的文件流 2-文件的大小 3-文件的后缀 4-可以不管他
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "jpg", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
        /*
            group1/M00/00/00/wKgrg1_XKJGAE1KGAAY3sWVp6Mk061.jpg
            M00/00/00/wKgrg1_XKJGAE1KGAAY3sWVp6Mk061.jpg
        */
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file = new File(filePath);
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "jpg", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
        // 获取缩略图路径
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
        
        /*
        group1/M00/00/00/wKgrg1_XKOuAJ5oEAAY3sWVp6Mk282.jpg
        M00/00/00/wKgrg1_XKOuAJ5oEAAY3sWVp6Mk282.jpg
        M00/00/00/wKgrg1_XKOuAJ5oEAAY3sWVp6Mk282_60x60.jpg
        * */
    }
}
