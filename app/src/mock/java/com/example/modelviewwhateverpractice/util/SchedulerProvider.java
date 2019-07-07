package com.example.modelviewwhateverpractice.util;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Trampoline: a Scheduler that queues work on the current thread to be
 * executed after the current work completes.
 * Another way to put it: emits result in a sequentially predictable order.
 *
 * Because this is a unit test that is running on the JVM,
 * all operations run on the same thread the tests are running on.
 * Otherwise, an error in thrown by the Observable.
 */
public class SchedulerProvider implements ISchedulerProviderContract {
    public SchedulerProvider() {
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
