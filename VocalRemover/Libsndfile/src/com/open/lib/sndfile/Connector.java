package com.open.lib.sndfile;

//javah -classpath ./classes/ -jni com.example.sndfiletest.Connector
public class Connector {
    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native int callLibsndjniInvert(String input_path, String output_path, float volume);

    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("Connector");
    }
}
