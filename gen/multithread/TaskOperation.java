package com.dawnfall.engine.gen.multithread;


import java.util.concurrent.RecursiveAction;

public class TaskOperation<T extends RecursiveAction> implements Task<T>{
    private final T task;
    public TaskOperation(T task){
        this.task = task;
    }
    @Override
    public void task() {
        executeFork();
    }
    @Override
    public void executeFork() {
      task.fork();
    }

    @Override
    public void executeInvoke() {
        task.invoke();
    }

    public T getObject() {
        return task;
    }
}
