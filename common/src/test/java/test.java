import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author zk
 * @Date 2017/6/8.
 * @Email 2117251154@qq.com
 */
public class test {
    public static void main(String [] args){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/mm/dd HH:mm:ss");
        try {
            Date d = simpleDateFormat.parse("09/03/04 22:11:02");
            System.out.println(d.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
