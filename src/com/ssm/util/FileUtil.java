package com.ssm.util;

import java.io.*;
import java.util.Scanner;

public class FileUtil {
    public static void write(String content,String filename){

            System.out.println(content);
            String fileName = "/Users/xixi/Desktop/SSM/library/"+filename;
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName,true));
                out.append(content);
                out.newLine();  //注意\n不一定在各种计算机上都能产生换行的效果
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

    }

    public static   String[] read() {
        File file = new File("/Users/xixi/Desktop/SSM/library/comments.txt");
        //行数
        int lineCount=1;
        try {
            FileInputStream fis=new FileInputStream(file);
            Scanner scanner=new Scanner(fis);
            while(scanner.hasNextLine()){
                scanner.nextLine();
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] sentences = new String[lineCount];

        BufferedReader reader = null;

        try {

            //System.out.println("以行为单位读取文件内容，一次读一整行：");

            reader = new BufferedReader(new FileReader(file));

            String tempString = null;

            int line = 0;

            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {

                if (tempString.equals("")) {
                    continue;
                }
                sentences[line] = tempString;

                // 显示行号
                //System.out.println("line " + line + ": " + tempString);

                line++;

            }

            reader.close();

        } catch (IOException e) {


        }

        return sentences;
    }

}
