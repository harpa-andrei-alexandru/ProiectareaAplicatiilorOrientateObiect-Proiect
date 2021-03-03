package Game.Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer
{
    private Clip clip;


    public MusicPlayer(String path)
    {
       setClip(path);
    }

    public void setClip(String path)
    {
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));

            AudioFormat baseFormat = ais.getFormat();

            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            AudioInputStream decodedAis = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(decodedAis);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        if(clip == null)
            return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop()
    {
        if(clip.isRunning())
            clip.stop();
    }

    public void loop()
    {
        if(clip == null)
            return;
        clip.setFramePosition(0);
        clip.loop(5);
    }

    public void close()
    {
        stop();
        clip.close();
    }
}
