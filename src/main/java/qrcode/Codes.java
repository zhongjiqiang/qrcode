package qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import util.FileUtil;
import util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc 二维码解析类
 *
 * @author 钟继强
 *
 */


public class Codes {

    //用于存储文件名和二维码内容的映射关系
    public static Map<String,String> map = new HashMap<String, String>();
    //用来存储二维码解析失败之后切割图片的重试次数
    public static int tryTimes=0;

    /**
     * 解析读取二维码，路径下所有的图片都会解析
     *
     * @param qrCodePath 二维码图片路径
     * @return
     */
    public static String decodeQRcode(String qrCodePath) {
        BufferedImage image;
        String qrCodeText = null;
        try {
            image = ImageIO.read(new File(qrCodePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            // 对图像进行解码
            com.google.zxing.Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            qrCodeText = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return qrCodeText;
    }

    /***
     * 遍历路径tmp下所有文件，如果无法识别pics目录下图片二维码则调用ImageUtil的imageCenter函数进行图片裁剪，裁剪结果放到picsSplit目录下
     */
    public static void decodeAllTmpPic(String sourcePath,String sourceFile) throws IOException {
        String path="tmp";
        File f = new File(path);
        String[] listFiles = f.list();
        for (String filePath : listFiles) {
            if(!filePath.equals(".DS_Store")){
                String decodeQRcode = Codes.decodeQRcode(path + "/" + filePath);
                if (decodeQRcode!=null){
                    map.put(sourceFile,decodeQRcode);
                    if(!Files.exists(Paths.get("pics_done")))
                        Files.createDirectory(Paths.get("pics_done"));
                    FileUtil.moveFile(sourcePath+"/"+sourceFile,"pics_done"+"/"+sourceFile);
                }
                Files.delete(Paths.get(path + "/" + filePath));
            }
        }
    }



    /***
     * 遍历路径下所有文件，如果无法识别pics目录下图片二维码则调用ImageUtil的imageCenter函数进行图片裁剪，裁剪结果放到picsSplit目录下
     * @param path 二维码图片路径
     */
    public static void decodeAllPic(String path) throws IOException {

        File f = new File(path);
        String[] listFiles = f.list();
        for (String filePath : listFiles) {
            if(!filePath.equals(".DS_Store")){
                String decodeQRcode = Codes.decodeQRcode(path + "/" + filePath);
                if (decodeQRcode!=null){
                    map.put(filePath,decodeQRcode);
                    if(!Files.exists(Paths.get("pics_done")))
                        Files.createDirectory(Paths.get("pics_done"));
                    FileUtil.moveFile(path + "/" + filePath,"pics_done"+"/"+filePath);
                } else{
                    if(!Files.exists(Paths.get("tmp")))
                        Files.createDirectory(Paths.get("tmp"));
                    //如果图片首次解析失败则将图片横竖二分之一分成四个图片放到tmp目录下
                    ImageUtil.splitImage(2,2,path + "/" + filePath,"tmp",filePath);
                    //如果图片首次解析失败则按照四周向中间缩圈20%的比例保留中间部分将图片画到tmp目录下
                    ImageUtil.imageCenter(20,20,path + "/" + filePath,"tmp",filePath);
                    //解析tmp目录下所有图片，若有结果则塞到静态变量map中，并将源文件移到pics_done目录
                    decodeAllTmpPic(path , filePath);
                }

            }
        }
    }





    public static void main(String[] args) throws IOException {
        //默认将图片放到项目的pics路径下
        Codes.decodeAllPic("pics");
        System.out.println("总共生产二维码"+map.size()+"个");
        map.forEach((a,b)->System.out.println(a+":\t"+b));
    }
}