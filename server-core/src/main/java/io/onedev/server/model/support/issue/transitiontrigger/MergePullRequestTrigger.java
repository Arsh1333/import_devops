package io.onedev.server.model.support.issue.transitiontrigger;

import io.onedev.server.annotation.Editable;

@Editable(order=250, name="Pull request is merged")
public class MergePullRequestTrigger extends PullRequestTrigger {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDescription() {
		if (getBranches() != null)
			return "pull request to branches '" + getBranches() + "' is merged";
		else
			return "pull request to any branch is merged";
	}

}
