package com.bridgelabz.fundoo.common.ObserverPattern;

import com.bridgelabz.fundoo.add_note_page.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class ObservableNotes implements Observable<List<Note>>
{

    private List<Note> listOfNotes;
    private List<Observer> observerList = new ArrayList<>();

    public ObservableNotes(List<Note> noteList)
    {
        this.listOfNotes = noteList;
    }

    @Override
    public void registerObserver(Observer observer)
    {
        this.observerList.add(observer);
    }

    @Override
    public void unRegisterObserver(Observer observer)
    {
        this.observerList.remove(observer);

    }

    @Override
    public void notify(Observer observer)
    {
        observer.update(this);
    }

    @Override
    public void notifyAllObserver()
    {
        for (Observer observer : observerList)
        {
            observer.update(this);
        }

    }

}
