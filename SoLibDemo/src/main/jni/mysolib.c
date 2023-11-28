//
// Created by hb on 2023/11/28.
//

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 函数 getMessageFromLibrary 返回一个字符串，将其作为 JNI 的 jstring 被返回给 Java 层。

JNIEXPORT jstring JNICALL
Java_com_hb_solibdemo_MainActivity_getMessageFromLibrary(JNIEnv *env, jobject obj, jobject o) {
    const char *message = "Hello World from the so library! \n";
    // return (*env)->NewStringUTF(env, message);

    // 获取 obj 的类
    jclass objClass = (*env)->GetObjectClass(env, o);

    // 获取 toString 方法的 ID
    jmethodID toStringMethodID = (*env)->GetMethodID(env, objClass, "toString",
                                                     "()Ljava/lang/String;");
    if (toStringMethodID == NULL) {
        return NULL;
    }

    // 调用 toString 方法获取 obj 的字符串表示形式
    jstring objString = (jstring) (*env)->CallObjectMethod(env, o, toStringMethodID);
    const char *objStr = (*env)->GetStringUTFChars(env, objString, NULL);

    // 计算结果字符串的长度
    size_t messageLen = strlen(message);
    size_t objStrLen = strlen(objStr);
    size_t resultLen = messageLen + objStrLen + 1;

    // 分配足够的内存来存储结果字符串
    char *result = (char *) malloc(resultLen);
    if (result == NULL) {
        return NULL;
    }

    // 将 message 和 obj 的字符串表示形式连接起来
    strcpy(result, message);
    strcat(result, objStr);

    // 释放资源
    (*env)->ReleaseStringUTFChars(env, objString, objStr);
    (*env)->DeleteLocalRef(env, objString);

    // 创建一个新的 Java 字符串对象，并返回
    jstring resultString = (*env)->NewStringUTF(env, result);
    free(result);
    return resultString;

}
