package io.onedev.server.entitymanager.impl;

import io.onedev.commons.utils.StringUtils;
import io.onedev.server.entitymanager.MilestoneManager;
import io.onedev.server.entitymanager.ProjectManager;
import io.onedev.server.model.Milestone;
import io.onedev.server.model.Project;
import io.onedev.server.persistence.annotation.Sessional;
import io.onedev.server.persistence.annotation.Transactional;
import io.onedev.server.persistence.dao.BaseEntityManager;
import io.onedev.server.persistence.dao.Dao;
import io.onedev.server.persistence.dao.EntityCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DefaultMilestoneManager extends BaseEntityManager<Milestone> implements MilestoneManager {

	private final ProjectManager projectManager;
	
	@Inject
	public DefaultMilestoneManager(Dao dao, ProjectManager projectManager) {
		super(dao);
		this.projectManager = projectManager;
	}

	@Sessional
	@Override
	public Milestone findInHierarchy(String milestoneFQN) {
		String projectName = StringUtils.substringBefore(milestoneFQN, ":");
		Project project = projectManager.findByPath(projectName);
		if (project != null) { 
			String milestoneName = StringUtils.substringAfter(milestoneFQN, ":");
			EntityCriteria<Milestone> criteria = EntityCriteria.of(Milestone.class);
			criteria.add(Restrictions.in("project", project.getSelfAndAncestors()));
			criteria.add(Restrictions.eq("name", milestoneName));
			criteria.setCacheable(true);
			return find(criteria);
		} else { 
			return null;
		}
	}
	
	@Sessional
	@Override
	public Milestone findInHierarchy(Project project, String name) {
		EntityCriteria<Milestone> criteria = EntityCriteria.of(Milestone.class);
		criteria.add(Restrictions.in("project", project.getSelfAndAncestors()));
		criteria.add(Restrictions.eq("name", name));
		criteria.setCacheable(true);
		return find(criteria);
	}

	@Override
	public List<Milestone> query() {
		return query(true);
	}

	@Override
	public int count() {
		return count(true);
	}

	@Sessional
	@Override
	public Milestone findNextOpen(Project project) {
		EntityCriteria<Milestone> criteria = EntityCriteria.of(Milestone.class);
		criteria.add(Restrictions.in("project", project.getSelfAndAncestors()));
		criteria.add(Restrictions.eq(Milestone.PROP_CLOSED, false));
		criteria.addOrder(Order.asc(Milestone.PROP_DUE_DATE));
		return find(criteria);
	}

	@Transactional
	@Override
	public void createOrUpdate(Milestone milestone) {
		dao.persist(milestone);
	}
	
}
