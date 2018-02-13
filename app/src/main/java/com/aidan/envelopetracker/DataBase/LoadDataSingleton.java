package com.aidan.envelopetracker.DataBase;


import com.aidan.envelopetracker.Model.Account;
import com.aidan.envelopetracker.Model.Envelope;
import com.aidan.envelopetracker.Model.MonthHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aidan on 2016/10/3.
 */

public class LoadDataSingleton {
    private List<Envelope> envelopeList = new ArrayList<>();
    private List<Envelope> historyEnvelopeList = new ArrayList<>();
    private HashMap<String, Envelope> envelopeHashMap = new HashMap<>();
    private List<Account> accountList = new ArrayList<>();
    private List<Account> historyAccountList = new ArrayList<>();
    private List<MonthHistory> monthHistoryList = new ArrayList<>();
    private static LoadDataSingleton loadDataSingleton;

    public static LoadDataSingleton getInstance() {
        if (loadDataSingleton == null)
            loadDataSingleton = new LoadDataSingleton();
        return loadDataSingleton;
    }

    public List<Envelope> getEnvelopeList() {
        return envelopeList;
    }

    public List<Envelope> getHistoryEnvelopeList() {
        return historyEnvelopeList;
    }

    public List<Account> getHistoryAccountList() {
        return historyAccountList;
    }

    public List<MonthHistory> getMonthHistoryList() {
        return monthHistoryList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(0, account);
        saveAccount(account);
    }

    public void saveAccount(Account account) {
        if (!AccountDAO.getInstance().update(account))
            AccountDAO.getInstance().insert(account);
    }

    public void saveAccount(Account account, String tableName) {
        if (!AccountDAO.getInstance().update(account, tableName))
            AccountDAO.getInstance().insert(account, tableName);
    }

    public void saveEnvelope(Envelope envelope) {
        if (!EnvelopeDAO.getInstance().update(envelope))
            EnvelopeDAO.getInstance().insert(envelope);
    }

    public void saveEnvelope(Envelope envelope, String tableName) {
        if (!EnvelopeDAO.getInstance().update(envelope, tableName))
            EnvelopeDAO.getInstance().insert(envelope, tableName);
    }

    public void saveMonth(MonthHistory monthHistory) {
        if (!MonthHistoryDAO.getInstance().update(monthHistory))
            MonthHistoryDAO.getInstance().insert(monthHistory);

    }

    public void saveMonthAndEnvelope(MonthHistory monthHistory) {
        if (!MonthHistoryDAO.getInstance().update(monthHistory))
            MonthHistoryDAO.getInstance().insert(monthHistory);
        List<Envelope> envelopes = monthHistory.getEnvelopeList();
        for (Envelope envelope : envelopes) {
            saveEnvelope(envelope, MonthHistoryDAO.envelopeTableName);
        }
    }

    public void deleteEnvelope(Envelope envelope) {
        EnvelopeDAO.getInstance().delete(envelope.getIndex());
    }

    public void deleteAccount(Account account) {
        AccountDAO.getInstance().delete(account.getIndex());
    }

    public void saveToDB() {
        try {
            for (Envelope envelope : envelopeList) {
                saveEnvelope(envelope);
            }
            for (Account account : accountList) {
                saveAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Envelope getEnvelope(String id) {
        return envelopeHashMap.get(id);
    }

    public void loadFromDB() {
        try {
            envelopeList = EnvelopeDAO.getInstance().getAll();
            accountList = AccountDAO.getInstance().getAll();
            monthHistoryList = MonthHistoryDAO.getInstance().getAll();
            historyEnvelopeList = EnvelopeDAO.getInstance().getAll(MonthHistoryDAO.envelopeTableName);
            historyAccountList = AccountDAO.getInstance().getAll(MonthHistoryDAO.accountTableName);
            Collections.reverse(accountList);
            for (Envelope envelope : envelopeList) {
                envelopeHashMap.put(envelope.getId(), envelope);
                for (Account account : accountList) {
                    if (account.getEnvelopId().equals(envelope.getId()))
                        envelope.addAccountFromDB(account);
                }
                envelope.refresh();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
