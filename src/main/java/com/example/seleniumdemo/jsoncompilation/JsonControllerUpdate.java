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
import java.util.Map;

@Controller
public class JsonControllerUpdate {
    @PostMapping("/caseNumberUpdate")
    @ResponseBody
    public ArrayList<Map<String, Object>> caseNumber(@Valid @RequestBody JsonBodyInputCaseNumber j) {
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        String input = new Gson().toJson(j);

        try {
            DistrictCourt d = new DistrictCourt("chrome");
            int viewTabsNo = d.caseNumber(j);
            int index = 0;

            for (int i = 0; i < viewTabsNo && index < j.getRecordsReturned(); i++) {
                try {
                    String t;

                    if (j.getFormat().equalsIgnoreCase("0")) {
                        JsonBodyOutput temp = d.casePage(i, true);
                        temp.setState_val(j.getState_val());
                        temp.setDistrict_val(j.getDistrict_val());
                        temp.setBench_val(j.getBench_val());
                        t = new Gson().toJson(temp);
                    } else {
                        JsonBodyOutputAlt temp = d.casePageAlt(i, true);
                        temp.setInputs(new JSONObject(input).toMap());
                        t = new Gson().toJson(temp);
                    }

                    ret.add(new JSONObject(t).toMap());
                    index++;
                } catch (Exception e) {
                    if (!e.getMessage().toLowerCase().contains("not a valid index")) {
                        System.out.println("Index [" + i + "] has failed: " + e);
                        ret.add(new JSONObject(new ErrorHandling(i, new JSONObject(input).toMap(), e)).toMap());
                    }
                }
            }

            d.driverQuit();
        } catch (Exception e) {
            e.printStackTrace();
            ret.add(new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap());
        }

        return ret;
    }

    @PostMapping("/partyNameUpdate")
    @ResponseBody
    public ArrayList<Map<String, Object>> partyName(@Valid @RequestBody JsonBodyInputPartyName j) {
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        String input = new Gson().toJson(j);

        try {
            DistrictCourt d = new DistrictCourt("chrome");
            int viewTabsNo = d.partyName(j);
            int index = 0;

            for (int i = 0; i < viewTabsNo && index < j.getRecordsReturned(); i++) {
                try {
                    String t;

                    if (j.getFormat().equalsIgnoreCase("0")) {
                        JsonBodyOutput temp = d.casePage(i, true);
                        temp.setState_val(j.getState_val());
                        temp.setDistrict_val(j.getDistrict_val());
                        temp.setBench_val(j.getBench_val());
                        t = new Gson().toJson(temp);
                    } else {
                        JsonBodyOutputAlt temp = d.casePageAlt(i, true);
                        temp.setInputs(new JSONObject(input).toMap());
                        t = new Gson().toJson(temp);
                    }

                    ret.add(new JSONObject(t).toMap());
                    index++;
                } catch (Exception e) {
                    if (!e.getMessage().toLowerCase().contains("not a valid index")) {
                        System.out.println("Index [" + i + "] has failed: " + e);
                        ret.add(new JSONObject(new ErrorHandling(i, new JSONObject(input).toMap(), e)).toMap());
                    }
                }
            }

            d.driverQuit();
        } catch (Exception e) {
            e.printStackTrace();
            ret.add(new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap());
        }

        return ret;
    }

    @PostMapping("/advocateNameUpdate")
    @ResponseBody
    public ArrayList<Map<String, Object>> advocateName(@Valid @RequestBody JsonBodyInputAdvocateName j) {
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        String input = new Gson().toJson(j);

        try {
            DistrictCourt d = new DistrictCourt("chrome");
            int viewTabsNo = d.advocateName(j);
            int index = 0;

            for (int i = 0; i < viewTabsNo && index < j.getRecordsReturned(); i++) {
                try {
                    String t;

                    if (j.getFormat().equalsIgnoreCase("0")) {
                        JsonBodyOutput temp = d.casePage(i, true);
                        temp.setState_val(j.getState_val());
                        temp.setDistrict_val(j.getDistrict_val());
                        temp.setBench_val(j.getBench_val());
                        t = new Gson().toJson(temp);
                    } else {
                        JsonBodyOutputAlt temp = d.casePageAlt(i, true);
                        temp.setInputs(new JSONObject(input).toMap());
                        t = new Gson().toJson(temp);
                    }

                    ret.add(new JSONObject(t).toMap());
                    index++;
                } catch (Exception e) {
                    if (!e.getMessage().toLowerCase().contains("not a valid index")) {
                        System.out.println("Index [" + i + "] has failed: " + e);
                        ret.add(new JSONObject(new ErrorHandling(i, new JSONObject(input).toMap(), e)).toMap());
                    }
                }
            }

            d.driverQuit();
        } catch (Exception e) {
            e.printStackTrace();
            ret.add(new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap());
        }

        return ret;
    }

    @PostMapping("/causeListUpdate")
    @ResponseBody
    public ArrayList<Map<String, Object>> causeList(@Valid @RequestBody JsonBodyInputCauseList j) {
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        String input = new Gson().toJson(j);

        try {
            DistrictCourt d = new DistrictCourt("chrome");
            int viewTabsNo = d.causeList(j);
            int index = 0;

            for (int i = 0; i < viewTabsNo && index < j.getRecordsReturned(); i++) {
                try {
                    String t;

                    if (j.getFormat().equalsIgnoreCase("0")) {
                        JsonBodyOutput temp = d.casePageCauseList(i, true);
                        temp.setState_val(j.getState_val());
                        temp.setDistrict_val(j.getDistrict_val());
                        temp.setBench_val(j.getBench_val());
                        t = new Gson().toJson(temp);
                    } else {
                        JsonBodyOutputAlt temp = d.casePageCauseListAlt(i, true);
                        temp.setInputs(new JSONObject(input).toMap());
                        t = new Gson().toJson(temp);
                    }

                    ret.add(new JSONObject(t).toMap());
                    index++;
                } catch (Exception e) {
                    if (!e.getMessage().toLowerCase().contains("not a valid index")) {
                        System.out.println("Index [" + i + "] has failed: " + e);
                        ret.add(new JSONObject(new ErrorHandling(i, new JSONObject(input).toMap(), e)).toMap());
                    }
                }
            }

            d.driverQuit();
        } catch (Exception e) {
            e.printStackTrace();
            ret.add(new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap());
        }

        return ret;
    }

    @PostMapping("/cnrNumberUpdate")
    @ResponseBody
    public Map<String, Object> cnrNumber(@Valid @RequestBody JsonBodyInputCNRNumber j) {
        Map<String, Object> ret;
        String input = new Gson().toJson(j);

        try {
            eCourt ec = new eCourt("chrome");

            ec.search(j);
            JsonBodyOutput temp = ec.casePage(true);

            String t = new Gson().toJson(temp);
            ret = new JSONObject(t).toMap();
        } catch (Exception e) {
            e.printStackTrace();
            ret = new JSONObject(new ErrorHandling(0, new JSONObject(input).toMap(), e)).toMap();
        }

        return ret;
    }
}