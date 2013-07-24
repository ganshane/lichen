// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

/**
 * @author jcai
 */
public final class XmlLoader {
    public static <T> T parseXML(Class<T> clazz,InputStream is,Option<InputStream> xsd){
        ValidationEventCollector vec = new ValidationEventCollector();
        try{
            //create io reader
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            JAXBContext context = JAXBContext.newInstance(clazz);
            //unmarshal xml
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //.unmarshal(reader).asInstanceOf[T]
            if (xsd.isDefined()){
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                StreamSource schemaSource = new StreamSource(xsd.get(), "UTF-8");
                Schema schema = sf.newSchema(schemaSource);
                unmarshaller.setSchema(schema);
                unmarshaller.setEventHandler(vec);
            }
            return clazz.cast(unmarshaller.unmarshal(reader));
        }catch(Throwable e){
                throw new RuntimeException(e);
        }finally {
            close(is);
            if (xsd.isDefined())
                close(xsd.get());
            if (vec.hasEvents()){
                ValidationEvent[] ves= vec.getEvents();
                if (ves.length > 0 ){
                    ValidationEvent ve = ves[0];
                    ValidationEventLocator vel = ve.getLocator();
                    throw new RuntimeException(String.format("line %s column %s :%s",vel.getLineNumber(),vel.getColumnNumber(),ve.getMessage()));
                }
            }
        }
    }
    private static void close(Closeable io){try{io.close();}catch(Throwable e){}}

    /**
     * 把对象转化为XML文件
     */
    public static <T> String toXml(T obj) throws Throwable{
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(obj,out);
        return new String(out.toByteArray(),"UTF-8");
    }
}
