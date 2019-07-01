package com.stephenelf.simpleinterviewtestapp.repositories;

import android.content.Context;

import java.util.List;

import io.reactivex.Single;
import ir.mirrajabi.rxcontacts.Contact;
import ir.mirrajabi.rxcontacts.RxContacts;

public class ContactsRepository {

    public Single<List<Contact>> getAllContacts(Context context){
          return RxContacts.fetch(context)
                .filter(m->m.getInVisibleGroup() == 1)
                .toSortedList(Contact::compareTo);
    }
}
