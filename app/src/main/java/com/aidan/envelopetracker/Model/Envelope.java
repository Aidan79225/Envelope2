package com.aidan.envelopetracker.Model;

import com.aidan.envelopetracker.DataBase.LoadDataSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Aidan on 2016/10/2.
 */

public class Envelope {
    private String id = "";
    private String name = "";
    private int max = 0;
    private int cost = 0;
    private List<Bill> billList = new ArrayList<Bill>();

    public Envelope() {
        id = UUID.randomUUID().toString().substring(0, 10);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        for (Bill bill : billList) {
            bill.setEnvelopeName(name);
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public void addAccount(Bill bill) {
        billList.add(bill);
        cost += bill.getCost();

    }

    public void addBillFromDB(Bill bill) {
        billList.add(bill);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Envelope){
            Envelope envelope = (Envelope)o;
            return envelope.getId().equals(this.id);
        }

        return false;
    }

    public void refresh() {
        cost = 0;
        for (Bill bill : billList) {
            cost += bill.getCost();
        }
    }

    public void tobeNewEnvelope() {
        id = UUID.randomUUID().toString().substring(0, 10);
        billList = new ArrayList<>();
        cost = 0;
        LoadDataSingleton.getInstance().saveEnvelope(this);
    }



}
