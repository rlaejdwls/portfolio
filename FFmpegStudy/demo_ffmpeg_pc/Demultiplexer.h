extern "C" {
#include <libavformat\avformat.h>
#include <libavutil\imgutils.h>
#include <libavutil\samplefmt.h>
#include <libavutil\timestamp.h>
}
#pragma comment(lib, "avformat.lib")
#pragma comment(lib, "avutil.lib")

class Demultiplexer {
public:
	int demuxing(int argc, char *argv[]);
};