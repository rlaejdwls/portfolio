extern "C" {
#include <libavformat\avformat.h>
#include <libavcodec\avcodec.h>
#include <libswresample\swresample.h>
#include <libswscale\swscale.h>
#include <libavutil\opt.h>
}
#pragma comment(lib, "avformat.lib")
#pragma comment(lib, "avcodec.lib")
#pragma comment(lib, "swresample.lib")
#pragma comment(lib, "swscale.lib")
#pragma comment(lib, "avutil.lib")

class Test {
public:
	int print(int argc, char *argv[]);
	void initArray();
};