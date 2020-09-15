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
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc 二维码解析类
 *
 * @author 钟继强
 *
 */


public class Codes {

    public static Map<String,String> map = new HashMap<String, String>();

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
     * 遍历路径下所有文件，如果无法识别pics目录下图片二维码则调用ImageUtil的imageCenter函数进行图片裁剪，裁剪结果放到picsSplit目录下
     * @param path 二维码图片路径
     */
    public static void qu(String path) throws IOException {

        File f = new File(path);
        String[] listFiles = f.list();
        for (String filePath : listFiles) {
            if(!filePath.equals(".DS_Store")){
                String decodeQRcode = Codes.decodeQRcode(path + "/" + filePath);
                if (decodeQRcode!=null){
                    map.put(filePath,decodeQRcode);
                    FileUtil.moveFile(path + "/" + filePath,"pics_done"+"/"+filePath);
                }
                else{
                    if(path.equals("pics")){
                        System.out.println(filePath+"\t解码失败文件进行切分下次在处理");
                        ImageUtil.imageCenter(10,15,path + "/" + filePath,"picsSplit",filePath);
                    }
                }

            }
        }
    }





    public static void main(String[] args) throws IOException {

        Codes.qu("pics");
        System.out.println("总共生产二维码"+map.size()+"个");
        map.forEach(
                (a,b)->{
                    System.out.println(a+":\t"+b);
                }
        );
    }
}