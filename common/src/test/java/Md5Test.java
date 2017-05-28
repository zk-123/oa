import cn.zkdcloud.util.Md5Util;

/**
 * @Author zk
 * @Date 2017/5/27.
 * @Email 2117251154@qq.com
 */
public class Md5Test {
    public static void main(String[] a){
        String str = "haha";
        System.out.println(Md5Util.toMD5(str));
        System.out.println(Md5Util.toMD5(Md5Util.toMD5(str)));
    }
}
