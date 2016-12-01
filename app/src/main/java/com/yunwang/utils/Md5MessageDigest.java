package com.yunwang.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/8/8.
 * Md5加密帮助类
 */


/**
 *
 * 一般我们在实现MD5加密主要有以下几个步骤

 1 首先得到一个信息摘要器 MessageDigest 然后设置MD5

 2  我们获取要加密的数据 例如 password=“123456”

 3 将这些数据转换成字节数据 password.getBytes()

 4 循环对每个字节数进行处理

 这里主要是做2个处理 一个是 对每个字节数据进行 与运算 一般是与上一个16进制的数

 例如 int number = p $ 0xff;

 然后 将得到的number'进行转换为16进制的数

 Integer.toHexString(number);

 如果这个数转换后的长度为1 那么我们就补0

 不满八个二进制那么我们就补全

 算法思想：

 1 用每个byte去和11111111做与运算 并且得到的是一个int类型的值 byte&11111111

 2 把int类型转换成16进制并返回String类型

 3 不满8个2进制位就补全
 *
 */
public class Md5MessageDigest {

    public static String getMD5Str(String str) {
        //信息摘要器
        MessageDigest messageDigest = null;
        try {
            //MessageDigest通过其getInstance系列静态函数来进行实例化和初始化
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xff & byteArray[i]).length() == 1)//表示为8位，需要转换成16位（16位加密）
                md5StrBuff.append("0").append(Integer.toHexString(0xff & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xff & byteArray[i]));
        }
        // 32位加密
        return md5StrBuff.toString();//最终会变成32位
    }
}
