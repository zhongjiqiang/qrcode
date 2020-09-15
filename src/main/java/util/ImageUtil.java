package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtil {


    public static void main(String[] args) throws IOException {
        imageAround(25,25,25,25,"pics/2505000164362042早购礼27.jfif","picsSplit2","2505000164362042早购礼27.jfif");
    }

    /***
     * @param rows 横切割数量最小为1
     * @param cols 数切割数量最小为1
     * @param originalImg 原图片路径地址
     * @param destPath 切分之后的文件路径
     * @param destFile 切分之后的文件输出结果（默认后缀加了${i}.jpg）
     * @throws IOException
     */
    public static void splitImage(int rows,int cols,String originalImg,String destPath,String destFile) throws IOException {

        File file = new File(originalImg);
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);

        int chunks = rows * cols;

        // 计算每个小图的宽度和高度
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;

        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //设置小图的大小和类型
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                //写入图像内容
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0,
                        chunkWidth, chunkHeight,
                        chunkWidth * y, chunkHeight * x,
                        chunkWidth * y + chunkWidth,
                        chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }

        // 输出小图
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "jpg", new File(destPath+"/"+destFile + i + ".jpg"));
        }

    }


    /***
     * 上下方向同尺寸和左右方向同尺寸裁剪
     * @param rows 上下方向裁剪的百分比如果传10则上下各裁剪10%
     * @param cols 左右方向裁剪的百分比如果传10则左右各裁剪10%
     * @param originalImg 源图片路径（含文件名）
     * @param destPath 目标图片目录（不包含文件名）
     * @param destFile 目标文件名
     * @throws IOException
     */
    public static void imageCenter(int rows,int cols,String originalImg,String destPath,String destFile) throws IOException {

        File file = new File(originalImg);
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);


        // 计算每个小图的宽度和高度
        double chunkWidthf = 1.0*image.getWidth()*(100-2*cols)/100;
        double chunkHeightf = 1.0*image.getHeight()*(100-2*rows)/100;
        int chunkWidth = (int)chunkWidthf;
        int chunkHeight = (int)chunkHeightf;
        BufferedImage img = null;
        img = new BufferedImage(chunkWidth, chunkHeight, image.getType());
        //写入图像内容
        Graphics2D gr = img.createGraphics();
        gr.drawImage(image, 0, 0,
                chunkWidth, chunkHeight,
                image.getWidth()*cols/100, image.getHeight()*rows/100,
                image.getWidth()*(100-cols)/100,
                image.getHeight()*(100-rows)/100, null);
        gr.dispose();

        ImageIO.write(img, "jpg", new File(destPath+"/"+destFile));


    }

    /**
     * 灵活去四周
     * @param up 从上面切割百分比
     * @param down 从下面切割百分比
     * @param left 从左边切割百分比
     * @param right 从右边切割百分比
     * @param originalImg 源图片路径（含文件名）
     * @param destPath 目标图片目录（不包含文件名）
     * @param destFile 目标文件名
     * @throws IOException
     */
    public static void imageAround(int up,int down,int left,int right,String originalImg,String destPath,String destFile) throws IOException {

        File file = new File(originalImg);
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);


        // 计算每个小图的宽度和高度
        double chunkWidthf = 1.0*image.getWidth()*(100-left-right)/100;
        double chunkHeightf = 1.0*image.getHeight()*(100-up-down)/100;
        int chunkWidth = (int)chunkWidthf;
        int chunkHeight = (int)chunkHeightf;
        BufferedImage img = null;
        img = new BufferedImage(chunkWidth, chunkHeight, image.getType());
        //写入图像内容
        Graphics2D gr = img.createGraphics();
        gr.drawImage(image, 0, 0,
                chunkWidth, chunkHeight,
                image.getWidth()*left/100, image.getHeight()*up/100,
                image.getWidth()*(100-right)/100,
                image.getHeight()*(100-down)/100, null);
        gr.dispose();

        ImageIO.write(img, "jpg", new File(destPath+"/"+destFile));


    }







}
