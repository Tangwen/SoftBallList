package com.twm.pt.softball.softballlist.utility;


import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 用來記錄TRACE ALL STACK
 *
 * 	定義:
 預設logList為空

 當使用traceNow時會看IS_DEBUG與logList決定要不要印
 但evenLog都會append

 但當發生error或用L.check()時只會看IS_DEBUG決定要不要印
 不會看logList有沒有目前的class

 用法:

 若目前是在Main.java
 在onCreate裡加一段
 L.logList.put(Main.class.getName(), true);
 就會印此java的log
 若
 L.logList.put(Main.class.getName(), false);
 就不會印此java的log
 *
 *
 * */
public final class L {
    public final static String TAG = "SoftBall";
    /**用來記錄所有EVENT，呼叫traceNow()就記一筆
     * 當呼叫L.printEventLog()就會印出來*/
    public static StringBuilder evenLog=new StringBuilder();

    /**判斷現在要不要印log*/
    public final static boolean IS_DEBUG=true;		//上線版本 false : log都不要印出

    /** 要印出LOG的Class清單,key是className,value是boolean(是否要印) */
    public static ConcurrentHashMap<String, Boolean> logList = new ConcurrentHashMap<String, Boolean>();

    /**判斷現在要不要印全部的log，層級在IS_DEBUG之下*/
    public final static boolean IS_PRINT_ALL=true;

    /**預設要印LOG的清單*/
    static{
        //L.logList.put(Main.class.getName(), true);
        //L.logList.put(EzpeerAudioPlayer.class.getName(), true);
        //L.logList.put(EzpeerAudioPlayerService.class.getName(), true);
        //L.logList.put(hot_list.class.getName(), true);
        //L.logList.put(Common_APIServer.class.getName(), true);
        //L.logList.put(MyMusicDBBehavior.class.getName(), true);
        //L.logList.put(UserMusicListDAO.class.getName(), true);

    }


    /**
     * 用來trace目前跑了幾個class和method
     * */
    public final static void traceAll() {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        StackTraceElement s=null;
        try {
            Log.d(TAG, "******************  Trace Log Start  ******************" );
            final StackTraceElement[] st = Thread.currentThread()
                    .getStackTrace();
            for (int i=0;i<st.length;i++) {
                s=st[i];
                nowClass = s.getClassName();
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = s.getMethodName();
                nowLineNumber = s.getLineNumber();
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]" );
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        Log.d(TAG, "******************  Trace Log End  ******************" );
    }

    /**
     * 印出所有EVENT
     * 可用L.traceNow()來append eventLog
     * */
    public final static void printEventLog(){
        if(IS_DEBUG){
            Log.d(TAG, "******************  Even Log Start  ******************" );
            Log.d(TAG, evenLog.toString());
            Log.d(TAG, "******************  Even Log End  ******************" );
        }
    }

    /**
     * 用來記錄Exception
     * 會印出eventLog
     * eventLog可用L.traceNow()來append
     * */
    public final static void e(String TAG, String msg,Exception e) {
        if(IS_DEBUG){
            Log.e(TAG, msg, e);
            printEventLog();
        }

    }

    /**
     * 用來記錄Exception
     * 會印出eventLog
     * eventLog可用L.traceNow()來append
     * */
    public final static void e(String msg,Exception e) {
        if(IS_DEBUG){
            Log.e(TAG, msg,  e);
            printEventLog();
        }
    }

    /**
     * 用來記錄Exception
     * 會印出eventLog
     * eventLog可用L.traceNow()來append
     * */
    public final static void e(Exception e) {
        if(IS_DEBUG){
            Log.e(TAG, "",  e);
            printEventLog();
        }
    }

    public final static void e(OutOfMemoryError e) {
        //if (!showDebug) return;
        if(IS_DEBUG){
            try {
                Log.e(TAG, "",  e);
            } catch (RuntimeException e2) {
            }catch (Exception e2) {
            }
        }
        try {
            //==========================
        } catch (RuntimeException e2) {
            L.e(e);
        }catch (Exception e2) {
            L.e(e);
        }
        System.gc();
        Runtime.getRuntime().gc();
    }

    /**
     * 用來記錄Exception classname, method, line number
     * 但不會印出eventLog
     * */
    @SuppressWarnings("unused")
    public final static void w(String msg) {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.w(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]["+msg+"]");
            }
        } catch (Exception e) {
            Log.e(TAG, nowMethod, e);
            Log.e(TAG, "");
        }
    }

    /**
     * 用來記錄Exception classname, method, line number
     * 但不會印出eventLog
     * */
    @SuppressWarnings("unused")
    public final static void i(String msg) {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.i(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]["+msg+"]");
            }
        } catch (Exception e) {
            Log.e(TAG, nowMethod, e);
            Log.e(TAG, "");
        }
    }

    /**
     * 用來記錄Exception classname, method, line number
     * 但不會印出eventLog
     * */
    public final static void e(String msg) {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(IS_DEBUG){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.e(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]["+msg+"]");
            }
        } catch (Exception e) {
            Log.e(TAG, nowMethod, e);
            Log.e(TAG, "");
        }
    }

    /**
     * 用來記錄目前跑到哪一個CLASS、METHOD、行數和訊息
     * */
    @SuppressWarnings("unused")
    public final static void d(String TAG, String msg) {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]"
                        + msg);
            }
        } catch (Exception e) {
            Log.e(TAG, nowMethod, e);
            Log.d(TAG, msg);
        }
    }

    /**
     * 用來記錄哪邊還要繼續寫完
     * */
    public final static void check() {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(IS_DEBUG){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]******************************************************");
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]    TODO 未完成");
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]******************************************************");
            }
        } catch (Exception e) {
            Log.e(TAG, nowMethod, e);
        }
    }

    /**
     * 用來記錄哪邊還要繼續寫完
     * */
    public final static void check(String msg) {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(IS_DEBUG){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]******************************************************");
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]    TODO "+msg);
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]******************************************************");
            }
        } catch (Exception e) {
            Log.e(TAG, nowMethod, e);
        }
    }

    /**
     * 用來記錄目前跑到哪一個CLASS、METHOD、行數和訊息
     * */
    @SuppressWarnings("unused")
    public final static void d(String msg) {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]"
                        + msg);
            }
        } catch (Exception e) {
            Log.e(TAG, nowClass, e);
            Log.d(TAG, nowClass + "[" + nowMethod + "][" + nowLineNumber + "]"
                    + msg);
        }
    }

    /**
     * 用來記錄目前跑到哪一個CLASS、METHOD、行數和訊息
     * */
    @SuppressWarnings("unused")
    public final static void d() {
        String nowClass = "";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3]
                        .getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3]
                        .getLineNumber();
                Log.d(TAG, "["+nowClass + "][" + nowMethod + "][" + nowLineNumber + "]");
            }
        } catch (Exception e) {
            Log.e(TAG, nowClass, e);
            Log.d(TAG, nowClass + "[" + nowMethod + "][" + nowLineNumber + "]");
        }
    }

    /**
     * 用來記錄目前跑到哪一個CLASS、METHOD、行數
     * 同時append到evenLog
     * 當呼叫L.printEventLog()可以印出所有eventLog
     * */
    @SuppressWarnings("unused")
    public final static void traceNow() {
        String nowClass = "",nowClass2="";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            nowClass2=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
            nowMethod = Thread.currentThread().getStackTrace()[3]
                    .getMethodName();
            nowLineNumber = Thread.currentThread().getStackTrace()[3]
                    .getLineNumber();
            final String log="["+nowClass2 + "][" + nowMethod + "][" + nowLineNumber + "]";
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                Log.d(TAG, log );
            }
            evenLog.append(log).append("\n");
        } catch(ArrayIndexOutOfBoundsException a){
            printEventLog();
            evenLog=new StringBuilder();
        }catch (Exception e) {
            Log.e(TAG, nowClass, e);
            Log.d(TAG, nowClass + "[" + nowMethod + "][" + nowLineNumber + "]" );
        }
    }

    /**
     * 用來記錄目前跑到哪一個CLASS、METHOD、行數和訊息
     * 同時append到evenLog
     * 當呼叫L.printEventLog()可以印出所有eventLog
     * */
    @SuppressWarnings("unused")
    public final static void traceNow(String msg) {
        String nowClass = "",nowClass2="";
        String nowMethod = "";
        int nowLineNumber = 0;
        try {
            nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
            if(nowClass.indexOf("$")!=-1)
                nowClass=nowClass.substring(0,nowClass.indexOf("$"));
            nowClass2=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
            nowMethod = Thread.currentThread().getStackTrace()[3]
                    .getMethodName();
            nowLineNumber = Thread.currentThread().getStackTrace()[3]
                    .getLineNumber();
            final String log="["+nowClass2 + "][" + nowMethod + "][" + nowLineNumber + "]"+msg;
            if(IS_DEBUG&&(IS_PRINT_ALL||(logList.containsKey(nowClass)&&logList.get(nowClass)))){
                Log.d(TAG, log );
            }
            evenLog.append(log).append("\n");
        } catch(ArrayIndexOutOfBoundsException a){
            printEventLog();
            evenLog=new StringBuilder();
        }catch (Exception e) {
            Log.e(TAG, nowClass, e);
            Log.d(TAG, nowClass + "[" + nowMethod + "][" + nowLineNumber + "]"+msg );
        }
    }
}
