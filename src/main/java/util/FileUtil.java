package util;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/***
 * @author 钟继强
 * @date 2020-09-14
 * @version 1.0
 * @description 文件处理类
 *
 */
public class FileUtil {


    /**
     * 移动文件
     * @param from
     * @param to
     * @return
     * @throws IOException
     */
    public static boolean moveFile(String from,String to) throws IOException {
        if (!copyFile(from,to))
            return false;
        Files.delete(Paths.get(from));
        return true;
    }

    /**
     * 复制文件
     * @param from
     * @param to
     * @return
     * @throws IOException
     */
    public static boolean copyFile(String from,String to) throws IOException {
        if(!Files.exists(Paths.get(from)))
            return false;
        byte[] bytes = Files.readAllBytes(Paths.get(from));
        Files.write(Paths.get(to),bytes, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        return true;
    }

    /**
     * 若存在目标文件则清空写，弱不存则创建新文件并写入
     * @param content 字符串类型
     * @param path
     * @throws IOException
     */
    public static void  writeStringTruncate(String content,Path path) throws IOException {
        Files.write(path,content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * 若存在目标文件则追加弱不存则创建新文件并写入
     * @param content 字符串类型
     * @param path
     * @throws IOException
     */
    public static void  writeStringAppend(String content,Path path) throws IOException {
        Files.write(path,content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }

    /**
     * 若存在目标文件则清空写，弱不存则创建新文件并写入
     * @param bytes 字节数组
     * @param path
     * @throws IOException
     */
    public static void  writeBytesTruncate(byte[] bytes,Path path) throws IOException {
        Files.write(path,bytes, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * 若存在目标文件则追加，弱不存则创建新文件并写入
     * @param bytes
     * @param path
     * @throws IOException
     */
    public static void  writeBytesAppend(byte[] bytes,Path path) throws IOException {
        Files.write(path,bytes, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }


}