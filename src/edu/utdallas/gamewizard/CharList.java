//Used to import characters into table
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CharList {

	@XmlElementWrapper(name = "characters")
	@XmlElement(name = "character")
	ArrayList<CharPrev> characterList;

	public CharList() {
		characterList = new ArrayList<CharPrev>();
	}

	public ArrayList<CharPrev> getCharacters() {
		return characterList;
	}

	@XmlElement
	public void setCharacters(ArrayList<CharPrev> characterList) {
		this.characterList = characterList;
	}
}
