package com.example.seleniumdemo.jsoncompilation;

import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyInputAdvocateName;
import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyInputCaseNumber;
import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyOutputExcel;
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
public class JsonController {
    @PostMapping("/caseNumber")
    @ResponseBody
    public ArrayList<Map<String, Object>> caseNumber(@Valid @RequestBody JsonBodyInputCaseNumber j) throws Exception {
        DistrictCourt d = new DistrictCourt("chrome", "CaseNumber");
        ArrayList<Map<String, Object>> ret = new ArrayList<>();

        try {
            d.caseByNumber(j);
            ArrayList<JsonBodyOutputExcel> temp = d.casePage(j.getRecordsReturned());

            for (JsonBodyOutputExcel b : temp) {
                String t = new Gson().toJson(b);
                ret.add(new JSONObject(t).toMap());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return ret;
    }

    @PostMapping("/advocateName")
    @ResponseBody
    public ArrayList<Map<String, Object>> advocateName(@Valid @RequestBody JsonBodyInputAdvocateName j) throws Exception {
        DistrictCourt d = new DistrictCourt("chrome", "AdvocateName");
        ArrayList<Map<String, Object>> ret = new ArrayList<>();

        try {
            d.caseByAdvocate(j);
            ArrayList<JsonBodyOutputExcel> temp = d.casePage(j.getRecordsReturned());

            for (JsonBodyOutputExcel b : temp) {
                String t = new Gson().toJson(b);
                ret.add(new JSONObject(t).toMap());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return ret;
    }

    @PostMapping("/json2")
    @ResponseBody
    public ArrayList<Map<String, Object>> jsonValidate(@Valid @RequestBody JsonBodyInputCaseNumber j) {
        System.out.println(j.getCaseCode());
        j.setCaseCode("temp");

        String temp = new Gson().toJson(j);
        JSONObject r = new JSONObject(temp);

        ArrayList<Map<String, Object>> rt = new ArrayList<>();
        rt.add(r.toMap());
        rt.add(r.toMap());

        return rt;
    }
}