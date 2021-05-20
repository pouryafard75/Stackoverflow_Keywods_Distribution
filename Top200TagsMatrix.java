package com.company;

import javafx.collections.transformation.SortedList;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.*;

public class Main {
    static Map<String, List<Integer>> map = new LinkedHashMap<String,List<Integer>>();
    static Map<String, Integer> countMap = new LinkedHashMap <String, Integer>();
    static LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

    public static void main(String[] args) {
        String[][] res;
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


        res = new String[201][201];

        Set<String> tags = map.keySet();

        for (String tag1:
             tags) {
            countMap.put(tag1,map.get(tag1).size());
        }

        Map<String, Integer> unSortedMap = countMap;

    
        unSortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        Set<String> sortedTags = sortedMap.keySet();





        int ti = 1;
        int count = 0;
        res[0][0]="Tags";
        for (String tag1 :
                sortedTags) {
            res[0][ti] = tag1;
            ti++;
            count++;
            if(count==200)
                break;
        }

        ti = 1;
        count = 0;
        for (String tag1 :
                sortedTags) {
            res[ti][0] = tag1;
            ti++;
            count++;
            if(count==200)
                break;
        }

        int ii = 1;
        int jj = 1;
        count = 0;

        for (String tag1 :
                sortedTags) {

            for (String tag2 :
                    sortedTags) {

                if(jj<ii){
                    res[ii][jj] = String.valueOf(findDiff(tag1, tag2));
                
                jj++;
                }
                else
                    break;

            }
            System.out.println("processed lines: " + ii);
            ii++;
            jj = 1;
            count++;
            if(count==200)
                break;

        }

        writeMatrix("dis.csv",res);
    }
    public static float findDiff(String tag1,String tag2)
    {
        List<Integer> tag1List = map.get(tag1);
        List<Integer> tag2List = map.get(tag2);

        int size1 = sortedMap.get(tag1);
        int size2 = sortedMap.get(tag2);

        int inter = intersection(tag1List, tag2List);
//        System.out.println("inter count " + inter);
                if (inter == 0)
            return 1;
        else {
            float dis = 1 - ((float) inter / (float) (size1 + size2 - inter));
            return (dis);
        }
    }

    public static int intersection(List<Integer> nums1, List<Integer> nums2) {
        HashSet<Integer> set1 = new HashSet<Integer>();
        for(int i: nums1){
            set1.add(i);
        }

        HashSet<Integer> set2 = new HashSet<Integer>();
        for(int i: nums2){
            if(set1.contains(i)){
                set2.add(i);
            }
        }

        int result = 0;
        for(int n: set2){
            result++;
        }

        return result;
    }

    public static void writeMatrix(String filename, String[][] matrix) {
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

