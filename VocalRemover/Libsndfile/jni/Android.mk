# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Connector
LOCAL_SRC_FILES := libsndjni-connector.c \
				   invert.c \
				   ./libsndfile/G72x/g721.c \
				   ./libsndfile/G72x/g723_16.c \
				   ./libsndfile/G72x/g723_24.c \
				   ./libsndfile/G72x/g723_40.c \
				   ./libsndfile/G72x/g72x_test.c \
				   ./libsndfile/G72x/g72x.c \
				   ./libsndfile/GSM610/add.c \
				   ./libsndfile/GSM610/code.c \
				   ./libsndfile/GSM610/decode.c \
				   ./libsndfile/GSM610/gsm_create.c \
				   ./libsndfile/GSM610/gsm_decode.c \
				   ./libsndfile/GSM610/gsm_destroy.c \
				   ./libsndfile/GSM610/gsm_encode.c \
				   ./libsndfile/GSM610/gsm_option.c \
				   ./libsndfile/GSM610/long_term.c \
				   ./libsndfile/GSM610/lpc.c \
				   ./libsndfile/GSM610/preprocess.c \
				   ./libsndfile/GSM610/rpe.c \
				   ./libsndfile/GSM610/short_term.c \
				   ./libsndfile/GSM610/table.c \
				   ./libsndfile/aiff.c \
				   ./libsndfile/alaw.c \
				   ./libsndfile/au.c \
				   ./libsndfile/audio_detect.c \
				   ./libsndfile/avr.c \
				   ./libsndfile/broadcast.c \
				   ./libsndfile/caf.c \
				   ./libsndfile/chanmap.c \
				   ./libsndfile/chunk.c \
				   ./libsndfile/command.c \
				   ./libsndfile/common.c \
				   ./libsndfile/dither.c \
				   ./libsndfile/double64.c \
				   ./libsndfile/dwd.c \
				   ./libsndfile/dwvw.c \
				   ./libsndfile/file_io.c \
				   ./libsndfile/flac.c \
				   ./libsndfile/float32.c \
				   ./libsndfile/g72x.c \
				   ./libsndfile/gsm610.c \
				   ./libsndfile/htk.c \
				   ./libsndfile/id3.c \
				   ./libsndfile/ima_adpcm.c \
				   ./libsndfile/ima_oki_adpcm.c \
				   ./libsndfile/interleave.c \
				   ./libsndfile/ircam.c \
				   ./libsndfile/macbinary3.c \
				   ./libsndfile/macos.c \
				   ./libsndfile/mat4.c \
				   ./libsndfile/mat5.c \
				   ./libsndfile/mpc2k.c \
				   ./libsndfile/ms_adpcm.c \
				   ./libsndfile/nist.c \
				   ./libsndfile/paf.c \
				   ./libsndfile/pcm.c \
				   ./libsndfile/pvf.c \
				   ./libsndfile/sndfile.c \
				   ./libsndfile/raw.c \
				   ./libsndfile/rf64.c \
				   ./libsndfile/rx2.c \
				   ./libsndfile/sd2.c \
				   ./libsndfile/sds.c \
				   ./libsndfile/strings.c \
				   ./libsndfile/svx.c \
				   ./libsndfile/txw.c \
				   ./libsndfile/ulaw.c \
				   ./libsndfile/voc.c \
				   ./libsndfile/vox_adpcm.c \
				   ./libsndfile/w64.c \
				   ./libsndfile/wav_w64.c \
				   ./libsndfile/wav.c \
				   ./libsndfile/wve.c \
				   ./libsndfile/xi.c \
LOCAL_LDLIBS := -llog
				   
include $(BUILD_SHARED_LIBRARY)
