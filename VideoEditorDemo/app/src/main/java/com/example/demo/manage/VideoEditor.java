package com.example.demo.manage;

import com.example.demo.manage.model.VideoClip;
import com.example.core.manage.Logger;

import org.mp4parser.Container;
import org.mp4parser.muxer.Movie;
import org.mp4parser.muxer.Track;
import org.mp4parser.muxer.builder.DefaultMp4Builder;
import org.mp4parser.muxer.container.mp4.MovieCreator;
import org.mp4parser.muxer.tracks.AppendTrack;
import org.mp4parser.muxer.tracks.ClippedTrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hwang on 2017-06-14.
 * 작성자 : 황의택
 * 내용 : 비디오를 편집하기 위한 기능을 모아 놓은 클래스
 */
public class VideoEditor {
    private Movie movie;
    private ArrayList<VideoClip> clips;

    private String srcPath;
    private String targetPath;

    private VideoEditor(ArrayList<VideoClip> clips, String srcPath, String targetPath) {
        this.clips = clips;
        this.srcPath = srcPath;
        this.targetPath = targetPath;
    }

    public static Builder builder() {
        return new Builder();
    }
//    public static Builder builder() {
//        return Builder.getInstance();
//    }

    public static class Builder {
//        private static Builder builder;

//        private HashMap<String, VideoEditor> editors = new HashMap<>();
        private ArrayList<VideoClip> clips = new ArrayList<>();

        private String srcPath;
        private String targetPath;

        private Builder() {
        }
//        public synchronized static Builder getInstance() {
//            if (builder == null) {
//                builder = new Builder();
//            }
//            return builder;
//        }

        public Builder setSrcPath(String path) {
            this.srcPath = path;
            return this;
        }
        public Builder setTargetPath(String path) {
            this.targetPath = path;
            return this;
        }
        public Builder setVideoClip(VideoClip ... clips) {
            this.clips.clear();
            for (VideoClip clip : clips) {
                this.clips.add(clip);
            }
            return this;
        }
        public Builder setVideoClip(ArrayList<VideoClip> clips) {
            this.clips = clips;
            return this;
        }
        public VideoEditor build() {
            return new VideoEditor(clips, srcPath, targetPath);
//            return build("default");
        }
//        public VideoEditor build(String key) {
//            if (clips.size() == 0) {
//                throw new RuntimeException("There are no clips to edit.");
//            }
//            if (editors.containsKey(key)) {
//
//            } else {
//                editors.put(key, new VideoEditor(clips, srcPath, targetPath));
//            }
//            return editors.get(key);
//        }
    }

    public VideoEditor cut() {
        try {
            VideoClip clip = clips.get(0);

            movie = MovieCreator.build(srcPath);

            List<Track> tracks = movie.getTracks();
            movie.setTracks(new LinkedList<Track>());

            double startAudioTime = clip.getStartTime();
            double endAudioTime = clip.getStopTime();

            for (Track track : tracks) {
                long currentSample = 0;
                long currentSyncSample;
                double currentTime = 0;
                double lastTime = -1;
                long startSample = -1;
                long endSample = -1;
                int syncCount = 0;

                for (int i = 0; i < track.getSampleDurations().length; i++) {
                    long delta = track.getSampleDurations()[i];

                    if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
                        currentSyncSample = track.getSyncSamples()[syncCount];

                        if (currentTime > lastTime && currentTime <= clip.getStartTime()) {
                            if (currentSyncSample <= currentSample) {
                                startSample = track.getSyncSamples()[syncCount];
                                syncCount++;
                                startAudioTime = currentTime;
                            }
                        } else if (currentTime > lastTime && currentTime <= clip.getStopTime()) {
                            if (currentSyncSample <= currentSample) {
                                if (syncCount < (track.getSyncSamples().length - 1)) {
                                    syncCount++;
                                }
                                endSample = track.getSyncSamples()[syncCount];
                            }
                        }
                        if (currentSample <= endSample) {
                            endAudioTime = currentTime;
                        } /*else {
                            break;
                        }*/
                    } else {
                        if (currentTime > lastTime && currentTime <= startAudioTime) {
                            startSample = currentSample;
                        }
                        if (currentTime > lastTime && currentTime <= endAudioTime) {
                            endSample = currentSample;
                        } /*else {
                            break;
                        }*/
                    }
                    lastTime = currentTime;
                    currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
                    currentSample++;
                }

                Logger.d("s1:" + startSample + ",e1:" + endSample);
                movie.addTrack(new AppendTrack(new ClippedTrack(track, startSample - 1, endSample - 1)/*, new ClippedTrack(track, startSample2, endSample2)*/));
            }
            multiplexing(movie);
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
        return this;
    }

    public void cut(String inPath, String outPath, long startVideoTime, long endVideoTime) {
        try {
            //long start1 = System.currentTimeMillis();
            movie = MovieCreator.build(inPath);

            List<Track> tracks = movie.getTracks();
            movie.setTracks(new LinkedList<Track>());

            double startAudioTime = startVideoTime;
            double endAudioTime = endVideoTime;

            for (Track track : tracks) {
                long currentSample = 0;
                long currentSyncSample;
                double currentTime = 0;
                double lastTime = -1;
                long startSample = -1;
                long endSample = -1;
                int syncCount = 0;

                for (int i = 0; i < track.getSampleDurations().length; i++) {
                    long delta = track.getSampleDurations()[i];

                    if (track.getSyncSamples() != null) {
                        currentSyncSample = track.getSyncSamples()[syncCount];

                        if (currentTime > lastTime && currentTime <= startVideoTime) {
                            if (currentSyncSample <= currentSample) {
                                startSample = track.getSyncSamples()[syncCount];
                                syncCount++;
                                startAudioTime = currentTime;
                            }
                        } else if (currentTime > lastTime && currentTime <= endVideoTime) {
                            if (currentSyncSample <= currentSample) {
                                if (syncCount < (track.getSyncSamples().length - 1)) {
                                    syncCount++;
                                }
                                endSample = track.getSyncSamples()[syncCount];
                            }
                        }
                        if (currentSample <= endSample) {
                            endAudioTime = currentTime;
                        } else {
                            break;
                        }
                    } else {
                        if (currentTime > lastTime && currentTime <= startAudioTime) {
                            startSample = currentSample;
                        }
                        if (currentTime > lastTime && currentTime <= endAudioTime) {
                            endSample = currentSample;
                        } else {
                            break;
                        }
                    }
                    lastTime = currentTime;
                    currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
                    currentSample++;
                }

                Logger.d("s1:" + startSample + ",e1:" + endSample);

                movie.addTrack(new AppendTrack(new ClippedTrack(track, startSample - 1, endSample - 1)/*, new ClippedTrack(track, startSample2, endSample2)*/));
            }
            multiplexing(movie);
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public VideoEditor multiplexing() {
        if (movie == null) {
            throw new RuntimeException("Movie(Video) object is null.");
        }
        if (targetPath == null || targetPath.equals("")) {
            throw new RuntimeException("No output path specified.");
        }
        return multiplexing(movie);
    }
    public VideoEditor multiplexing(Movie movie) {
        if (targetPath == null || targetPath.equals("")) {
            throw new RuntimeException("No output path specified.");
        }
        return multiplexing(targetPath.substring(0, targetPath.lastIndexOf("/")), targetPath.substring(targetPath.lastIndexOf("/") + 1), movie);
    }
    public VideoEditor multiplexing(String dirPath, String fileName, Movie movie) {
        FileOutputStream fos = null;
        FileChannel fc = null;

        try {
            Container out = new DefaultMp4Builder().build(movie);
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            fos = new FileOutputStream(dirPath + "/" + fileName);
            fc = fos.getChannel();
            out.writeContainer(fc);
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this;
    }
}
