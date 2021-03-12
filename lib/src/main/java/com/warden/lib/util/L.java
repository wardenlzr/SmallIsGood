package com.warden.lib.util;

import android.util.Log;

/**
 * Created by yubin 2021/1/7 0007  16:26
 */
public class L {
    public static void e() {
        e("【空参打印】",4);
    }

    /**
     * 4: L.e(s)的地方
     * @param s
     */
    public static void e(String s) {
        e(s, 4);
    }
    public static void e(String s, int stackIndex) {
        String SUFFIX = ".java";

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

//        for (int i = 0; i < stackTrace.length; i++) {
//            StackTraceElement element = stackTrace[i];
//            String s1 = element.getClassName() + "=====" + element.getLineNumber() +  element.getMethodName();
//            System.out.print(s1);
//        }
        Log.e("length", "stackIndex=" + stackIndex);
        StackTraceElement targetElement = stackTrace[stackIndex];
        StackTraceElement targetElement1 = stackTrace[stackIndex+1];
        //类名
        String className = targetElement.getClassName();
        String className1 = targetElement1.getClassName();
        String[] classNameInfo = className.split("\\.");
        String[] classNameInfo1 = className1.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        if (classNameInfo1.length > 0) {
            className1 = classNameInfo1[classNameInfo1.length - 1] + SUFFIX;
        }
        if (className1.contains("$")) {
            className1 = className1.split("\\$")[0] + SUFFIX;
        }
        String methodName = targetElement.getMethodName();
        String methodName1 = targetElement.getMethodName();
        //方法名
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String methodNameShort1 = methodName1.substring(0, 1).toUpperCase() + methodName1.substring(1);

//        System.out.println("className=" + className);
//        System.out.println("lineNumber=" + lineNumber);
//        System.out.println("methodNameShort=" + methodNameShort);
        String headString = "[ (" + className + ":" + targetElement.getLineNumber() + ")#" + methodNameShort + " ]"
                          + "[ (" + className1 + ":" + targetElement1.getLineNumber() + ")#" + methodNameShort1 + " ]";
        Log.e(headString + "", s + "");
    }

}
