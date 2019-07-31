package net.sf.jsqlparser;

import net.ipip.ipdb.District;
import net.ipip.ipdb.DistrictInfo;

import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            District db = new District("E:\\my_google_drive_download\\data\\mydata4vipday2_cn.ipdb");

            System.out.println(Arrays.toString(db.find("118.24.75.135", "CN")));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
