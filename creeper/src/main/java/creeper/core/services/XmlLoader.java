// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package creeper.core.services;

import lichen.migration.internal.Option;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 解析XML的类，使用JAXB模式进行解析
 * @author jcai
 */
public final class XmlLoader {
    private XmlLoader() {

    }
    public static <T> T parseXML(Class<T> clazz, InputStream is, Option<InputStream> xsd) {
        ValidationEventCollector vec = new ValidationEventCollector();
        try {
            //create io reader
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            JAXBContext context = JAXBContext.newInstance(clazz);
            //unmarshal xml
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //.unmarshal(reader).asInstanceOf[T]
            if (xsd.isDefined()) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                StreamSource schemaSource = new StreamSource(xsd.get(), "UTF-8");
                Schema schema = sf.newSchema(schemaSource);
                unmarshaller.setSchema(schema);
                unmarshaller.setEventHandler(vec);
            }
            return clazz.cast(unmarshaller.unmarshal(reader));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            close(is);
            if (xsd.isDefined()) {
                close(xsd.get());
            }
            if (vec.hasEvents()) {
                ValidationEvent[] ves = vec.getEvents();
                if (ves.length > 0) {
                    ValidationEvent ve = ves[0];
                    ValidationEventLocator vel = ve.getLocator();
                    throw new RuntimeException(String.format("line %s column %s :%s",
                            vel.getLineNumber(), vel.getColumnNumber(), ve.getMessage()));
                }
            }
        }
    }

    private static void close(Closeable io) {
        try {
            io.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 把对象转化为XML文件.
     */
    public static <T> String toXml(T obj) throws Throwable {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(obj, out);
        return new String(out.toByteArray(), "UTF-8");
    }
}
