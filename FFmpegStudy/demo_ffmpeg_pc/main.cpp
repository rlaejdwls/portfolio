#include <conio.h>
#include "Multiplexer.h"
#include "Demultiplexer.h"
#include "Study.h"
#include "Test.h"

int main(int argc, char *argv[]) { 
	int result = 0;

	//Test test;
	//test.initArray();
	//result = test.print(argc, argv);

	//Multiplexer muxer;
	//muxer.muxing(argc, argv);

	//Demultiplexer demuxer;
	//demuxer.demuxing(argc, argv);

	Study study;
	study.encoding(argc, argv);
	
	_getch();
	return result;
}