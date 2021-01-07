package com.warden.lib.util;

import android.util.Log;

/**
 * Created by yubin 2021/1/7 0007  16:26
 */
public class L {
    public static void e(String s) {
        int STACK_TRACE_INDEX = 2;
        String SUFFIX = ".java";

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

//        for (int i = 0; i < stackTrace.length; i++) {
//            StackTraceElement element = stackTrace[i];
//            String s1 = element.getClassName() + "=====" + element.getLineNumber() +  element.getMethodName();
//            System.out.print(s1);
//        }
        StackTraceElement targetElement1 = stackTrace[3];
        StackTraceElement targetElement = stackTrace[4];
        //类名
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }
        //方法名
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

//        System.out.println("className=" + className);
//        System.out.println("lineNumber=" + lineNumber);
//        System.out.println("methodNameShort=" + methodNameShort);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ]";
         headString += "called:[ (" + targetElement1.getClassName() + ":" + targetElement1.getLineNumber() + ")#" + targetElement1.getMethodName() + " ] ";
        Log.e(headString, s);
    }

}
