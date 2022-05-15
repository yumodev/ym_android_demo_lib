package com.yumo.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.yumo.demo.anno.YmClassTest;
import com.yumo.demo.config.YmTestDefine;
import com.yumo.demo.entry.YmClass;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import dalvik.system.DexFile;

/**
 * Created by yumodev on 3/18/16.
 */
public class YmClassUtils {
    private static String LOG_TAG = "YmClassUtils";

    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";

    private static final String SECONDARY_FOLDER_NAME = "code_cache" + File.separator +
            "secondary-dexes";

    private static final String PREFS_FILE = "multidex.version";
    private static final String KEY_DEX_NUMBER = "dex.number";


    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE,
                Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                        ? Context.MODE_PRIVATE
                        : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }

    public static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException, IOException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        File sourceApk = new File(applicationInfo.sourceDir);
        File dexDir = new File(applicationInfo.dataDir, SECONDARY_FOLDER_NAME);

        List<String> sourcePaths = new ArrayList<String>();
        sourcePaths.add(applicationInfo.sourceDir); //add the default apk path

        //the prefix of extracted file, ie: test.classes
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
        //the total dex numbers
        int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);

        for (int secondaryNumber = 2; secondaryNumber <= totalDexNumber; secondaryNumber++) {
            //for each dex file, ie: test.classes2.zip, test.classes3.zip...
            String fileName = extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX;
            File extractedFile = new File(dexDir, fileName);
            if (extractedFile.isFile()) {
                sourcePaths.add(extractedFile.getAbsolutePath());
                //we ignore the verify zip part
            } else {
                throw new IOException("Missing extracted secondary dex file '" +
                        extractedFile.getPath() + "'");
            }
        }

        return sourcePaths;
    }

    public static List<String> getAllClasses(Context context) throws PackageManager.NameNotFoundException, IOException {
        List<String> classNames = new ArrayList<String>();
        for (String path : getSourcePaths(context)) {
            try {
                DexFile dexfile = null;
                if (path.endsWith(EXTRACTED_SUFFIX)) {
                    //NOT use new DexFile(path), because it will throw "permission error in /data/dalvik-cache"
                    dexfile = DexFile.loadDex(path, path + ".tmp", 0);
                } else {
                    dexfile = new DexFile(path);
                }
                Enumeration<String> dexEntries = dexfile.entries();
                while (dexEntries.hasMoreElements()) {
                    classNames.add(dexEntries.nextElement());
                }
            } catch (IOException e) {
                throw new IOException("Error at loading dex file '" +
                        path + "'");
            }
        }
        return classNames;
    }

    public static Method[] getMethodData(Class<?> c) {
        Method[] m = c.getDeclaredMethods();
        Vector<Method> v = new Vector();

        for (int i = 0; i < m.length; i++) {
            if (m[i].getName().indexOf(YmTestDefine.TEST_METHOD_PREFIX) == 0) {
                v.add(m[i]);
            }
        }

        Method[] tmp = new Method[v.size()];
        v.copyInto(tmp);
        return tmp;
    }

    public static String getApkName(Context context){
        String packageName = context.getPackageName();
        String apkName = null;
        try {
            apkName = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return apkName;
    }

    /**
     * 获取一个子类
     * yumodev
     * void
     * 2014-11-6
     */
    public static String getConfigPackageData(Context context, Class<?> superClass){
        String packageName = YmUIDemoManager.getInstance().getAppPackageName(context);
        String apkName = getApkName(context);
        if (TextUtils.isEmpty(apkName)){
            return "";
        }

        YmTestLog.i(LOG_TAG, apkName);
        DexFile dexFile;
        try {
            dexFile = new DexFile(apkName);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        String subClassName = "";
        try{
            Enumeration<String> apkClassNames = dexFile.entries();
            while (apkClassNames.hasMoreElements()) {
                String className = apkClassNames.nextElement();
                YmTestLog.i(LOG_TAG, className);
                if (className.indexOf('$') >= 0){
                    continue;
                }

                final Class<?> cls = Class.forName(className);
                if(superClass.isAssignableFrom(cls)
                        && ! className.equals(superClass.getName())) {
                    subClassName = className;
                  break;
                }
            }
        }catch ( ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                dexFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return subClassName;
    }

    /**
     * 获取一个包下的子类
     * yumodev
     * void
     * 2014-11-6
     */
    public static List<YmClass> getAllSubClassInPackage(Context context, List<Class<?>> superClassList, String parentPackageName){
        String packageName = context.getPackageName();
        String apkName = getApkName(context);
        if (TextUtils.isEmpty(apkName)){
            return null;
        }

        if (TextUtils.isEmpty(parentPackageName)){
            parentPackageName = packageName;
        }

        YmTestLog.i(LOG_TAG, apkName);
        DexFile dexFile;
        try {
            dexFile = new DexFile(apkName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        List<YmClass> dataList = new ArrayList<>();
        try{
            Enumeration<String> apkClassNames = dexFile.entries();
            while (apkClassNames.hasMoreElements()) {
                try {
                    String className = apkClassNames.nextElement();
                    YmTestLog.i(LOG_TAG, className);
                    if (className.indexOf('$') >= 0 || !className.startsWith(parentPackageName)){
                        continue;
                    }

                    final Class<?> cls = Class.forName(className);
                    if (!cls.isAnnotation() && cls.getAnnotations() != null && cls.isAnnotationPresent(YmClassTest.class)){
                        YmClassTest classTest = cls.getAnnotation(YmClassTest.class);
                        dataList.add(YmClass.createInstance(cls, classTest.name()));
                    }else if (isSuperClass(cls, superClassList)){
                        dataList.add(YmClass.createInstance(cls, ""));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }finally {
            try {
                dexFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    private static boolean isSuperClass(Class<?> cls, List<Class<?>> superClassList) {
        boolean result = false;
        for (int n = 0; n < superClassList.size(); n++) {
            Class<?> superClass = superClassList.get(n);
            if (superClass.isAssignableFrom(cls)
                    && !cls.getName().equals(superClass.getName())
                    ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static String getSimpleName(String className)  {
        int pos = className.lastIndexOf('.');
        if(pos < 0) {
            return className;
        } else {
            return className.substring(pos + 1);
        }
    }
}
