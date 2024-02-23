package com.mycompany.app.interfaces;

import java.util.List;

public interface ExecutionManager {
    /**
     *
     * @param callback выполняется после завершения всех tasks
     * @param tasks задачи, которые будут выполнены в отдельном ThreadPool
     * @return возвращает {@linkplain com.mycompany.app.interfaces.Context} сразу после добавления всех задач в пул
     */
    Context execute(Runnable callback, List<Runnable> tasks);
}

