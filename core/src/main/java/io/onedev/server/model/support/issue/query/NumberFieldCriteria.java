package io.onedev.server.model.support.issue.query;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import io.onedev.server.model.Issue;
import io.onedev.server.model.IssueFieldUnary;
import io.onedev.server.model.Project;

public class NumberFieldCriteria extends FieldCriteria {

	private static final long serialVersionUID = 1L;

	private final int value;
	
	private final int operator;
	
	public NumberFieldCriteria(String name, int value, int operator) {
		super(name);
		this.value = value;
		this.operator = operator;
	}

	@Override
	public Predicate getPredicate(Project project, QueryBuildContext context) {
		Path<Integer> attribute = context.getJoin(getFieldName()).get(IssueFieldUnary.ORDINAL);
		if (operator == IssueQueryLexer.Is)
			return context.getBuilder().equal(attribute, value);
		else if (operator == IssueQueryLexer.IsNot)
			return context.getBuilder().notEqual(attribute, value);
		else if (operator == IssueQueryLexer.IsGreaterThan)
			return context.getBuilder().greaterThan(attribute, value);
		else
			return context.getBuilder().lessThan(attribute, value);
	}

	@Override
	public boolean matches(Issue issue) {
		Integer fieldValue = (Integer) getFieldValue(issue);
		if (operator == IssueQueryLexer.Is)
			return Objects.equals(fieldValue, value);
		else if (operator == IssueQueryLexer.IsNot)
			return !Objects.equals(fieldValue, value);
		else if (operator == IssueQueryLexer.IsGreaterThan)
			return fieldValue != null && fieldValue > value;
		else
			return fieldValue != null && fieldValue < value;
	}

	@Override
	public boolean needsLogin() {
		return false;
	}

	@Override
	public String toString() {
		return IssueQuery.quote(getFieldName()) + " " + IssueQuery.getRuleName(operator) + " " + IssueQuery.quote(String.valueOf(value));
	}

	@Override
	public void populate(Issue issue, Serializable fieldBean, Set<String> initedLists) {
		if (operator == IssueQueryLexer.Is)
			setFieldValue(fieldBean, value);
	}

}
