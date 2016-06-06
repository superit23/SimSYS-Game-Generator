package edu.utdallas.gamewizard;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Dan on 6/6/2016.
 */
public class JAXBAutoUnmarshall {
    public static <T extends Object> T unmarshall(Class<T> tClass, String filePath) throws JAXBException
    {
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return(T) jaxbUnmarshaller.unmarshal(file);
    }
}
