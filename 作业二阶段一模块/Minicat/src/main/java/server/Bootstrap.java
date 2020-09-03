package server;

import adapter.Adapter;
import adapter.CoyotoAdapter;
import classloader.WebAppClassloader;
import connector.Connector;
import connector.Endpoint;
import connector.ProtocolHandler;
import connector.impl.BioEndpoint;
import connector.impl.Http11ProtocolHandler;
import container.*;
import container.impl.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Minicat 开发，装载启动类。
 * 开发流程：
 * 1、实现浏览器与minicat连接，给浏览器返回“hello，minicat”。
 * 2、实现Request，Response的封装,实现静态资源的响应。
 * 3、以及动态资源的访问。
 * 4、实现多线程改造。以及线程池改造
 */
public class Bootstrap {
    private int port = 8080;

    private Map<String, HttpServlet> servletMap = new HashMap<String, HttpServlet>();

    /**
     * V1.0：启动minicat，客户端一旦连接，返回“hello，minicat”。
     */
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("==============>>Minicat启动。");
        while(true){
            Socket accept = serverSocket.accept();
            String content = "<h1>Hello, Minicat.</h1>";
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write(ProtocolHttpUtil.http200header(content.getBytes().length).getBytes());
            outputStream.write(content.getBytes());
            accept.close();
        }
    }

    /**
     * V2.0:实现Request ， Responnse的对象的封装
     */
//    public void startV2() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(port);
//        System.out.println("==============>>Minicat启动2.0。");
//        while (true) {
//            Socket accept = serverSocket.accept();
//            Request request = new Request(accept.getInputStream());
//            Response response = new Response(accept.getOutputStream());
//            response.outputHtml(request.getUrl());
//
//            accept.close();
//        }
//    }

    /**
     * V3.0:实现以及动态资源的访问。
     * 需要实现servlet配置与解析，并加载到容器中。
     */
//    public void startV3() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(port);
//        loadServlet();
//        //定义线程池
//        int corePoolSize = 10; //线程池大小
//        int maximumPoolSize = 50; //线程池最大大小
//        long keepAliveTime = 100L; //保持活动时间
//        TimeUnit unit = TimeUnit.SECONDS; //保持活动单位
//        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(50);
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
//        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
//                maximumPoolSize,
//                keepAliveTime,
//                unit,
//                workQueue,
//                threadFactory,
//                handler);
//        System.out.println("==============>>Minicat线程池加载完毕。");
//
//        System.out.println("==============>>Minicat启动3.0。");
//
//        while (true) {
//            Socket accept = serverSocket.accept();
//
//            executor.execute(new ProcessHandler(accept,servletMap));
//        }
//    }


    private Server server;

    /**
     * V4.0，实现webapps部署多个项目，并且加载
     */
    public void startV4(){
        init();
        start4();
    }

    /**
     * V4.0,初始化
     */
    private void init(){
        //加载配置文件server.xml以及初始化Context容器和Wapper容器
        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        server.init();
    }

    private void load() throws FileNotFoundException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document server = saxReader.read(resourceAsStream);
            Element rootElement = server.getRootElement();
            this.server = createServer();

            //初始化service
            List<Element> servicesList = rootElement.selectNodes("//Service");
            Service[]services = new Service[servicesList.size()];
            for (int i = 0; i < servicesList.size(); i++) {
                services[i] = createService(this.server,servicesList.get(i));
            }
            this.server.setServices(services);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Service节点
     * @param server
     * @param element
     * @return
     */
    private Service createService(Server server ,Element element) throws FileNotFoundException {
        List<Element> listConnector = element.selectNodes("//Connector");
        List<Element> listEngine = element.selectNodes("//Engine");
        Service service = new StandService();
        for (int i = 0; i < listEngine.size(); i++) {
            Engine engine = createEngine(service,listEngine.get(i));
            service.addEngine(engine);
        }
        service.setServer(server);

        for (int i = 0; i < listConnector.size(); i++) {
            Element elementConnector = listConnector.get(i);
            String port = elementConnector.attributeValue("port");
            Adapter adapter = new CoyotoAdapter();
            Endpoint endpoint = new BioEndpoint(adapter);
            ProtocolHandler protocolHandler = new Http11ProtocolHandler(endpoint);
            Connector connector = new Connector(protocolHandler);
            adapter.setConnector(connector);
            protocolHandler.setConnector(connector);
            endpoint.setProtocolHandler(protocolHandler);
            connector.setPort(Integer.valueOf(port));
            connector.setServer(server);
            connector.setService(service);

            server.addConnector(connector);
        }
        return service;
    }

    /**
     * 创建引擎
     * @param element
     * @return
     */
    private Engine createEngine(Service service,Element element) throws FileNotFoundException {
        List<Element> listHost = element.selectNodes("//Host");
        Engine engine = new StandEngine();
        Host[] hosts = new Host[listHost.size()];
        for (int i = 0; i < listHost.size(); i++) {
            hosts[i] = createHost(engine,listHost.get(i));
        }
        engine.setHosts(hosts);
        return engine;
    }

    /**
     * 创建Host容器
     * @param engine
     * @param element
     * @return
     */
    private Host createHost(Engine engine, Element element) throws FileNotFoundException {
        Host host = new StandHost();
        String name = element.attributeValue("name");
        String appBase = element.attributeValue("appBase");
        host.setName(name);
        host.setAppBase(appBase);
        host.setEngine(engine);

        File baseFile = new File(host.getAppBase());
        File[] files = baseFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            File web = files[i];
            if ( web.isDirectory() ){
                // 初始化Context
                Context context = createContext(web);
                host.addContext(context);
            }
        }
        return host;
    }

    /**
     * 创建上下文容器
     * @param web
     * @return
     */
    private Context createContext(File web) throws FileNotFoundException {
        Context context = new StandContext();
        String name = web.getName();
        context.setContextName(name);
        File classPath = new File(web,"/WEB-INF/classes");
        URL[] urls = new URL[1];
        try {
            urls[0] = classPath.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        context.setClassloader(new WebAppClassloader(urls));
        // 加载web.xml,并解析出Wapper
        File webXml = new File(web,"/WEB-INF/web.xml");
        if (webXml.exists()){
            createWapper(context,webXml);
        }
        return context;
    }

    private void createWapper(Context context, File webXml) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(webXml);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");

            for (Element element : selectNodes) {
                Element servletNameEle = (Element) element.selectSingleNode("//servlet-name");
                String servletName = servletNameEle.getStringValue();
                Element servletClsEle = (Element) element.selectSingleNode("//servlet-class");
                String servletClass = servletClsEle.getStringValue();

                //根据servlet-name的值找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                Wapper wapper = new StandWapper();
                wapper.setServletName(servletName);
                wapper.setClassName(servletClass);
                wapper.setUrlPattern(urlPattern);
                wapper.setContext(context);

                context.addWapper(wapper);
            }
        }catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建server
     * @return
     */
    private Server createServer() {
        return new StandServer();
    }

    /**
     * V4.0,初始化
     */
    private void start4(){
        this.server.start();
    }

    /**
     * 加载服务
     */
    private void loadServlet() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");

            for (Element element : selectNodes) {
                Element servletNameEle = (Element) element.selectSingleNode("//servlet-name");
                String servletName = servletNameEle.getStringValue();
                Element servletClsEle = (Element) element.selectSingleNode("//servlet-class");
                String servletClass = servletClsEle.getStringValue();

                //根据servlet-name的值找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
//        new Bootstrap().start();
//        new Bootstrap().startV2();
//        new Bootstrap().startV3();
        new Bootstrap().startV4();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
