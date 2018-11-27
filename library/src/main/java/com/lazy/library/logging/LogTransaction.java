package com.lazy.library.logging;

import android.support.annotation.NonNull;

/**
 * Created by lazy on 2017/4/12.
 */

public abstract class LogTransaction {

    public abstract LogTransaction msg(@NonNull final Object msg);

    public abstract LogTransaction msgs(@NonNull final Object... msg);

    public abstract LogTransaction tag(@NonNull final String tag);

    public abstract LogTransaction tags(@NonNull final String... tags);

    public abstract LogTransaction file();

    public abstract LogTransaction file(@NonNull final String fileName);

    public abstract LogTransaction ln();

    public abstract LogTransaction format(@NonNull final String format, Object... args);

    public abstract LogTransaction fmtJSON(@NonNull final String json);

    public abstract LogTransaction out();
}
