#include "Study.h"

#include <stdio.h>

#define __STDC_FORMAT_MACROS
#include <inttypes.h>

#pragma warning(disable: 4996)

void Study::init() {
	inCtx.fmtCtx = NULL;
	inCtx.videoIndex = -1;
	inCtx.audioIndex = -1;

	outCtx.fmtCtx = NULL;
	outCtx.videoIndex = -1;
	outCtx.audioIndex = -1;

	videoFilterCtx.filterGraph = NULL;
	audioFilterCtx.filterGraph = NULL;
}

int Study::openDecoder(AVCodecContext * codecCtx) {
	AVCodec * decoder = avcodec_find_decoder(codecCtx->codec_id);
	if (decoder == NULL) {
		return -1;
	}
	if (avcodec_open2(codecCtx, decoder, NULL) < 0) {
		return -1;
	}
	return 0;
}

int Study::openInput(const char * filename) {
	unsigned int i;

	inCtx.fmtCtx = NULL;
	inCtx.videoIndex = -1;
	inCtx.audioIndex = -1;

	if (avformat_open_input(&inCtx.fmtCtx, filename, NULL, NULL) < 0) {
		av_log(NULL, AV_LOG_INFO, "%s not found file\n", filename);
		return -1;
	}

	if (avformat_find_stream_info(inCtx.fmtCtx, NULL) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to retrieve input stream information\n");
		return -1;
	}

	for (i = 0; i < inCtx.fmtCtx->nb_streams; i++) {
		AVCodecContext * avCodecCtx = inCtx.fmtCtx->streams[i]->codec;
		if (avCodecCtx->codec_type == AVMEDIA_TYPE_VIDEO && inCtx.videoIndex < 0) {
			av_log(NULL, AV_LOG_INFO, "------- Video info -------\n");
			av_log(NULL, AV_LOG_INFO, "Codec ID : %d\n", avCodecCtx->codec_id);
			av_log(NULL, AV_LOG_INFO, "Bitrate : %d\n", avCodecCtx->bit_rate);
			av_log(NULL, AV_LOG_INFO, "Width : %d, Height : %d\n", avCodecCtx->width, avCodecCtx->height);
			if (int ret = openDecoder(avCodecCtx) < 0) {
				return ret;
			}
			inCtx.videoIndex = i;
		}
		else if (avCodecCtx->codec_type == AVMEDIA_TYPE_AUDIO && inCtx.audioIndex < 0) {
			av_log(NULL, AV_LOG_INFO, "\n------- Audio info -------\n");
			av_log(NULL, AV_LOG_INFO, "Codec ID : %d\n", avCodecCtx->codec_id);
			av_log(NULL, AV_LOG_INFO, "Bitrate : %d\n", avCodecCtx->bit_rate);
			av_log(NULL, AV_LOG_INFO, "Sample rate : %d\n", avCodecCtx->sample_rate);
			av_log(NULL, AV_LOG_INFO, "Number of Channels : %d\n", avCodecCtx->channels);
			if (int ret = openDecoder(avCodecCtx) < 0) {
				return ret;
			}
			inCtx.audioIndex = i;
		}
	}

	if (inCtx.videoIndex < 0 && inCtx.audioIndex < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to retrieve input stream information\n");
		return -1;
	}
	return 0;
}

int Study::initVideoFilter() {
	AVStream * inStream = inCtx.fmtCtx->streams[inCtx.videoIndex];
	AVStream * outStream = outCtx.fmtCtx->streams[outCtx.videoIndex];
	AVCodecContext * inCodecCtx = inStream->codec;
	AVCodecContext * outCodecCtx = outStream->codec;
	AVFilterContext * rescaleFilter;
	AVFilterContext * formatFilter;
	AVFilterInOut * inputs;
	AVFilterInOut * outputs;
	char args[512];

	videoFilterCtx.filterGraph = NULL;
	videoFilterCtx.srcCtx = NULL;
	videoFilterCtx.sinkCtx = NULL;

	videoFilterCtx.filterGraph = avfilter_graph_alloc();
	if (videoFilterCtx.filterGraph == NULL) {
		return -1;
	}

	if (avfilter_graph_parse2(videoFilterCtx.filterGraph, "null", &inputs, &outputs) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to parse video filtergraph\n");
		return -2;
	}

	snprintf(args, sizeof(args), "time_base=%d/%d:video_size=%dx%d:pix_fmt=%d:pixel_aspect=%d/%d",
		inStream->time_base.num, inStream->time_base.den,
		inCodecCtx->width, inCodecCtx->height,
		inCodecCtx->pix_fmt,
		inCodecCtx->sample_aspect_ratio.num, inCodecCtx->sample_aspect_ratio.den);

	if (avfilter_graph_create_filter(
			&videoFilterCtx.srcCtx,
			avfilter_get_by_name("buffer"),
			"in", args, NULL, videoFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to create video buffer source\n");
		return -1;
	}

	if (avfilter_link(videoFilterCtx.srcCtx, 0, inputs->filter_ctx, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link video buffer source\n");
		return -1;
	}

	if (avfilter_graph_create_filter(
			&videoFilterCtx.sinkCtx,
			avfilter_get_by_name("buffersink"),
			"out", NULL, NULL, videoFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to create video buffer sink\n");
		return -1;
	}

	snprintf(args, sizeof(args), "%d:%d", DST_WIDTH, DST_HEIGHT);
	if (avfilter_graph_create_filter(
			&rescaleFilter,
			avfilter_get_by_name("scale"),
			"scale", args, NULL, videoFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link video scale filter\n");
		return -1;
	}

	if (avfilter_link(outputs->filter_ctx, 0, rescaleFilter, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link video format filter\n");
		return -1;
	}

	if (avfilter_graph_create_filter(
		&formatFilter,
		avfilter_get_by_name("format"),
		"format",
		av_get_pix_fmt_name(outCodecCtx->pix_fmt),
		NULL, videoFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to create video format filter\n");
		return -1;
	}

	if (avfilter_link(rescaleFilter, 0, formatFilter, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link video format filter\n");
		return -1;
	}


	if (avfilter_link(formatFilter, 0, videoFilterCtx.sinkCtx, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link video format filter\n");
		return -1;
	}

	if (avfilter_graph_config(videoFilterCtx.filterGraph, NULL) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to configure video filter context\n");
		return -1;
	}

	av_buffersink_set_frame_size(videoFilterCtx.sinkCtx, inCodecCtx->frame_size);

	avfilter_inout_free(&inputs);
	avfilter_inout_free(&outputs);
}

int Study::initAudioFilter() {
	AVStream * stream = inCtx.fmtCtx->streams[inCtx.audioIndex];
	AVCodecContext * codecCtx = stream->codec;
	AVFilterContext * rescaleFilter;
	AVFilterInOut * inputs;
	AVFilterInOut * outputs;
	char args[512];

	audioFilterCtx.filterGraph = NULL;
	audioFilterCtx.srcCtx = NULL;
	audioFilterCtx.sinkCtx = NULL;

	audioFilterCtx.filterGraph = avfilter_graph_alloc();
	if (audioFilterCtx.filterGraph == NULL) {
		return -1;
	}

	if (avfilter_graph_parse2(audioFilterCtx.filterGraph, "anull", &inputs, &outputs) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to parse audio filtergraph\n");
		return -2;
	}

	snprintf(args, sizeof(args), "time_base=%d/%d:sample_rate=%d:sample_fmt=%s:channel_layout=0x%" PRIx64,
		stream->time_base.num, stream->time_base.den,
		codecCtx->sample_rate,
		av_get_sample_fmt_name(codecCtx->sample_fmt),
		codecCtx->channel_layout);

	if (avfilter_graph_create_filter(
		&audioFilterCtx.srcCtx,
		avfilter_get_by_name("abuffer"),
		"in", args, NULL, audioFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to create audio buffer source\n");
		return -1;
	}

	if (avfilter_link(audioFilterCtx.srcCtx, 0, inputs->filter_ctx, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link audio buffer source\n");
		return -1;
	}

	if (avfilter_graph_create_filter(
		&audioFilterCtx.sinkCtx,
		avfilter_get_by_name("abuffersink"),
		"out", NULL, NULL, audioFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to create audio buffer sink\n");
		return -1;
	}

	snprintf(args, sizeof(args), "sample_rates=%d:channel_layouts=0x%" PRIx64, 
		DST_SAMPLE_RATE, DST_CH_LAYOUT);

	if (avfilter_graph_create_filter(
		&rescaleFilter,
		avfilter_get_by_name("aformat"),
		"aformat", args, NULL, audioFilterCtx.filterGraph) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to create audio format filter\n");
		return -1;
	}

	if (avfilter_link(outputs->filter_ctx, 0, rescaleFilter, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link audio format filter\n");
		return -1;
	}

	if (avfilter_link(rescaleFilter, 0, audioFilterCtx.sinkCtx, 0) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to link audio format filter\n");
		return -1;
	}

	if (avfilter_graph_config(audioFilterCtx.filterGraph, NULL) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed to configure audio filter context\n");
		return -1;
	}

	av_buffersink_set_frame_size(audioFilterCtx.sinkCtx, codecCtx->frame_size);

	avfilter_inout_free(&inputs);
	avfilter_inout_free(&outputs);

	return 0;
}

int Study::createOutput(const char * filename) {
	unsigned int i;
	int outIndex;

	outCtx.fmtCtx = NULL;
	outCtx.videoIndex = -1;
	outCtx.audioIndex = -1;

	if (avformat_alloc_output_context2(&outCtx.fmtCtx, NULL, NULL, filename) < 0) {
		av_log(NULL, AV_LOG_INFO, "Create failed output context %s\n", filename);
		return -1;
	}

	outIndex = 0;
	for (i = 0; i < inCtx.fmtCtx->nb_streams; i++) {
		if (i != inCtx.videoIndex && i != inCtx.audioIndex) {
			continue;
		}

		AVStream * inStream = inCtx.fmtCtx->streams[i];
		AVCodecContext * inCodecCtx = inStream->codec;

		AVCodec * encoder;
		encoder = avcodec_find_encoder((i == inCtx.videoIndex) ? AV_CODEC_ID_H264 : AV_CODEC_ID_AAC);
		if (encoder == NULL) {
			av_log(NULL, AV_LOG_INFO, "Not found encoder\n");
			return -1;
		}

		AVStream * outStream = avformat_new_stream(outCtx.fmtCtx, encoder/*inCodecCtx->codec*/);
		if (outStream == NULL) {
			av_log(NULL, AV_LOG_INFO, "Failed to allocate output stream\n");
			return -1;
		}

		AVCodecContext * outCodecCtx = outStream->codec;
		/*
		if (avcodec_copy_context(outCodecCtx, inCodecCtx) < 0) {
			av_log(NULL, AV_LOG_INFO, "Error occurred while copying context\n");
			return -1;
		}

		outStream->time_base = inStream->time_base;
		outCodecCtx->codec_tag = 0;
		*/

		if (i == inCtx.videoIndex) {
			outCodecCtx->bit_rate = inCodecCtx->bit_rate;//DST_VBIT_RATE;
			outCodecCtx->width = inCodecCtx->width;//DST_WIDTH;
			outCodecCtx->height = inCodecCtx->height;//DST_HEIGHT;
			outCodecCtx->time_base = inCodecCtx->time_base;
			outCodecCtx->sample_aspect_ratio = inCodecCtx->sample_aspect_ratio;
			outCodecCtx->pix_fmt = avcodec_default_get_format(outCodecCtx, encoder->pix_fmts);

			outCtx.videoIndex = outIndex++;
		}
		else {
			outCodecCtx->bit_rate = inCodecCtx->bit_rate;
			outCodecCtx->sample_rate = DST_SAMPLE_RATE;
			outCodecCtx->channel_layout = DST_CH_LAYOUT;
			outCodecCtx->channels = av_get_channel_layout_nb_channels(DST_CH_LAYOUT);
			outCodecCtx->sample_fmt = encoder->sample_fmts[0];
			outCodecCtx->time_base = { 1, DST_SAMPLE_RATE };

			//outCodecCtx->bit_rate = inCodecCtx->bit_rate;//DST_ABIT_RATE;
			//outCodecCtx->sample_rate = inCodecCtx->sample_rate;//DST_SAMPLE_RATE;
			//outCodecCtx->channel_layout = inCodecCtx->channel_layout;//DST_CH_LAYOUT;
			//outCodecCtx->channels = av_get_channel_layout_nb_channels(outCodecCtx->channel_layout);//DST_CH_LAYOUT);
			//outCodecCtx->sample_fmt = encoder->sample_fmts[0];
			//outCodecCtx->time_base = { 1, DST_SAMPLE_RATE };

			outCtx.audioIndex = outIndex++;
		}

		if (outCtx.fmtCtx->oformat->flags & AVFMT_GLOBALHEADER) {
			outCodecCtx->flags |= CODEC_FLAG_GLOBAL_HEADER;
		}

		if (avcodec_open2(outCodecCtx, encoder, NULL) < 0) {
			return -1;
		}
	}

	if (!(outCtx.fmtCtx->oformat->flags & AVFMT_NOFILE)) {
		if (avio_open(&outCtx.fmtCtx->pb, filename, AVIO_FLAG_WRITE) < 0) {
			av_log(NULL, AV_LOG_INFO, "Failed to create output file %s\n", filename);
			return -1;
		}
	}

	if (avformat_write_header(outCtx.fmtCtx, NULL) < 0) {
		av_log(NULL, AV_LOG_INFO, "Failed writing header into output file\n");
		return -1;
	}

	return 0;
}

void Study::release() {
	unsigned int i;

	if (inCtx.fmtCtx != NULL) {
		for (i = 0; i < inCtx.fmtCtx->nb_streams; i++) {
			AVCodecContext * codecCtx = inCtx.fmtCtx->streams[i]->codec;
			if (i == inCtx.videoIndex || i == inCtx.audioIndex) {
				avcodec_close(codecCtx);
			}
		}
		avformat_close_input(&inCtx.fmtCtx);
	}

	if (outCtx.fmtCtx != NULL) {
		for (i = 0; i < outCtx.fmtCtx->nb_streams; i++) {
			AVCodecContext * codecCtx = outCtx.fmtCtx->streams[i]->codec;
			avcodec_close(codecCtx);
		}
		if (!(outCtx.fmtCtx->oformat->flags & AVFMT_NOFILE)) {
			avio_closep(&outCtx.fmtCtx->pb);
		}
		avformat_free_context(outCtx.fmtCtx);
	}

	if (audioFilterCtx.filterGraph != NULL) {
		avfilter_graph_free(&audioFilterCtx.filterGraph);
	}
	if (videoFilterCtx.filterGraph != NULL) {
		avfilter_graph_free(&videoFilterCtx.filterGraph);
	}
}

int Study::decodePacket(AVCodecContext * codecCtx, AVPacket * pkt, AVFrame ** frame, int * gotFrame) {
	int(*decode)(AVCodecContext *, AVFrame *, int *, const AVPacket *);
	int decodedSize;

	if (codecCtx->codec_type == AVMEDIA_TYPE_VIDEO) {
		decodedSize = avcodec_decode_video2(codecCtx, *frame, gotFrame, pkt);
	}
	else {
		decodedSize = avcodec_decode_audio4(codecCtx, *frame, gotFrame, pkt);
	}

	//decode = (codecCtx->codec_type == AVMEDIA_TYPE_VIDEO) ? avcodec_decode_video2 : avcodec_decode_audio4;
	//decodedSize = decode(codecCtx, *frame, gotFrame, pkt);
	if (*gotFrame) {
		(*frame)->pts = av_frame_get_best_effort_timestamp(*frame);
	}
	return decodedSize;
}

int Study::encodeWriteFrame(AVFrame * frame, int outStreamIndex, int * gotPacket) {
	AVStream * stream = outCtx.fmtCtx->streams[outStreamIndex];
	AVCodecContext * codecCtx = stream->codec;
	int(*encode)(AVCodecContext *, AVPacket *, const AVFrame *, int *);
	AVPacket encodedPkt;

	av_init_packet(&encodedPkt);
	encodedPkt.data = NULL;
	encodedPkt.size = 0;

	encode = (outStreamIndex == outCtx.videoIndex) ? avcodec_encode_video2 : avcodec_encode_audio2;
	*gotPacket = 0;

	if (frame != NULL) frame->pict_type = AV_PICTURE_TYPE_NONE;

	if (encode(codecCtx, &encodedPkt, frame, gotPacket) < 0) {
		av_log(NULL, AV_LOG_INFO, "Error occurred when encoding frame\n");
		return -1;
	}

	if (*gotPacket) {
		encodedPkt.stream_index = outStreamIndex;
		av_packet_rescale_ts(&encodedPkt, codecCtx->time_base, stream->time_base);

		if (av_interleaved_write_frame(outCtx.fmtCtx, &encodedPkt) < 0) {
			av_log(NULL, AV_LOG_INFO, "Error occurred when writing packet into file\n");
			return -1;
		}
		av_free_packet(&encodedPkt);
	}

	return 0;
}

int Study::filterEncodeWriteFrame(AVFrame * frame, int outStreamIndex) {
	AVStream * outStream = outCtx.fmtCtx->streams[outStreamIndex];
	AVCodecContext * outCodecCtx = outStream->codec;
	FilterContext * filterContext = (outStreamIndex == outCtx.videoIndex) ? &videoFilterCtx : &audioFilterCtx;
	int gotPacket;

	AVFrame * filteredFrame = av_frame_alloc();
	if (filteredFrame == NULL) {
		return -1;
	}

	if (av_buffersrc_add_frame(filterContext->srcCtx, frame) < 0) {
		av_log(NULL, AV_LOG_INFO, "Error occurred when putting frame into filter context\n");
		return -1;
	}

	while (true) {
		if (av_buffersink_get_frame(filterContext->sinkCtx, filteredFrame) < 0) {
			break;
		}
		if (encodeWriteFrame(filteredFrame, outStreamIndex, &gotPacket) < 0) {
			break;
		}
		av_frame_unref(filteredFrame);
	}
	av_frame_free(&filteredFrame);
	return 0;
}

int Study::demuxing(int argc, char * argv[]) {
	av_register_all();

	if (argc < 2) {
		av_log(NULL, AV_LOG_INFO, "Usage : %s <input>\n", argv[0]);
		return -1;
	}

	if (openInput(argv[1]) < 0) {
		goto main_finally;
	}

	AVPacket pkt;
	int ret = 0;

	while (true) {
		ret = av_read_frame(inCtx.fmtCtx, &pkt);
		if (ret == AVERROR_EOF) {
			av_log(NULL, AV_LOG_INFO, "File end");
			break;
		}

		if (pkt.stream_index == inCtx.videoIndex) {
			av_log(NULL, AV_LOG_INFO, "Video Packet, %d, %d, %d, %d\n", pkt.pts, pkt.duration, pkt.dts, pkt.size);
		}
		else if (pkt.stream_index == inCtx.audioIndex) {
			av_log(NULL, AV_LOG_INFO, "Audio Packet, %d, %d, %d, %d\n", pkt.pts, pkt.duration, pkt.dts, pkt.size);
		}

		av_free_packet(&pkt);
	}

main_finally:
	release();
	return 0;
}

int Study::remuxing(int argc, char * argv[]) {
	av_register_all();

	if (argc < 3) {
		av_log(NULL, AV_LOG_INFO, "Usage : %s <input>\n", argv[0]);
		return -1;
	}

	if (openInput(argv[1]) < 0) {
		goto main_finally;
	}

	if (createOutput(argv[2]) < 0) {
		goto main_finally;
	}

	av_dump_format(outCtx.fmtCtx, 0, outCtx.fmtCtx->filename, 1);

	AVPacket pkt;
	int ret = 0;
	int outStreamIndex;

	while (true) {
		ret = av_read_frame(inCtx.fmtCtx, &pkt);
		if (ret == AVERROR_EOF) {
			av_log(NULL, AV_LOG_INFO, "File end");
			break;
		}

		if (pkt.stream_index != inCtx.videoIndex && pkt.stream_index != inCtx.audioIndex) {
			av_free_packet(&pkt);
			continue;
		}

		AVStream * inStream = inCtx.fmtCtx->streams[pkt.stream_index];
		outStreamIndex = (pkt.stream_index == inCtx.videoIndex) ? outCtx.videoIndex : outCtx.audioIndex;

		AVStream * outStream = outCtx.fmtCtx->streams[outStreamIndex];
		av_packet_rescale_ts(&pkt, inStream->time_base, outStream->time_base);
		pkt.stream_index = outStreamIndex;

		if (av_interleaved_write_frame(outCtx.fmtCtx, &pkt) < 0) {
			av_log(NULL, AV_LOG_INFO, "Error occurred when writing packet into file\n");
			break;
		}
	}

	av_write_trailer(outCtx.fmtCtx);

main_finally:
	release();
	return 0;
}

int Study::decoding(int argc, char * argv[]) {
	init();
	av_register_all();

	if (argc < 2) {
		av_log(NULL, AV_LOG_INFO, "Usage : %s <input>\n", argv[0]);
		return -1;
	}

	if (openInput(argv[1]) < 0) {
		goto main_finally;
	}

	AVFrame * decodedFrame = av_frame_alloc();
	if (decodedFrame == NULL) goto main_finally;

	AVPacket pkt;
	int gotFrame;
	int ret = 0;

	while (true) {
		ret = av_read_frame(inCtx.fmtCtx, &pkt);
		if (ret == AVERROR_EOF) {
			av_log(NULL, AV_LOG_INFO, "File end");
			break;
		}

		if (pkt.stream_index != inCtx.videoIndex && pkt.stream_index != inCtx.audioIndex) {
			av_free_packet(&pkt);
			continue;
		}

		AVStream * avStream = inCtx.fmtCtx->streams[pkt.stream_index];
		AVCodecContext * codecCtx = avStream->codec;
		gotFrame = 0;

		av_packet_rescale_ts(&pkt, avStream->time_base, codecCtx->time_base);

		ret = decodePacket(codecCtx, &pkt, &decodedFrame, &gotFrame);
		if (ret >= 0 && gotFrame) {
			av_log(NULL, AV_LOG_INFO, "-------------------------------------------\n");
			if (codecCtx->codec_type == AVMEDIA_TYPE_VIDEO) {
				av_log(NULL, AV_LOG_INFO, "Video:width, height:%d, %d\n", decodedFrame->width, decodedFrame->height);
				av_log(NULL, AV_LOG_INFO, "Video:sample aspect ratio:%d, %d\n", decodedFrame->sample_aspect_ratio.num, decodedFrame->sample_aspect_ratio.den);
			}
			else {
				av_log(NULL, AV_LOG_INFO, "Audio:samples, channels:%d, %d\n", decodedFrame->nb_samples, decodedFrame->channels);
			}
			av_frame_unref(decodedFrame);
		}
		av_free_packet(&pkt);
	}
	av_frame_free(&decodedFrame);
main_finally:
	release();
	return 0;
}

int Study::filtering(int argc, char * argv[]) {
	init();
	av_register_all();
	avfilter_register_all();

	if (argc < 2) {
		av_log(NULL, AV_LOG_INFO, "Usage : %s <input>\n", argv[0]);
		return -1;
	}

	if (openInput(argv[1]) < 0) {
		goto main_finally;
	}

	if (initVideoFilter() < 0) {
		goto main_finally;
	}

	if (initAudioFilter() < 0) {
		goto main_finally;
	}

	AVFrame * decodedFrame = av_frame_alloc();
	if (decodedFrame == NULL) goto main_finally;

	AVFrame * filteredFrame = av_frame_alloc();
	if (filteredFrame == NULL) {
		av_frame_free(&decodedFrame);
		goto main_finally;
	}

	AVPacket pkt;
	int gotFrame;
	int streamIndex;
	int ret = 0;

	while (true) {
		ret = av_read_frame(inCtx.fmtCtx, &pkt);
		if (ret == AVERROR_EOF) {
			av_log(NULL, AV_LOG_INFO, "File end");
			break;
		}

		streamIndex = pkt.stream_index;

		if (streamIndex != inCtx.videoIndex && streamIndex != inCtx.audioIndex) {
			av_free_packet(&pkt);
			continue;
		}

		AVStream * avStream = inCtx.fmtCtx->streams[pkt.stream_index];
		AVCodecContext * codecCtx = avStream->codec;
		gotFrame = 0;

		av_packet_rescale_ts(&pkt, avStream->time_base, codecCtx->time_base);

		ret = decodePacket(codecCtx, &pkt, &decodedFrame, &gotFrame);
		if (ret >= 0 && gotFrame) {
			FilterContext * filterCtx;
			av_log(NULL, AV_LOG_INFO, "-------------------------------------------\n");
			if (codecCtx->codec_type == AVMEDIA_TYPE_VIDEO) {
				av_log(NULL, AV_LOG_INFO, "Video:width, height:%d, %d\n", decodedFrame->width, decodedFrame->height);
				av_log(NULL, AV_LOG_INFO, "Video:sample aspect ratio:%d, %d\n", decodedFrame->sample_aspect_ratio.num, decodedFrame->sample_aspect_ratio.den);

				filterCtx = &videoFilterCtx;
				av_log(NULL, AV_LOG_INFO, "[before] Video : resolution:%dx%d\n", decodedFrame->width, decodedFrame->height);
			}
			else {
				av_log(NULL, AV_LOG_INFO, "Audio:samples, channels:%d, %d\n", decodedFrame->nb_samples, decodedFrame->channels);

				filterCtx = &audioFilterCtx;
				av_log(NULL, AV_LOG_INFO, "[before] Audio : sample_rate:%d/channels:%d\n", decodedFrame->sample_rate, decodedFrame->channels);
			}

			if (av_buffersrc_add_frame(filterCtx->srcCtx, decodedFrame) < 0) {
				av_log(NULL, AV_LOG_INFO, "Error occurred when putting frame into filter context\n");
				break;
			}

			while (true) {
				if (av_buffersink_get_frame(filterCtx->sinkCtx, filteredFrame) < 0) {
					break;
				}

				if (streamIndex == inCtx.videoIndex) {
					av_log(NULL, AV_LOG_INFO, "[after] Video : resolution:%dx%d\n", filteredFrame->width, filteredFrame->height);
				}
				else {
					av_log(NULL, AV_LOG_INFO, "[after] Audio : sample_rate:%d/channels:%d\n", filteredFrame->sample_rate, filteredFrame->channels);
				}
				av_frame_unref(filteredFrame);
			}
			av_frame_unref(decodedFrame);
		}
		av_free_packet(&pkt);
	}
	av_frame_free(&decodedFrame); 
	av_frame_free(&filteredFrame);
main_finally:
	release();
	return 0;
}

int Study::encoding(int argc, char * argv[]) {
	init();
	av_register_all();
	avfilter_register_all();

	if (argc < 3) {
		av_log(NULL, AV_LOG_INFO, "Usage : %s <input>\n", argv[0]);
		return -1;
	}

	if (openInput(argv[1]) < 0 || createOutput(argv[2]) < 0) {
		goto main_finally;
	}

	if (initVideoFilter() < 0 || initAudioFilter() < 0) {
		goto main_finally;
	}

	AVFrame * decodedFrame = av_frame_alloc();
	if (decodedFrame == NULL) {
		goto main_finally;
	}

	AVPacket pkt;
	int ret;
	int gotFrame;
	int outStreamIndex;

	while (true) {
		ret = av_read_frame(inCtx.fmtCtx, &pkt);
		if (ret == AVERROR_EOF) {
			av_log(NULL, AV_LOG_INFO, "End of frame\n");
			break;
		}

		if (pkt.stream_index != inCtx.videoIndex && pkt.stream_index != inCtx.audioIndex) {
			av_free_packet(&pkt);
			continue;
		}
		
		AVStream * inStream = inCtx.fmtCtx->streams[pkt.stream_index];
		AVCodecContext * inCodecCtx = inStream->codec;

		outStreamIndex = (pkt.stream_index == inCtx.videoIndex) ? outCtx.videoIndex : outCtx.audioIndex;

		gotFrame = 0;
		av_packet_rescale_ts(&pkt, inStream->time_base, inCodecCtx->time_base);

		ret = decodePacket(inCodecCtx, &pkt, &decodedFrame, &gotFrame);
		av_log(NULL, AV_LOG_INFO, "%s, PTS : %d, DTS : %d\n", (inCodecCtx->codec_type == AVMEDIA_TYPE_VIDEO ? "Video" : "Audio"), decodedFrame->pts, decodedFrame->pkt_dts);
		if (ret >= 0 && gotFrame) {
			ret = filterEncodeWriteFrame(decodedFrame, outStreamIndex);
			av_frame_unref(decodedFrame);
			if (ret < 0) {
				av_free_packet(&pkt);
				break;
			}
		}
		av_free_packet(&pkt);
	}

	int i;
	int gotPacket;
	/*
	for (i = 0; i < inCtx.fmtCtx->nb_streams; i++) {
		if (pkt.stream_index != inCtx.videoIndex && pkt.stream_index != inCtx.audioIndex) {
			continue;
		}

		outStreamIndex = (i == inCtx.videoIndex) ? outCtx.videoIndex : outCtx.audioIndex;
		ret = filterEncodeWriteFrame(NULL, outStreamIndex);
		if (ret < 0) {
			av_log(NULL, AV_LOG_INFO, "Error occurred while flusing flter context\n");
			break;
		}

		while (true) {
			ret = encodeWriteFrame(NULL, outStreamIndex, &gotPacket);
			if (ret < 0 || gotPacket == 0) {
				break;
			}
		}
	}*/

	av_write_trailer(outCtx.fmtCtx);
	av_frame_free(&decodedFrame);
main_finally:
	release();
	return 0;
}

//int convert_and_cut(char *file, float starttime, float endtime) {
//	AVFrame *frame;
//	AVPacket inPacket, outPacket;
//
//	if (avio_open(&outFormatContext->pb, file, AVIO_FLAG_WRITE) < 0) {
//		fprintf(stderr, "convert(): cannot open out file\n");
//		return 0;
//	}
//
//	// seek to the start time you wish.
//	// BEGIN
//	AVRational default_timebase;
//	default_timebase.num = 1;
//	default_timebase.den = AV_TIME_BASE;
//
//	// suppose you have access to the "inVideoStream" of course
//	int64_t starttime_int64 = av_rescale_q((int64_t)(starttime * AV_TIME_BASE), default_timebase, inVideoStream->time_base);
//	int64_t endtime_int64 = av_rescale_q((int64_t)(endtime * AV_TIME_BASE), default_timebase, inVideoStream->time_base);
//
//	if (avformat_seek_file(inFormatContext, inVideoStreamIndex, INT64_MIN, starttime_int64, INT64_MAX, 0) < 0) {
//		// error... do something...
//		return 0; // usually 0 is used for success in C, but I am following your code.
//	}
//
//	avcodec_flush_buffers(inVideoStream->codec);
//	// END
//
//	avformat_write_header(outFormatContext, NULL);
//	frame = avcodec_alloc_frame();
//	av_init_packet(&inPacket);
//
//	// you used avformat_seek_file() to seek CLOSE to the point you want... in order to give precision to your seek,
//	// just go on reading the packets and checking the packets PTS (presentation timestamp) 
//	while (av_read_frame(inFormatContext, &inPacket) >= 0) {
//		if (inPacket.stream_index == inVideoStreamIndex) {
//			avcodec_decode_video2(inCodecContext, frame, &frameFinished, &inPacket);
//			// this line guarantees you are getting what you really want.
//			if (frameFinished && frame->pkt_pts >= starttime_int64 && frame->pkt_pts <= endtime_int64) {
//				av_init_packet(&outPacket);
//				avcodec_encode_video2(outCodecContext, &outPacket, frame, &outputed);
//				if (outputed) {
//					if (av_write_frame(outFormatContext, &outPacket) != 0) {
//						fprintf(stderr, "convert(): error while writing video frame\n");
//						return 0;
//					}
//				}
//				av_free_packet(&outPacket);
//			}
//
//			// exit the loop if you got the frames you want.
//			if (frame->pkt_pts > endtime_int64) {
//				break;
//			}
//		}
//	}
//
//	av_write_trailer(outFormatContext);
//	av_free_packet(&inPacket);
//	return 1;
//}