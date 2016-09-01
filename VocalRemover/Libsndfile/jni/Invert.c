#include "Invert.h"
#include "./libsndfile/sndfile.h"

#define BLOCK_SIZE 1048576 / sizeof(float)

int effectInvert(char *input_path, char *output_path, float volume) {
	SF_INFO info;
	SNDFILE *input, *output;
	float *samples;
	float *invert;
	float *result;
	int len;

	input = sf_open(input_path, SFM_READ, &info);

	//원본 파일 열기 실패
	if (input == NULL) {
		return 1;
	}

	//format 검사
	if (info.format != (SF_FORMAT_WAV | SF_FORMAT_PCM_16) &&
		info.format != (SF_FORMAT_WAV | SF_FORMAT_PCM_24)) {
		return 3;
	}

	//스테레오로 제한
	if (info.channels != 2) {
		return 4;
	}

	int block = BLOCK_SIZE;

	len = info.frames;
	samples = (float *)malloc(sizeof(float) * info.channels * block);
	invert = (float *)malloc(sizeof(float) * info.channels * len);

	int count = 0, i = 0;
	do {
		block = BLOCK_SIZE;
		block = sf_readf_float(input, (float *)samples, block);

		//채널 구분
		for (i = 0; i < block * info.channels; i++) {
			if (i % info.channels == 0) {
				//음성 반전
				invert[i + count] = -samples[i];
			}
			else {
				invert[i + count] = samples[i];
			}
		}

		count = i + count;
	} while (block > 0);

	sf_close(input);
	result = (float *)malloc(sizeof(float) * info.channels * len / 2);

	for (i = 0; i < len; i++) {
		//모노로 변환하는 부분
		result[i] = 0;
		int c;
		for (c = 0; c < info.channels; c++) {
			result[i] += invert[info.channels * i + c];
		}
	}

	//노멀라이즈 부분
	float max = 0;
	for (i = 0; i < len; i++) {
		if (max < (result[i] < 0 ? -result[i] : result[i])) { max = (result[i] < 0 ? -result[i] : result[i]); }
	}

	max = volume - max;
	if (max <= 0.5) max = 0.5;
	if (max >= 1.0) max = 1.0;

	for (i = 0; i < len; i++) {
		result[i] = result[i] * max;
	}

	//모노로 출력
	info.channels = 1;
	output = sf_open(output_path, SFM_WRITE, &info);

	//변환 파일 출력 실패
	if (output == NULL) {
		return 2;
	}

	sf_writef_float(output, result, len);
	sf_close(output);

	//메모리 해제
	free(samples);
	free(invert);
	free(result);

	//정상반환
	return 0;
}

char *jstringToCharPointer(const char target[]) {
	int i = 0;
	while (target[i] != '\0') {
		i++;
	}

	char *result = (char *)malloc(i + 1);

	int max = i;
	for (i = 0; i <= max; i++) {
		result[i] = target[i];
	}
	return result;
}
