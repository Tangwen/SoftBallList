package com.twm.pt.softball.softballlist.Manager;


import android.content.Context;
import android.util.Log;

import com.twm.pt.softball.softballlist.utility.L;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class BackupManager {
    private static BackupManager mBackupManager;
    public static BackupManager getinstance() {
        if(mBackupManager==null) {
            mBackupManager = new BackupManager();
        }
        return mBackupManager;
    }

    public void localBackup(String outputFilePath, String data) {
        localBackup(new File(outputFilePath), data);
    }
    public void localBackup(File outputFile, String data) {
        writeStringAsFile(outputFile, data);
    }

    public String[] getLocalRestoreFileList(File filePath, String parserKey) {
        File files[] = filePath.listFiles();
        ArrayList<String> fileList = new ArrayList<>();
        for (File file:files) {
            if(file.getName().indexOf(parserKey) >= 0) {
                fileList.add( file.getName());
            }
        }
        return fileList.toArray(new String[fileList.size()]);
    }

    public String localRestore(String fileName) {
        return localRestore(new File(fileName));
    }

    public String localRestore(File readFile) {
        return readFileAsString(readFile);
    }

    public static void writeStringAsFile(File outputFile, String fileContents) {
        try {
            FileWriter out = new FileWriter(outputFile);
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            L.e(e);
        }
    }

    public static String readFileAsString(File readFile) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(readFile));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
            L.e(e);
        } catch (IOException e) {
            L.e(e);
        }
        return stringBuilder.toString();
    }

}
