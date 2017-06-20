extern "C" {
#include <libavformat\avformat.h>
#include <libavcodec\avcodec.h>
#include <libavfilter\avfilter.h>
#include <libavfilter\avfiltergraph.h>
#include <libavfilter\buffersink.h>
#include <libavfilter\buffersrc.h>
#include <libswresample\swresample.h>
#include <libswscale\swscale.h>
#include <libavutil\common.h>
#include <libavutil\avassert.h>
#include <libavutil\pixdesc.h>
#include <libavutil\channel_layout.h>
#include <libavutil\opt.h>
#include <libavutil\mathematics.h>
#include <libavutil\timestamp.h>
}
#pragma comment(lib, "avformat.lib")
#pragma comment(lib, "avcodec.lib")
#pragma comment(lib, "avfilter.lib")
#pragma comment(lib, "swresample.lib")
#pragma comment(lib, "swscale.lib")
#pragma comment(lib, "avutil.lib")

typedef struct FILE_CONEXT {
	AVFormatContext * fmtCtx;
	int videoIndex;
	int audioIndex;
} FileContext;

typedef struct FILTER_CONTEXT {
	AVFilterGraph * filterGraph;
	AVFilterContext * srcCtx;
	AVFilterContext * sinkCtx;
} FilterContext;

class Study {
private:
	FileContext inCtx;
	FileContext outCtx;
	FilterContext videoFilterCtx;
	FilterContext audioFilterCtx;

	const int DST_WIDTH = 1280;
	const int DST_HEIGHT = 720;
	const int DST_VBIT_RATE = 641376;
	const int DST_ABIT_RATE = 128000;
	const int64_t DST_CH_LAYOUT = AV_CH_LAYOUT_MONO;
	const int DST_SAMPLE_RATE = 32000;

public:
	void init();
	int openDecoder(AVCodecContext * codecCtx);
	int openInput(const char * filename);
	int initVideoFilter();
	int initAudioFilter();
	int createOutput(const char * filename);
	void release();
	int decodePacket(AVCodecContext * codecCtx, AVPacket * pkt, AVFrame ** frame, int * gotFrame);
	int encodeWriteFrame(AVFrame * frame, int outStreamIndex, int * gotPacket);
	int filterEncodeWriteFrame(AVFrame * frame, int outStreamIndex);
	int demuxing(int argc, char * argv[]);
	int remuxing(int argc, char * argv[]);
	int decoding(int argc, char * argv[]);
	int filtering(int argc, char * argv[]);
	int encoding(int argc, char * argv[]);
};