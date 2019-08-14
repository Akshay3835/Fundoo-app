package com.bridgelabz.fundoo.common.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public class ObservableImpl<T> implements Observable
{
    private T data;
    private List<Observer> observerList = new ArrayList<>();


    public ObservableImpl(T data)
    {
        this.data = data;
    }

    @Override
    public void registerObserver(Observer observer)
    {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    @Override
    public void unRegisterObserver(Observer observer)
    {
        if (observerList.contains(observer)) {
            observerList.remove(observer);
        }
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
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        notifyAllObserver();
    }
}
