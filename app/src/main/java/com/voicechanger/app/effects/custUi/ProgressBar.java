package com.voicechanger.app.effects.custUi;

import com.voicechanger.app.effects.getApiData.appScheduler.SchedularProvider;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.BehaviorSubject;

public final class ProgressBar extends Observable<Boolean> {
    private Observable<Object> objectObservable;
    private final BehaviorSubject<Integer> integerBehaviorSubject;

    public ProgressBar(SchedularProvider provider) {
        BehaviorSubject<Integer> aDefault = BehaviorSubject.createDefault(0);
        this.integerBehaviorSubject = aDefault;
        Observable<Object> observable = aDefault.subscribeOn(provider.getIo()).observeOn(provider.getUi()).onErrorReturnItem(0).map(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) throws Exception {
                return ProgressBar.getProgresses(integer);
            }
        }).share();
        this.objectObservable = observable;
    }

    public static Boolean getProgresses(Integer num) {
        return Boolean.valueOf(num.intValue() > 0);
    }

    public void subscribeActual(Observer<? super Boolean> observer) {
        this.objectObservable.subscribe((Consumer<? super Object>) observer);
    }

    public  void reset() {
        this.integerBehaviorSubject.onNext(0);
    }
}
