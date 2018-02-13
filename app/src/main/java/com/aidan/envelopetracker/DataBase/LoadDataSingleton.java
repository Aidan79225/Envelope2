package com.aidan.envelopetracker.DataBase;


import com.aidan.envelopetracker.Model.Bill;
import com.aidan.envelopetracker.Model.Envelope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aidan on 2016/10/3.
 */

public class LoadDataSingleton {
    private static LoadDataSingleton loadDataSingleton;

    public static LoadDataSingleton getInstance() {
        if (loadDataSingleton == null)
            loadDataSingleton = new LoadDataSingleton();
        return loadDataSingleton;
    }

    public void saveAccount(Bill bill) {
        if (!BillDAO.getInstance().update(bill))
            BillDAO.getInstance().insert(bill);
    }

    public void saveEnvelope(Envelope envelope) {
        if (!EnvelopeDAO.getInstance().update(envelope))
            EnvelopeDAO.getInstance().insert(envelope);
    }

    public void deleteEnvelope(Envelope envelope) {
        EnvelopeDAO.getInstance().delete(envelope.getId());
    }

    public void deleteAccount(Bill bill) {
        BillDAO.getInstance().delete(bill.getId());
    }

}
