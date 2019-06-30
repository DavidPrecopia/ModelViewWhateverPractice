package com.example.modelviewwhateverpractice.util;

import io.reactivex.Scheduler;

public interface ISchedulerProviderContract {
    Scheduler io();
    Scheduler ui();
}
