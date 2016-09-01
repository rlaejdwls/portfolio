#include "Connector.h"
#include "Invert.h"

#include <string.h>
#include <jni.h>
#include <android/log.h>

#define  LOG_TAG   "jni_external_storage"
#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define  LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

jint Java_com_open_lib_sndfile_Connector_callLibsndjniInvert
(JNIEnv *jnienv, jobject jobj, jstring input_path, jstring output_path, jfloat volume) {
	char *input_char_path = jstringToCharPointer((*jnienv)->GetStringUTFChars(jnienv, input_path, 0));
	char *output_char_path = jstringToCharPointer((*jnienv)->GetStringUTFChars(jnienv, output_path, 0));

	int result = effectInvert(input_char_path, output_char_path, volume);

	free(input_char_path);
	free(output_char_path);

	return result;
}
