
@TypeDefs({@TypeDef(typeClass = DateUserType.class,
				    name= CreeperCoreConstants.TYPE_INT_DATE)
	     })
@GenericGenerator(name="uuid",strategy = "uuid")
package lichen.creeper.core.entities;
import lichen.creeper.core.CreeperCoreConstants;
import lichen.creeper.core.internal.hibernate.DateUserType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
