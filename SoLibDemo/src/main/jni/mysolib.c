//
// Created by hb on 2023/11/28.
//

#include <jni.h>

// 函数 getMessageFromLibrary 返回一个字符串，将其作为 JNI 的 jstring 被返回给 Java 层。

JNIEXPORT jstring JNICALL
Java_com_hb_solibdemo_MainActivity_getMessageFromLibrary(JNIEnv *env, jobject obj) {
    const char* message = "Hello World from the so library!";
    return (*env)->NewStringUTF(env, message);
}
