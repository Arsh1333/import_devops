package io.onedev.server.buildspec.param.instance;

import io.onedev.commons.utils.ExplicitException;
import io.onedev.server.annotation.ChoiceProvider;
import io.onedev.server.annotation.Editable;
import io.onedev.server.buildspec.param.ParamCombination;
import io.onedev.server.buildspec.param.spec.ParamSpec;
import io.onedev.server.model.Build;
import io.onedev.server.util.Input;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Editable(name="Use value of specified parameter/secret")
public class PassthroughValue implements ValueProvider {

	private static final long serialVersionUID = 1L;

	public static final String DISPLAY_NAME = "Use value of specified parameter";
	
	private String paramName;
	
	@Editable
	@ChoiceProvider("getParamChoices")
	@NotEmpty
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@SuppressWarnings("unused")
	private static List<String> getParamChoices() {
		List<ParamSpec> paramSpecs = ParamSpec.list();
		if (paramSpecs != null) 
			return paramSpecs.stream().map(it->it.getName()).collect(Collectors.toList());
		else
			return new ArrayList<>();
	}
	
	@Override
	public List<String> getValue(Build build, ParamCombination paramCombination) {
		if (paramCombination != null) {
			Input param = paramCombination.getParamInputs().get(paramName);
			if (param != null) {
				return param.getValues();
			} else {
				String message = String.format("Param not found: %s", paramName);
				throw new ExplicitException(message);
			}
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PassthroughValue)) 
			return false;
		if (this == other)
			return true;
		PassthroughValue otherPassthroughValues = (PassthroughValue) other;
		return new EqualsBuilder()
			.append(paramName, otherPassthroughValues.paramName)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(paramName)
			.toHashCode();
	}		

}
