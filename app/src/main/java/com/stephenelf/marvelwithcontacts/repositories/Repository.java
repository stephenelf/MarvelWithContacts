/*
 * Copyright 2019 stephenelf@gmail.com(EB). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stephenelf.marvelwithcontacts.repositories;

import android.content.Context;
import android.net.Uri;

import com.stephenelf.marvelwithcontacts.database.Character;
import com.stephenelf.marvelwithcontacts.util.People;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.rxcontacts.Contact;

public class Repository {

    public static final String TAG = "Repository";

    private Context context;

    LocalRepository localRepository;

    RemoteRepository remoteRepository;

    ContactsRepository contactsRepository;

    @Inject
    public Repository(Context context, LocalRepository localRepository, RemoteRepository remoteRepository, ContactsRepository contactsRepository) {
        this.context = context;
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.contactsRepository = contactsRepository;
    }

    public Single<List<People>> getPeople() {
        return Single.zip(
                localRepository.getAllCharacters()
                        .switchIfEmpty(getFromRemote())
                        .subscribeOn(Schedulers.io()).toSingle(),
                contactsRepository.getAllContacts(context).subscribeOn(Schedulers.io()),
                (localCharacters, contacts) -> mergePeople(localCharacters, contacts)
        );

    }

    //TODO: Fix this
    /*
    private MaybeSource<List<Character>> getFromRemote() {

        return remoteRepository.getCharacters().subscribeOn(Schedulers.io())
                .flatMapMaybe( characterResponse ->
                        Maybe.just(characterResponse.getData().getResults()));

    }
*/
    private MaybeSource<List<Character>> getFromRemote() {
        List<Character> characters = remoteRepository.getCharacters()
                .subscribeOn(Schedulers.io()).blockingGet().getData().getResults();
        localRepository.saveCharacters(characters);
        return Maybe.just(characters);
    }


    private List<People> mergePeople(List<Character> localCharacters, List<Contact> contacts) {
        localRepository.saveCharacters(localCharacters);
        List<People> people = new ArrayList<>();
        for (Character character : localCharacters) {
            people.add(new People(character.name, Uri.parse(character.thumbnail.toString())));
        }

        for (Contact contact : contacts) {
            people.add(new People(contact.getDisplayName(),
                    contact.getThumbnail(),
                    contact.getPhoneNumbers().size() > 0 ? contact.getPhoneNumbers().iterator().next() : null));
        }

        Collections.sort(people, (o1, o2) -> o1.name.compareTo(o2.name));

        return people;
    }


}
