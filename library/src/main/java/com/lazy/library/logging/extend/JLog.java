package com.lazy.library.logging.extend;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by rentianlong on 2018/11/26
 */
public final class JLog implements Handler.Callback {
    private static final String TAG = "JLog";
    private static final int WHAT_INIT = 1;
    private static final int WHAT_DESTROY = 2;
    private static final int WHAT_PRINT = 3;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private HandlerThread handlerThread;
    private Handler ioHandler;
    private String host;
    private int port;
    Socket socket;
    OutputStream out;

    public JLog(String host, int port) {
        this.host = host;
        this.port = port;
        handlerThread = new HandlerThread("JLogThread");
        handlerThread.start();
        ioHandler = new Handler(handlerThread.getLooper(), this);
        ioHandler.sendEmptyMessage(WHAT_INIT);
    }

    public void println(@NonNull String msg) {
        Message obtain = Message.obtain();
        obtain.obj = msg;
        obtain.what = WHAT_PRINT;
        ioHandler.sendMessage(obtain);
    }

    public void release() {
        ioHandler.sendEmptyMessage(WHAT_DESTROY);
    }

    private void destroy() {
        if (this.socket != null) {
            try {
                if (out != null) {
                    out.flush();
                }
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.socket = null;
                this.out = null;
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INIT: {
                contentRemote();
                break;
            }
            case WHAT_DESTROY: {
                destroy();
                break;
            }
            case WHAT_PRINT: {
                String msgText = (String) msg.obj;
                output(msgText);
                break;
            }
            default:
                break;
        }

        return false;
    }

    private void output(String msgText) {
        if (!isConnected()) {
            contentRemote();
        }
        if (out != null) {
            try {
                out.write(msgText.getBytes());
                out.write(LINE_SEPARATOR.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    private void contentRemote() {
        try {
            this.socket = new Socket(host, port);
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("init JLog error");
        }
    }
}
