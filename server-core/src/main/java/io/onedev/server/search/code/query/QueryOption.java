package io.onedev.server.search.code.query;

import org.apache.wicket.markup.html.form.FormComponentPanel;

import java.io.Serializable;

public interface QueryOption extends Serializable {

	BlobQuery.Builder newInsideCommitQueryBuilder();

	FormComponentPanel<? extends QueryOption> newOptionEditor(String componentId);
	
}
