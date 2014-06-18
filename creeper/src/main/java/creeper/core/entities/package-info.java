
@TypeDefs({@TypeDef(typeClass = DateUserType.class,name= CreeperCoreConstants.TYPE_INT_DATE)})
@GenericGenerator(name="uuid",strategy = "uuid")
package creeper.core.entities;
import creeper.core.CreeperCoreConstants;
import creeper.core.internal.hibernate.DateUserType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
