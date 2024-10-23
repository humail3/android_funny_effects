package com.voicechanger.app.effects.getApiData.appScheduler;

import io.reactivex.Scheduler;

public interface SchedularProvider {
    Scheduler getScheduler2();

    Scheduler getIo();

    Scheduler getUi();
}
