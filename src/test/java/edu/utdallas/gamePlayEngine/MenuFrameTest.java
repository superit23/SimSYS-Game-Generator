package edu.utdallas.gamePlayEngine;

import edu.utdallas.gamePlayEngine.view.GameView;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dan on 6/8/2016.
 */
public class MenuFrameTest {

    @Test
    public void testStart() throws Exception {
        GameView gameView = new GameView();
        MenuFrame menuFrame = new MenuFrame(gameView);

        //menuFrame.setVisible(true);

        Thread.sleep(5000);
    }

}