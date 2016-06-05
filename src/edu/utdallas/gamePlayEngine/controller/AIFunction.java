package edu.utdallas.gamePlayEngine.controller;

import java.util.List;
import java.util.Random;

import simsys.schema.components.Mood;

/**
 * This class is used to explicitly define the interaction logic between
 * characters' attributes when a question is answered in a challenge
 *
 * @author Greg Merkel, Jeremy Wallenfang
 * @version Created: Mar 25, 2015, Modified: date - description
 */
public class AIFunction {

	// blank constructor
	public AIFunction() {

	}

	// method that takes in a character list, question difficulty, and two
	// players answers for a given question (first nemesis, then user)
	public List<simsys.schema.components.Character> recalculate(
			List<simsys.schema.components.Character> characterList,
			int difficulty, boolean player0, boolean player1) {

		// nemesis
		simsys.schema.components.Character ch0 = characterList.get(0);
		// user
		simsys.schema.components.Character ch1 = characterList.get(1);

		double m0, m1;

		// both players get question right
		if (player0 && player1) {
			m0 = ch0.getAttributes().getMood().getValue()
					+ difficulty
					* (ch0.getAttributes().getCharisma() / ch0.getAttributes()
							.getSkill()) - 50;
			m1 = ch1.getAttributes().getMood().getValue()
					+ difficulty
					* (ch1.getAttributes().getCharisma() / ch1.getAttributes()
							.getSkill()) - 50;
		}
		// nemesis right and user wrong
		else if (player0 && !player1) {
			m0 = ch0.getAttributes().getMood().getValue()
					+ difficulty
					* (ch0.getAttributes().getCharisma() / ch0.getAttributes()
							.getSkill()) - 50;
			m1 = ch1.getAttributes().getMood().getValue()
					- (5 - difficulty)
					* (ch0.getAttributes().getCharisma() / ch1.getAttributes()
							.getResilience()) - 50;
		}
		// nemesis wrong and user right
		else if (!player0 && player1) {
			m0 = ch0.getAttributes().getMood().getValue()
					- (5 - difficulty)
					* (ch1.getAttributes().getCharisma() / ch0.getAttributes()
							.getResilience()) - 50;
			m1 = ch1.getAttributes().getMood().getValue()
					+ difficulty
					* (ch1.getAttributes().getCharisma() / ch1.getAttributes()
							.getSkill()) - 50;
		}
		// nemesis wrong and user wrong
		else {
			m0 = ch0.getAttributes().getMood().getValue()
					- (5 - difficulty)
					* (ch0.getAttributes().getSkill() / ch0.getAttributes()
							.getResilience()) - 50;
			m1 = ch1.getAttributes().getMood().getValue()
					- (5 - difficulty)
					* (ch1.getAttributes().getSkill() / ch1.getAttributes()
							.getResilience()) - 50;
		}

		// get the two characters' moods and assign the new mood values
		Mood ch0_mood = ch0.getAttributes().getMood();
		Mood ch1_mood = ch1.getAttributes().getMood();
		ch0_mood.setValue((int) (100 / (1 + Math.exp(-1 * m0 / 20))));
		ch1_mood.setValue((int) (100 / (1 + Math.exp(-1 * m1 / 20))));

		// set nemesis and users' attributes
		ch0.setAttributes(ch0_mood, ch0.getAttributes().getCharisma(), ch0
				.getAttributes().getSkill(), ch0.getAttributes()
				.getResilience());
		ch1.setAttributes(ch1_mood, ch1.getAttributes().getCharisma(), ch1
				.getAttributes().getSkill(), ch1.getAttributes()
				.getResilience());

		// refresh the current mood images
		ch0.getAttributes().RefreshMood();
		ch1.getAttributes().RefreshMood();

		// set characters back into character list with new attribute values
		characterList.set(0, ch0);
		characterList.set(1, ch1);

		// return the character list
		return characterList;
	}

	// method to generate an answer to a question for a given character
	public boolean GenerateAnswer(
			simsys.schema.components.Character character) {

		// the skill is what determines how likely it is that a character will
		// get a question right
		int skill = character.getAttributes().getSkill();

		// generate a value between 0 and 100
		Random generator = new Random();
		int answer = generator.nextInt(100);
		System.out.println("RANDOM NUMBER ANSWER: " + answer);

		// use the random number and skill to determine if character got the
		// question right
		if (answer > skill) {
			return false;
		} else {
			return true;
		}

	}
}
