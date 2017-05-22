package cn.zkdcloud.util;

/**
 * @Author zk
 * @Date 2017/5/21.
 * @Email 2117251154@qq.com
 */
public class StrUtil{

    /** 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        if( str == null || str.trim().equals("") || str.isEmpty())
            return true;
        return false;
    }

    /** 判断字符串中是否包含除 数字，字母，汉字 之外的特殊字符
     *
     * @param str
     * @return
     */
    public static boolean notDigitAndLetterAndChinese(String str){
        for(Character c : str.toCharArray()){
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '1' && c <= '9') ||(c >= 19968 && c <= 171941))
                continue;
            else
                return true;
        }
        return false;
    }

    /** 判断上是否为整数
     *
     * @param digit
     * @return
     */
    public static boolean isIntegerDigit(String digit){
        try{
            Integer t = Integer.parseInt(digit);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
