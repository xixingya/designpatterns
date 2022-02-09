package tech.xixing.proxy.dymatic;

import sun.misc.ProxyGenerator;
import tech.xixing.proxy.statics.MemberDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author liuzhifei
 * @date 2022/2/8 7:12 下午
 */
public class ProxyGeneratorTest {

    public static void main(String[] args) throws IOException {
        URL resource = Test.class.getResource("/");

        byte[] bts = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{MemberDao.class});
        File file = new File(resource.getPath(), "$Proxy0.class");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream =new FileOutputStream(file);
        fileOutputStream.write(bts);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
