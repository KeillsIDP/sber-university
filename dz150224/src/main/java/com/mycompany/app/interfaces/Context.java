package com.mycompany.app.interfaces;

public interface Context {
    /**
     *
     * @return кол-во всех завершенных задач на данный момент
     */
    int getCompletedTaskCount();

    /**
     *
     * @return задачи, которые не смогли выполниться на данный момент
     */
    int getFailedTaskCount();
    /**
     *
     * @return задачи, которые были прерваны на данный момент
     */
    int getInterruptedTaskCount();
    /**
     * Завершает выполнение всех задач
     */
    void interrupt();

    /**
     *
     * @return возвращает boolean значение - завершены ли все задачи
     */
    boolean isFinished();
}
