package edu.utdallas.gamePlayEngine.view;

public class GameViewBoundary {
	private GameViewControl gameViewControl;

	public GameViewBoundary(GameView gameView) {
		this.gameViewControl = new GameViewControl(gameView);
	}

}
