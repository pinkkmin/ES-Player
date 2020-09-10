package com.player.es.Utils;

import com.player.es.cmf.Domain.POJO.MatchDataPojo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUtils {
    public BufferedReader readFile(MultipartFile file) {
        try {
            InputStreamReader is = new InputStreamReader(file.getInputStream(), "GBK");
            BufferedReader br = new BufferedReader(is);
            return br;
        }catch (Exception e){
            return null;
        }
    }
    boolean isHasItem(Map<String,Integer> data){
        Map<String, Integer> map = new HashMap<>(data);
        String enItem[] = {"name","number","score","free","foul","block",
                "steal","bound","time","assist","turnover"};
        String cnItem[] ={"姓名","号码","得分","篮板","助攻","抢断","罚球",
        "时间","盖帽","失误","犯规"};
        if(map.get(enItem[0])!=-1)
        {
            for (int i = 0; i < enItem.length; i++) {
                if (map.get(enItem[i]) == null && !enItem[i].equals("number")) return false;
                map.remove(enItem[i]);
            }
        }
        else {
            for (int i = 0; i < cnItem.length; i++) {
                if (map.get(cnItem[i]) == null && !enItem[i].equals("号码")) return false;
                map.remove(cnItem[i]);
            }
        }
//        System.out.println(map);
        if(map.isEmpty()) return true;
        return false;
    } //
    public LinkedHashMap<String,Object>  doMatchDataByCSV(MultipartFile file) {
        BufferedReader br = readFile(file);
              if(br == null) return null;
        LinkedHashMap<String,Object> data = new LinkedHashMap<>();
       try{
           String line = br.readLine();
           if(line ==null) return null;
           String itemList[] = line.split(",");
           Map<String,Integer> item = new HashMap(); //key-value ：index-name
           Map<Integer,String> name = new HashMap<>(); //key-value：name-index 对应字段名称与index
           for(int index = 0;index<itemList.length;index++) {
               item.put(itemList[index],index);
               name.put(index,itemList[index]);
           }
           if(!isHasItem(item)) {
               data.put("code",250);   //文件格式不对，或缺失对应数据项
               return data;
           }
           itemList = br.readLine().split(",");
           System.out.println(itemList.length);
           ArrayList<MatchDataPojo> homePlayer = new ArrayList<>(),awayPlayer = new ArrayList<>();
          while(!itemList[0].equals("end") && !itemList[0].equals("")) {
              homePlayer.add(new MatchDataPojo(name, itemList));
              line = br.readLine();
              System.out.println(line);
              itemList = line.split(",");
              if(itemList.length == 0) break;
          }
          data.put("home",homePlayer);
          if(itemList.length != 0 && itemList[0].equals("end")) {
              data.put("away",awayPlayer);
              return data;
          }
          itemList = br.readLine().split(",");
          while(!itemList[0].equals("end") && !itemList[0].equals("")) {
               awayPlayer.add(new MatchDataPojo(name,itemList));
              line = br.readLine();
              System.out.println(line);
              itemList = line.split(",");
//              System.out.println(itemList.length);
              if(itemList.length == 0) break;
//              System.out.println(itemList[0]);
           }
           data.put("away",awayPlayer);
           data.put("code",200);
          return data;
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
    public LinkedHashMap<String,Object> doMatchDataByXLS(MultipartFile file){
        LinkedHashMap<String,Object> data = new LinkedHashMap<>();
        try {
            HSSFWorkbook whb = new HSSFWorkbook(file.getInputStream());
            if(whb.getNumberOfSheets()==0) {
                data.put("code",400);
                return data;
            };
            HSSFSheet sheet = whb.getSheetAt(0);
            int last = sheet.getLastRowNum();
            if(last==0 || last == 1) {
                data.put("code",400);
                return data;
            }
            Map<String,Integer> item = new HashMap();
            Map<Integer,String> name = new HashMap<>();
            ArrayList<MatchDataPojo> homePlayer = new ArrayList<>(),awayPlayer = new ArrayList<>();
            for (int j = 0; j < sheet.getRow(0).getLastCellNum() ; j++) {
                item.put(sheet.getRow(0).getCell(j).toString(),j);
                name.put(j,sheet.getRow(0).getCell(j).toString());
            }
            int row = 1;
            for (; row < last; row++) {
                if(sheet.getRow(row) == null)  break;
                int col = sheet.getRow(row).getLastCellNum();
                String itemList[] = new String[col];
                for (int j = 0; j < col ; j++) {
                    System.out.print(sheet.getRow(row).getCell(j).toString()+" ");
                    itemList[j] = sheet.getRow(row).getCell(j).toString();
                }
                homePlayer.add(new MatchDataPojo(name, itemList));
                System.out.println();
            }
            for(;row<last;row++) {
                if(sheet.getRow(row) == null) {
                    System.out.println("end");
                    break;
                }
                int col = sheet.getRow(row).getLastCellNum();
                String itemList[] = new String[col];
                for (int j = 0; j < col ; j++) {
                    System.out.print(sheet.getRow(row).getCell(j)+" ");
                    itemList[j] = sheet.getRow(row).getCell(j).toString();

                }
                awayPlayer.add(new MatchDataPojo(name, itemList));
                System.out.println();
            }
            data.put("home",homePlayer);
            data.put("away",awayPlayer);
            data.put("code",200);
        }catch (Exception e){
            data.put("code",401);
            e.printStackTrace();
        }
        System.out.println(data);
        return data;
    }
}
