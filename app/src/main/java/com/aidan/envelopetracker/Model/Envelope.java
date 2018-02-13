package com.aidan.envelopetracker.Model;

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
    private List<Account> accountList = new ArrayList<Account>();

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
        for (Account account : accountList) {
            account.setEnvelopeName(name);
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
        cost += account.getCost();

    }

    public void addAccountFromDB(Account account) {
        accountList.add(account);
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
        for (Account account : accountList) {
            cost += account.getCost();
        }
    }

    public void tobeNewEnvelope() {
        id = UUID.randomUUID().toString().substring(0, 10);
        accountList = new ArrayList<>();
        cost = 0;
//        LoadDataSingleton.getInstance().saveEnvelope(this);
    }



}
