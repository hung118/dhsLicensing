package gov.utah.dts.det.ccl.model;

public interface ModifiableStateObject<S extends Enum<S>, C extends Enum<C>> {

	public S getState();
	
	public void setState(S state, C changeType, String note);
}