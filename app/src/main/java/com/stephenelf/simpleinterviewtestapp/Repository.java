package com.stephenelf.simpleinterviewtestapp;

import android.content.Context;
import android.util.Log;

import com.stephenelf.simpleinterviewtestapp.database.Character;
import com.stephenelf.simpleinterviewtestapp.database.MyDatabase;
import com.stephenelf.simpleinterviewtestapp.net.response.CharacterResponse;
import com.stephenelf.simpleinterviewtestapp.repositories.ContactsRepository;
import com.stephenelf.simpleinterviewtestapp.repositories.LocalRepository;
import com.stephenelf.simpleinterviewtestapp.repositories.RemoteRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.rxcontacts.Contact;

public class Repository {

    public static final String TAG="Repository";

    private Context context;


    @Inject
    LocalRepository localRepository;

    @Inject
    RemoteRepository remoteRepository;

    @Inject
    ContactsRepository contactsRepository;

    public Repository(Context context) {
        this.context = context;

    }

    public void getPeople(){

        Observable.zip(
                localRepository.getAllCharacters().subscribe(),
                remoteRepository.getCharacters().subscribe(),
                contactsRepository.getAllContacts(context).subscribe(),
                (localCharacters,remoteCharacters,contacts)->mergePeople(localCharacters,remoteCharacters,contacts)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private List<People> mergePeople(List<Character> localCharacters, List<Character> remoteCharacters, List<Contact> contacts) {
            localRepository.saveCharacters(remoteCharacters);

            return Collections.emptyList();
    }



}
