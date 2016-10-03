package AppPackage;

    import java.io.BufferedInputStream;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import javazoom.jl.decoder.JavaLayerException;
    import javazoom.jl.player.Player;

public class MainClass
{
    FileInputStream FIS;
    BufferedInputStream BIS;

    public Player player;
    public long PauseLocation;
    public long SongTotalLength;
    public String filepath;

    public void stop()
    {
        if (player != null)
        {
            player.close();
            //these 2 statements because if we dont do this, then after touching stop
            //and then resuming, it actually resumes which we dont want
            SongTotalLength=0;
            PauseLocation=0;

            //removes the song name from text label after we press stop
            MP3PlayerGUI.Display.setText("");
        }
    }

    public void pause()
    {
        if (player != null)
        {
            try
            {
                PauseLocation=FIS.available(); //stores remaining length of the song
                player.close();
            }
            catch (IOException ex)
            {

            }
        }
    }

    public void play(String path)
    {

        try
        {
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);

            player = new Player(BIS);
            SongTotalLength = FIS.available();
            filepath = path + "";
        }
        catch (FileNotFoundException | JavaLayerException ex)
        {

        }
        catch (IOException ex)
        {

        }

        new Thread()    // we want this because we want the song to play in background
                        // and access and roam around anywhere in the software
        {
            @Override
            public void run()
            {
                try
                {
                    player.play();
                }
                catch (JavaLayerException ex)
                {

                }
            }
        }.start();
    }

    public void resume()
    {
        try
        {
            FIS = new FileInputStream(filepath);
            BIS = new BufferedInputStream(FIS);

            player = new Player(BIS);
            FIS.skip(SongTotalLength-PauseLocation);
        }
        catch (FileNotFoundException | JavaLayerException ex)
        {

        }
        catch (IOException ex)
        {

        }

        new Thread()    // we want this because we want the song to play in background
                        // and access and roam around anywhere in the software
        {
            @Override
            public void run()
            {
                try
                {
                    player.play();
                }
                catch (JavaLayerException ex)
                {

                }
            }
        }.start();
    }
}
