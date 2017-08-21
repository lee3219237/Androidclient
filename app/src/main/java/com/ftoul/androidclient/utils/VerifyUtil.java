package com.ftoul.androidclient.utils;


/**
 * Created by ftoul106 on 2016/12/6 0006.
 */

public class VerifyUtil {
    /**
     * 匹配手机号码
     * @param phoneNum
     * @return
     */
    public static boolean phone(String phoneNum) {
        String regex = "1[34578]\\d{9}";
        return phoneNum.matches(regex);
    }

    /**
     * 匹配密码
     * @param pwd
     * @return
     */
    public static boolean pwd(String pwd){
        String regex ="[0-9a-zA-Z_]{6,20}";
        return pwd.matches(regex);
    }
    /**
     * 匹配弱密码
     * @param pwd
     * @return
     */
    public static boolean pwdLow(String pwd){
        String regex1 ="[0-9]{6,10}";
        String regex2 ="[a-zA-Z_]{6,10}";
        return pwd.matches(regex1)||pwd.matches(regex2);
    }
    /**
     * 匹配中等密码
     * @param pwd
     * @return
     */
    public static boolean pwdMiddling(String pwd){
        String regex1 ="[0-9]{10,20}";
        String regex2 ="[a-zA-Z_]{10,20}";
        String regex3 ="[0-9a-zA-Z_]{6,9}";
        return pwd.matches(regex1)||pwd.matches(regex2)||pwd.matches(regex3);
    }

    /**
     * 匹配验证码
     * @param captcha
     * @return
     */
    public static boolean captcha(String captcha){
        String regex ="[0-9]{4,6}";
        return captcha.matches(regex);
    }

    /**
     * 匹配QQ号码
     */
    public static boolean qq(String qq){
        String regex ="[0-9]{5,12}";
        return qq.matches(regex);
    }

    /**
     * 去除字符串所有空格
     * @param strs
     * @return
     */
    public static String trim(String strs){
        return strs.trim().replace(" ","");
    }

    /**
     * 匹配座机号码
     * @param phoneNum
     * @return
     */
    public static boolean tel(String phoneNum) {
     //   String regex = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{8}";
        String regex = "[0-9]{3,4}-[0-9]{7,8}";
        return phoneNum.matches(regex);
    }
    /**
     * 匹配邮箱
     * @param email
     * @return
     */
    public static boolean email(String email) {
        String regex = "[0-9a-zA-Z\\._-]{1,20}@[a-zA-Z0-9_-]{1,15}\\.[\\w\\._-]{1,10}";
        return email.matches(regex);
    }

    /**
     * 匹配银行卡卡号
     * @param card
     * @return
     */
    public static boolean card(String card) {
        String regex = "[0-9]{16,19}";
        return card.matches(regex);
    }

    /**
     * 匹配身份证号码
     * @param idCard
     * @return
     */
    public static boolean idCard(String idCard) {
        String regex = "^\\d{17}[\\d|Xx]|\\d{15}$";
        return idCard.matches(regex);
    }
    /**
     * 是否包含emoji表情
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

}
