package com.example.seleniumdemo.jsoncompilation;

import com.example.seleniumdemo.jsoncompilation.jsonbody.*;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class JsonControllerMisc {
    @PostMapping("/advocateNameBasic")
    @ResponseBody
    public ArrayList<Map<String, Object>> advocateName(@Valid @RequestBody JsonBodyInputAdvocateName j) {
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        String input = new Gson().toJson(j);

        try {
            DistrictCourt d = new DistrictCourt("chrome");
            int viewTabsNo = d.advocateName(j);

            for (int i = 0; i < viewTabsNo && i < j.getRecordsReturned(); i++) {
                try {
                    LinkedHashMap<String, String> out = d.casePageAdvocateBasic(i);
                    LinkedHashMap<String, String> temp = new LinkedHashMap<>();
                    JsonBodyOutputToCaseNumber jo = new JsonBodyOutputToCaseNumber();

                    temp.put("state_val", j.getState_val());
                    temp.put("district_val", j.getDistrict_val());
                    temp.put("courtComplexEstb", j.getCourtComplexEstb());
                    temp.put("bench_val", j.getBench_val());
                    temp.put("ct", out.get("CT"));
                    temp.put("cn", out.get("CN"));
                    temp.put("cy", out.get("CY"));
                    temp.put("recordsReturned", "1");
                    jo.setIndex(out.get("Index"));
                    jo.setTitle(out.get("Title"));
                    jo.setCourt(out.get("Court"));
                    jo.setCaseNumber(temp);
                    jo.setLastUpdated();

                    String t = new Gson().toJson(jo);
                    ret.add(new JSONObject(t).toMap());
                } catch (Exception e) {
                    System.out.println("Index [" + i + "] has failed: " + e);
                    ret.add(new JSONObject(new ErrorHandling(i, new JSONObject(input).toMap(), e)).toMap());
                }
            }

            d.driverQuit();
        } catch (Exception e) {
            e.printStackTrace();
            ret.add(new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap());
        }

        return ret;
    }

    @PostMapping("/cnrNumberBasic")
    @ResponseBody
    public Map<String, Object> cnrNumber(@Valid @RequestBody JsonBodyInputCNRNumber j) {
        Map<String, Object> ret;
        String input = new Gson().toJson(j);

        try {
            eCourt ec = new eCourt("chrome");

            ec.search(j);
            JsonBodyOutputToCaseNumber out = ec.casePageBasic();

            String t = new Gson().toJson(out);
            ret = new JSONObject(t).toMap();
        } catch (Exception e) {
            e.printStackTrace();
            ret = new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap();
        }

        return ret;
    }
}