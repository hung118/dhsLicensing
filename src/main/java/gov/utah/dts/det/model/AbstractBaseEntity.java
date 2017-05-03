package gov.utah.dts.det.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractBaseEntity<PK extends Serializable> implements Serializable {

	//Had to do this lame hack to get around a problem with struts type conversion using generic types.
	//the get and set Pk methods need to be overridden in the subclasses so that the base DAO can use
	//them to load entities.  The subclasses must also declare get and set id methods so that struts will
	//perform type conversion.  Both sets of methods get/set the same property field.
	public abstract PK getPk();
	
	public abstract void setPk(PK pk);
}