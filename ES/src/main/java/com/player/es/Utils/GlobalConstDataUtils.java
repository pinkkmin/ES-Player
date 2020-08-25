package com.player.es.Utils;

import com.google.common.collect.Lists;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
public class GlobalConstDataUtils {
    private   static  ArrayList<String> en8NameList = Lists.newArrayList(
            "score","assist", "bound","steal","block","turnover","foul","free");
    private   static  ArrayList<String> enNameList = Lists.newArrayList(
            "score","assist", "bound","steal","block","turnover","foul");
    private   static  ArrayList<String> en5NameList = Lists.newArrayList(
            "score","assist", "bound","steal","block");
    private static  ArrayList<String> cnNameList = Lists.newArrayList(
            "得分","助攻","篮板","抢断","盖帽","失误","犯规");
    private static  ArrayList<String> cn5NameList = Lists.newArrayList(
            "得分","助攻","篮板","抢断","盖帽");
    private static ArrayList<String> cnCmpNameList = Lists.newArrayList(
            "得分","助攻","篮板","抢断","盖帽","罚球","犯规","失误"
    );
    private static ArrayList<String> maxNameList = Lists.newArrayList(
            "maxScore","maxAssist","maxBound","maxSteal","maxBLock"
    );
    private static ArrayList<String> max7NameList = Lists.newArrayList(
            "maxScore","maxAssist","maxBound","maxSteal","maxBlock","maxTurnOver","maxFoul"
    );
    private   static  ArrayList<String> allTeamList = Lists.newArrayList(
            "allTeamScore","allTeamAssist", "allTeamBound","allTeamSteal",
            "allTeamBlock","allTeamTurnover","allTeamFoul");
    public  ArrayList<String> getEnNameList() {
        return enNameList;
    }
    public  ArrayList<String> getCnNameList() {
        return cnNameList;
    }
    public  ArrayList<String> getEn8NameList() {return en8NameList;}
    public  ArrayList<String> getCn5NameList(){return cn5NameList;}
    public ArrayList<String> getEn5NameList(){return en5NameList;}
    public ArrayList<String> getCnCmpNameList(){return cnCmpNameList;}
    public ArrayList<String> getAllTeamList() {return allTeamList;}
    public ArrayList<String> getMaxNameList(){return maxNameList;}
    public ArrayList<String> getMax7NameList() {return max7NameList;}

}
