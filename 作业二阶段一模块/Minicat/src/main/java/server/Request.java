package server;

import container.Context;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private String url;  //请求路径
    private String method; //请求方法GET,POST
    private String host; //请求的主机名称
    private String contextName; //请求上下文名称
    private String port;

    private Context context;

    private final InputStream inputStream;

    /**
     * 构造请求对象，解析http的请求输入流中的内容。
     * 获取请求的路径和方法并赋值到url和method参数中。
     * 请求格式：// GET / HTTP/1.1
     * @param inputStream
     */
    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        int count = 0;
        while (count == 0){
            count = this.inputStream.available();
        }

        byte []bytes = new byte[count];
        inputStream.read(bytes);

        String instr = new String(bytes);
        String[] split = instr.split("\n");
        String firstLine = split[0];
        String[] s = firstLine.split(" ");
        String secLine = split[1];
        String[] s1 = secLine.split(" ");
        this.method = s[0];
        this.url = s[1];
        String[] split1 = s1[1].split(":");
        this.host = split1[0];
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
