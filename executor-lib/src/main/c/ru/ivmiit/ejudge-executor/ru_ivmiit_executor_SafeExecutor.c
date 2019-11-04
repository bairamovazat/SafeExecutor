//#include "../../../../../../target/headers/ru_ivmiit_executor_SafeExecutor.h"
//#include <ru_ivmiit_executor_SafeExecutor.h>
#include <jni.h>
#include <stdio.h>

JNIEXPORT void JNICALL Java_ru_ivmiit_executor_SafeExecutor_main
  (JNIEnv * env, jobject jobj, jint jint, jobjectArray jobjArray){
    printf("Work!");
  }