package com.stephenelf.simpleinterviewtestapp;

import android.content.Context;

import com.stephenelf.simpleinterviewtestapp.database.Character;
import com.stephenelf.simpleinterviewtestapp.net.response.CharacterResponse;
import com.stephenelf.simpleinterviewtestapp.repositories.ContactsRepository;
import com.stephenelf.simpleinterviewtestapp.repositories.LocalRepository;
import com.stephenelf.simpleinterviewtestapp.repositories.RemoteRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.rxcontacts.Contact;

public class Repository {

    public static final String TAG = "Repository";

    private Context context;

    LocalRepository localRepository;

    RemoteRepository remoteRepository;

    ContactsRepository contactsRepository;

    public Repository(Context context,LocalRepository localRepository,RemoteRepository remoteRepository,ContactsRepository contactsRepository) {
        this.context = context;
        this.localRepository=localRepository;
        this.remoteRepository=remoteRepository;
        this.contactsRepository=contactsRepository;
    }

    public Single<List<People>> getPeople() {

        return Single.zip(
                localRepository.getAllCharacters().defaultIfEmpty(new ArrayList<>()).subscribeOn(Schedulers.io()).toSingle(),
                remoteRepository.getCharacters().subscribeOn(Schedulers.io()),
                contactsRepository.getAllContacts(context).subscribeOn(Schedulers.io()),
                (localCharacters, remoteCharacters, contacts) -> mergePeople(localCharacters, remoteCharacters, contacts)
        )
                .observeOn(AndroidSchedulers.mainThread());

    }


    private List<People> mergePeople(List<Character> localCharacters, CharacterResponse remoteCharacters, List<Contact> contacts) {
        localRepository.saveCharacters(remoteCharacters.getData().getResults());

        List<People> people = new ArrayList<>();
        for(Character character:localCharacters){
            people.add(new People(character.name,character.thumbnail));
        }
        for(Character character:remoteCharacters.getData().getResults()){
            people.add(new People(character.name,character.thumbnail));
        }
        for(Contact contact:contacts){
            people.add(new People(contact.getDisplayName(),contact.getThumbnail().toString()));
        }

        return people;
    }


}
