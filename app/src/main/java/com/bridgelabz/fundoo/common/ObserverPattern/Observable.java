package com.bridgelabz.fundoo.common.ObserverPattern;

public interface Observable<T>
{
    void registerObserver(Observer observer);
    void unRegisterObserver(Observer observer);
    void notify(Observer observer);
    void notifyAllObserver();
}
