extern "C" {
#include <libavformat\avformat.h>
#include <libavcodec\avcodec.h>
#include <libswresample\swresample.h>
#include <libswscale\swscale.h>
#include <libavutil\avassert.h>
#include <libavutil\channel_layout.h>
#include <libavutil\opt.h>
#include <libavutil\mathematics.h>
#include <libavutil\timestamp.h>
}
#pragma comment(lib, "avformat.lib")
#pragma comment(lib, "avcodec.lib")
#pragma comment(lib, "swresample.lib")
#pragma comment(lib, "swscale.lib")
#pragma comment(lib, "avutil.lib")

class Multiplexer {
public:
	int muxing(int argc, char *argv[]);
};