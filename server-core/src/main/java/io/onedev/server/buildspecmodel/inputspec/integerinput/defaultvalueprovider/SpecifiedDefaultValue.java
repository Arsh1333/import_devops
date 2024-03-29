package io.onedev.server.buildspecmodel.inputspec.integerinput.defaultvalueprovider;

import io.onedev.server.annotation.Editable;
import io.onedev.server.annotation.OmitName;

@Editable(order=100, name="Use specified default value")
public class SpecifiedDefaultValue implements DefaultValueProvider {

	private static final long serialVersionUID = 1L;

	private int value;

	@Editable(name="Specified default value")
	@OmitName
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int getDefaultValue() {
		return getValue();
	}

}
