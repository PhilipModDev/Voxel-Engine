package com.dawnfall.engine.gen.multithread;

import java.util.concurrent.RecursiveAction;

public interface Task<T extends RecursiveAction> {
    void task();
    void executeFork();
    void executeInvoke();
}
