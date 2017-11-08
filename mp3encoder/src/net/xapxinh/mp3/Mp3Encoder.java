package net.xapxinh.mp3;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public class Mp3Encoder {

	public static void main(String[] args) throws IllegalArgumentException, InputFormatException, EncoderException {
		File source = new File("C:\\Users\\tsa1hc\\2F01f853610e24780b.wav");
		File target = new File("C:\\Users\\tsa1hc\\2F01f853610e24780b.mp3");
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(320000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);
	}
}
