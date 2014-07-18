package lichen.creeper.core.internal;

import java.util.List;

import lichen.core.services.LichenException;
import lichen.creeper.core.annotations.Initialize;

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

/**
 * 初始化对象worker
 * @author shen
 *
 */
public class InitializeObjectWorker implements ComponentClassTransformWorker2 {

	@Override
	public void transform(PlasticClass plasticClass,
			TransformationSupport support, MutableComponentModel model) {
		List<PlasticField> fields = plasticClass.getFieldsWithAnnotation(Initialize.class);
		if(fields.size()>0){
			for(PlasticField field : fields){
				Object obj;
				try {
					obj = Class.forName(field.getTypeName()).newInstance();
					field.claim(obj);
				} catch (InstantiationException e) {
					throw LichenException.wrap(e);
				} catch (IllegalAccessException e) {
					throw LichenException.wrap(e);
				} catch (ClassNotFoundException e) {
					throw LichenException.wrap(e);
				}
			}
		}
	}

}
