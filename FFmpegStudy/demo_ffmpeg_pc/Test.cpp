#include "Test.h"

int Test::print(int argc, char *argv[]) { /*
-    if ((err = av_find_stream_info(fmt_ctx)) < 0) {
+    if ((err = avformat_find_stream_info(fmt_ctx, NULL)) < 0) {


     for (i = 1; i < pmp->num_streams; i++) {
-        AVStream *ast = av_new_stream(s, i);
+        AVStream *ast = avformat_new_stream(s, NULL);
         if (!ast)
             return AVERROR(ENOMEM);
+        ast->id = i;
         ast->codec->codec_type = AVMEDIA_TYPE_AUDIO;
         ast->codec->codec_id = audio_codec_id;
         ast->codec->channels = channels;
	*/

	if (argc < 2) {
		return -1;
	}

	AVRational rational = { 1, 25 };

	av_log(NULL, AV_LOG_INFO, "Hello FFmpeg, %s, %d\n", argv[1], 1);
	av_register_all();
	avcodec_register_all();

	char * fileName = "D:\\Temp\\test.mp4";
	AVFormatContext * formatContext = NULL;
	int err = avformat_open_input(&formatContext, fileName, NULL, NULL);

	if (err < 0) {
		return -1;
	}

	int videoStreamIndex = -1;
	int audioStreamIndex = -1;

	err = avformat_find_stream_info(formatContext, NULL);
	if (err < 0) {
		return -1;
	}

	av_log(NULL, AV_LOG_INFO, "Complete\n");
	return 0;
}

void Test::initArray() {
	int param1[10] = { -1, };
	int param2[10] = { -1 };

	for (int i = 0; i < 10; i++) {
		av_log(NULL, AV_LOG_INFO, "%d ", param1[i]);
	}
	av_log(NULL, AV_LOG_INFO, "\n"); 
	for (int i = 0; i < 10; i++) {
		av_log(NULL, AV_LOG_INFO, "%d ", param2[i]);
	}
	av_log(NULL, AV_LOG_INFO, "\n");
}