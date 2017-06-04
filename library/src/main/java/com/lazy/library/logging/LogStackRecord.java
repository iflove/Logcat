package com.lazy.library.logging;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lazy on 2017/4/12.
 */

final class LogStackRecord extends LogTransaction {
    static final int OP_NULL = 0;

    static final int OP_TAG = 1;
    static final int OP_TAGS = 2;
    static final int OP_MSG = 3;
    static final int OP_MSGS = 4;
    static final int OP_FILE = 5;
    static final int OP_LN = 6;
    static final int OP_FORMAT = 7;

    private final LogLevel logLevel;

    LogStackRecord(LogLevel logLevel) {
        this.logLevel = logLevel;
    }


    @Override
    public LogTransaction msg(@NonNull Object msg) {
        doOp(OP_MSG, msg);
        return this;
    }

    @Override
    public LogTransaction msgs(@NonNull Object... msg) {
        doOp(OP_MSGS, msg);
        return this;
    }

    @Override
    public LogTransaction tag(@NonNull String tag) {
        doOp(OP_TAG, tag);
        return this;
    }

    @Override
    public LogTransaction tags(@NonNull String... tags) {
        doOp(OP_TAGS, tags);
        return this;
    }

    @Override
    public LogTransaction file() {
        doOp(OP_FILE, "");
        return this;
    }

    @Override
    public LogTransaction file(@NonNull String fileName) {
        doOp(OP_FILE, fileName);
        return this;
    }

    @Override
    public LogTransaction ln() {
        doOp(OP_LN, "\n");
        return this;
    }

    @Override
    public LogTransaction format(@NonNull String format, Object... args) {
        doOp(OP_FORMAT, new Pair<String, List<Object>>(format, Arrays.asList(args)));
        return this;
    }

    @Override
    public LogTransaction out() {
        List<Object> msgsList = new ArrayList<>();
        List<String> tagsList = new ArrayList<>();
        String filesName = null;

        while (mHead != null) {

            switch (mHead.cmd) {
                case OP_MSG:
                    msgsList.add(mHead.obj);
                    break;
                case OP_MSGS:
                    msgsList.addAll(Arrays.asList((Object[]) mHead.obj));
                    break;
                case OP_TAG:
                    tagsList.add((String) mHead.obj);
                    break;
                case OP_TAGS:
                    tagsList.addAll(Arrays.asList((String[]) mHead.obj));
                    break;
                case OP_FILE:
                    filesName = ((String) mHead.obj);
                    break;
                case OP_LN:
                    msgsList.add((String) mHead.obj);
                    break;
                case OP_FORMAT:
                    Pair<String, List<String>> pair = (Pair<String, List<String>>) mHead.obj;
                    String format = String.format(pair.first, pair.second.toArray());
                    msgsList.add(format);
                    break;
            }
            mHead = mHead.next;
            if (mHead != null) {
                mHead.prev = null;
            }
        }
        mTail = null;
        mNumOp = 0;

        boolean hasMsg = !msgsList.isEmpty();
        boolean hasTag = !tagsList.isEmpty();
        boolean hasFile = filesName != null;

        StringBuffer buffer = new StringBuffer();
        for (Object o : msgsList) {
            buffer.append(o.toString());
            buffer.append(" ");
        }
        String[] tags = new String[tagsList.size()];
        tagsList.toArray(tags);
        String msg = buffer.toString();


        if (hasFile) {
            Logcat.writeLog(logLevel.value, Logcat.INDEX, msg, filesName, tags);
        }
        Logcat.consoleLog(logLevel.value, Logcat.INDEX, msg, tags);

        return this;
    }

    static final class Op {
        Op next;
        Op prev;
        int cmd;
        Object obj;
    }


    Op mHead;
    Op mTail;
    int mNumOp;


    private void doOp(int opCmd, Object object) {
        if (object == null) {
            object = "null";
        }
        Op op = new Op();
        op.cmd = opCmd;
        op.obj = object;
        addOp(op);
    }

    void addOp(Op op) {
        if (mHead == null) {
            mHead = mTail = op;
        } else {
            op.prev = mTail;
            mTail.next = op;
            mTail = op;
        }

        mNumOp++;
    }

}
