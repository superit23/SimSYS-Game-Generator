//Used for testing XML does not impact Wizard Tool
package edu.utdallas.gamewizard;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class Test {
	public static void main(String args[]) {
		try {
			File file = new File(
					"WizardRepo\\KnowledgeAreas/Milwaukee Public Schools_Fourth_Math.xml");
			JAXBContext jaxbContext = JAXBContext
					.newInstance(KnowledgeRepo.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			KnowledgeRepo kr = (KnowledgeRepo) jaxbUnmarshaller.unmarshal(file);
			System.out.println(kr);
			System.out.println(kr.getKnowledgeAreas().get(0));
			System.out.println(kr.getKnowledgeAreas().get(0)
					.getSubKnowledgeArea().get(0));
			System.out.println(kr.getKnowledgeAreas().get(0)
					.getSubKnowledgeArea().get(0).getLearningObjectives()
					.get(1).getTaxonomyCategories());
			System.out.println(kr.getKnowledgeAreas().get(0)
					.getSubKnowledgeArea().get(0).getLearningObjectives()
					.get(1).getTaxonomyCategories());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
