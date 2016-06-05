package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FailureScreen")
public class FailureScreen extends BaseScreen {
	@Override
	public final ScreenType getType() {
		return ScreenType.FAILURE;
	}
}
