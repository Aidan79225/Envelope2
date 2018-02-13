package com.aidan.envelopetracker.Model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by s352431 on 2016/10/28.
 */
public class MonthHistory {

    String name;
    String id;
    int saveMoney = 0;
    long index;


    List<String> envelopeIdList = new ArrayList<>();
    List<Envelope> envelopeList = new ArrayList<>();
    List<Account> accountList = new ArrayList<>();

    public MonthHistory() {
        Calendar now = Calendar.getInstance();
        id = UUID.randomUUID().toString().substring(0, 10);
        name = "歷史資料" + String.valueOf(now.get(Calendar.YEAR)) + String.valueOf(now.get(Calendar.MONTH) + 1);
    }


    public List<Envelope> getEnvelopeList() {
        return envelopeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvelopId() {
        JSONArray array = new JSONArray();
        try {
            for (Envelope envelope : envelopeList) {
                array.put(envelope.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getIndex() {
        return index;
    }

    public void setEnvelopId(String ids) {
        try {
            JSONArray array = new JSONArray(ids);
            for (int i = 0; i < array.length(); i++) {
                envelopeIdList.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catchEnvelopeList();
    }

    private void catchEnvelopeList() {
        for (String envelopeId : envelopeIdList) {
            try {
//                envelopeList.addAll(MonthHistoryDAO.getInstance().getEnvelops(envelopeId));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        refreshSaveMoney();
    }

    private void refreshSaveMoney() {
        for (Envelope envelope : envelopeList) {
            saveMoney += envelope.getMax() - envelope.getCost();
        }
    }

    public int getSaveMoney() {
        return saveMoney;
    }

    public void setEnvelops(List<Envelope> e) {
        envelopeList.addAll(e);
    }


}
