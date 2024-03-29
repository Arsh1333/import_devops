package io.onedev.server.model.support.issue.field.spec;

import java.util.List;
import java.util.Map;

import io.onedev.server.buildspecmodel.inputspec.CommitInput;
import io.onedev.server.annotation.Editable;

@Editable(order=1400, name=FieldSpec.COMMIT)
public class CommitField extends FieldSpec {

	private static final long serialVersionUID = 1L;

	@Override
	public String getPropertyDef(Map<String, Integer> indexes) {
		return CommitInput.getPropertyDef(this, indexes);
	}

	@Editable
	@Override
	public boolean isAllowMultiple() {
		return false;
	}
	
	@Override
	public Object convertToObject(List<String> strings) {
		return CommitInput.convertToObject(strings);
	}

	@Override
	public List<String> convertToStrings(Object value) {
		return CommitInput.convertToStrings(value);
	}

	@Override
	protected void runScripts() {
	}
	
}
