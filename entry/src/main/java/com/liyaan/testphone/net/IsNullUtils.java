package com.liyaan.testphone.net;

public class IsNullUtils {

    public static boolean isNotEmpty(String content){
        if (content== null || "".equals(content) || content.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
