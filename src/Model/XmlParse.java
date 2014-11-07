package Model;

import java.util.*;
import org.w3c.dom.Element;

/**
 * 
 */
public interface XmlParse {

    /**
     * @param Element 
     * @return
     */
    public int buildFromXML(Element element);

}