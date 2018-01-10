package com.pvirtech.pzpolice.test.other;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.utils.L;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioRecorderActivity extends AppCompatActivity {
    private boolean isTalk = false;
    private String TAG = "AudioRecorderActivity";
    int frequency = 44100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.start, R.id.stop, R.id.play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                isTalk = true;
//                new AudioSend().start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        record();
                    }
                }).start();
                break;
            case R.id.stop:
                isTalk = false;
                break;

            case R.id.play:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        play();
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public void play() {
        // Get the file we want to playback.
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");
        // Get the length of the audio stored in the file (16 bit so 2 bytes per short)
        // and create a short array to store the recorded audio.
        int intfileLength = (int) file.length();
//        int musicLength = (int) (file.length() / 2);
        int musicLength = (int) (file.length());
        byte[] music = new byte[intfileLength];
        try {
            // Create a DataInputStream to read the audio data back from the saved file.
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            // Read the file into the music array.
          /*  int i = 0;
            while (dis.available() > 0) {
                music[i] = dis.readByte();
                L.d(TAG, i + "---------" + String.valueOf(music[i]));
                i++;
            }*/


            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    frequency,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    musicLength * 2,
                    AudioTrack.MODE_STREAM);
            // Start playback
            audioTrack.play();
            // Write the music buffer to the AudioTrack object


            byte[] fileReader = new byte[1 * 1024];
            int i = 0;
            while (true) {
                int read = dis.read(fileReader);
                i++;
                if (read == -1) {
                    break;
                }
                L.d(TAG, i + "----" + String.valueOf(fileReader.length));
                audioTrack.write(fileReader, 0, fileReader.length);
            }

            // Close the input streams.
            dis.close();
            audioTrack.stop();

            // Create a new AudioTrack object using the same parameters as the AudioRecord
            // object used to create the file.
        /*    AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    frequency,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    musicLength * 2,
                    AudioTrack.MODE_STREAM);
            // Start playback
            audioTrack.play();
            // Write the music buffer to the AudioTrack object
            audioTrack.write(music, 0, musicLength);
            audioTrack.stop();*/

        } catch (Throwable t) {
            Log.e("AudioTrack", "Playback Failed");
        }
    }



    public void record() {

        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");

        // Delete any previous recording.
        if (file.exists())
            file.delete();


        // Create the new file.
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create " + file.toString());
        }

        try {
            // Create a DataOuputStream to write the audio data into the saved file.
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            // Create a new AudioRecord object to record the audio.
            int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    frequency, channelConfiguration,
                    audioEncoding, bufferSize);

            byte[] buffer = new byte[bufferSize];
            audioRecord.startRecording();

            isTalk = true;
            while (isTalk) {
                int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                for (int i = 0; i < bufferReadResult; i++)
                    dos.writeByte(buffer[i]);
            }


            audioRecord.stop();
            dos.close();

        } catch (Throwable t) {
            Log.e("AudioRecord", "Recording Failed");
        }
    }


    @OnClick(R.id.play)
    public void onClick() {
    }


    class AudioSend extends Thread {

        public AudioSend() {
            // android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        }

        /**
         *
         */
        @Override
        public void run() {
            super.run();
            int size = 300;
//            Socket socket = null;
//            OutputStream os = null;
            AudioRecord recorder = null;
            try {
//                socket = new Socket("192.168.0.1", 8080);
//                socket.setSoTimeout(5000);
//                os = socket.getOutputStream();
                // 获得录音缓冲区大小
                int bufferSize = AudioRecord.getMinBufferSize(size/*FlyGeonApplication.getInstance().sampRate*/, AudioFormat
                                .CHANNEL_CONFIGURATION_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
                Log.e("", "录音缓冲区大小" + bufferSize);

                // 获得录音机对象
                recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, size, AudioFormat
                        .CHANNEL_CONFIGURATION_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, bufferSize * 10);

                recorder.startRecording();// 开始录音
                byte[] readBuffer = new byte[640];// 录音缓冲区

                int length = 0;

                while (isTalk) {
                    length = recorder.read(readBuffer, 0, 640);// 从mic读取音频数据
                    if (length > 0 && length % 2 == 0) {
                        L.d(TAG, readBuffer.toString());
//                        os.write(readBuffer, 0, length);// 写入到输出流，把音频数据通过网络发送给对方

                    }
                }
                recorder.stop();
                recorder.release();
                recorder = null;
//                os.close();
//                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
