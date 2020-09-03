package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StaticResourceUtil {
    /**
     * 获取静态资源的绝对路径
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\","/") + path;
    }

    /**
     *
     * @param inputStream
     * @param outputStream
     */
    public static void outputResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        while(count == 0) {
            count = inputStream.available();
        }

        int resourceSize = count;
        // 输出http请求头,然后再输出具体内容
        outputStream.write(ProtocolHttpUtil.http200header(resourceSize).getBytes());

        //写出总数统计
        int writeLen = 0;
        int byteSize = 1024; //缓存区大小
        byte []bytes = new byte[1024]; //写出缓冲区

        while (writeLen < resourceSize ) {
            if ((writeLen + byteSize) > resourceSize){ //表示读取到最后一个字节
                byteSize = resourceSize - writeLen;
                bytes = new byte[byteSize];
            }

            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            writeLen+=byteSize;
        }

    }

}
