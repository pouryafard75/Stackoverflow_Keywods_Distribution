package com.company;

import javafx.collections.transformation.SortedList;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.*;

public class Main {
    static Map<String, List<Integer>> map = new LinkedHashMap<String,List<Integer>>();
    static Map<String, Integer> countMap = new LinkedHashMap <String, Integer>();

    public static void main(String[] args) {
        float[][] res;
        String fileName = "qid_tag.txt";
        String line = null;
        String[] linearr = new String[1];
        int id;
        String tag = "";
        int i = 0;


        try {
            FileReader fileReader =
                    new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if (i == 0)
                {
                    i ++;
                    continue;
                }

                linearr = line.split("\\s+");
                id = Integer.parseInt(linearr[0]);
                tag = linearr[1];
                List<Integer> bl = map.get(tag);

                if (bl == null || bl.size() == 0) {
                    List<Integer> tempList = new ArrayList<Integer>();
                    tempList.add(id);
                    map.put(tag,tempList);
                }
                else
                {
                    bl.add(id);

                }
                i++;
            }

            bufferedReader.close();

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }


        res = new float[map.size()][map.size()];
        String[][] titles = new String[map.size()+1][map.size()+1];

        Set<String> tags = map.keySet();
        for (String tag1:
             tags) {
            countMap.put(tag1,map.get(tag1).size());
        }


//        System.out.println(findDiff("java","swing"));
//
//        int ti = 1;
//        for (String tag1 :
//                tags) {
//            titles[0][ti] = tag1;
//            ti++;
//        }
//
//        ti = 1;
//        for (String tag1 :
//                tags) {
//            titles[ti][0] = tag1;
//            ti++;
//        }
//
//        writeMatrix2("title.csv",titles);

        int ii = 0;
        int jj = 0;


        for (String tag1 :
                tags) {

            for (String tag2 :
                    tags) {

                if(jj<ii){
                    res[ii][jj] = findDiff(tag1, tag2);
                jj++;
                }
                else
                    break;

            }
            System.out.println("processed lines: " + ii);
            ii++;
            jj = 0;
        }

        writeMatrix("dis.csv",res);
    }
    public static float findDiff(String tag1,String tag2)
    {
        List<Integer> tag1List = map.get(tag1);
        List<Integer> tag2List = map.get(tag2);
        int size1 = countMap.get(tag1);
        int size2 = countMap.get(tag2);
        tag1List.retainAll(tag2List);
        int inter = tag1List.size();
                if (inter == 0)
            return 1;
        else {
            float dis = 1 - ((float) inter / (float) (size1 + size2 - inter));
            return (dis);
        }
    }


    public static void writeMatrix(String filename, float[][] matrix) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    bw.write(matrix[i][j] + ((j == matrix[i].length - 1) ? "" : ","));
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {}
    }

    public static void writeMatrix2(String filename, String[][] matrix) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    bw.write(matrix[i][j] + ((j == matrix[i].length - 1) ? "" : ","));
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {}
    }

}

