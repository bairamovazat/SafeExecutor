//#include "../../../../../../target/headers/ru_ivmiit_executor_SafeExecutor.h"
//#include <ru_ivmiit_executor_SafeExecutor.h>
#include <jni.h>
#include <string.h>
#include "runguard.h"

char *getSomeString(JNIEnv *env, jstring jstr) {
    char * nativeString;
    {
        const char *_nativeString = (*env)->GetStringUTFChars(env, jstr, 0);
        nativeString = strdup(_nativeString);
        (*env)->ReleaseStringUTFChars(env, jstr, _nativeString);

    }
    return nativeString;
}

/*
 * Class:     ru_ivmiit_executor_SafeExecutor
 * Method:    main
 * Signature: (I[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL
Java_ru_ivmiit_executor_SafeExecutor_main(JNIEnv *env, jobject jobj, jint jint, jobjectArray jobjArray) {
    char *argv[jint];
    int length = (*env)->GetArrayLength(env, jobjArray);

    for (int i = 0; i < jint; i++) {
        jobject value = (*env)->GetObjectArrayElement(env, jobjArray, i);
        argv[i] = getSomeString(env, value);
    }

    return main(jint, argv);
}
