/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.job;

import com.epam.ta.reportportal.dao.ProjectRepository;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectAttribute;
import com.epam.ta.reportportal.job.service.impl.AttachmentCleanerServiceImpl;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@ExtendWith(MockitoExtension.class)
class CleanScreenshotsJobTest {

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private AttachmentCleanerServiceImpl attachmentCleanerService;

	@InjectMocks
	private CleanScreenshotsJob cleanScreenshotsJob;

	@Test
	void runTest() {
		String name = "name";
		Project project = new Project();
		final ProjectAttribute projectAttribute = new ProjectAttribute();
		final Attribute attribute = new Attribute();
		attribute.setName("job.keepScreenshots");
		projectAttribute.setAttribute(attribute);
		projectAttribute.setValue("2 weeks");
		project.setProjectAttributes(Sets.newHashSet(projectAttribute));
		project.setName(name);

		when(projectRepository.findAllIdsAndProjectAttributes(any(), any())).thenReturn(new PageImpl<>(Collections.singletonList(project)));

		cleanScreenshotsJob.execute(null);

		verify(attachmentCleanerService, times(1)).removeProjectAttachments(any(), any(), any(), any());
	}

	@Test
	void wrongAttributeValue() {
		String name = "name";
		Project project = new Project();
		final ProjectAttribute projectAttribute = new ProjectAttribute();
		final Attribute attribute = new Attribute();
		attribute.setName("job.keepScreenshots");
		projectAttribute.setAttribute(attribute);
		projectAttribute.setValue("wrong");
		project.setProjectAttributes(Sets.newHashSet(projectAttribute));
		project.setName(name);

		when(projectRepository.findAllIdsAndProjectAttributes(any(), any())).thenReturn(new PageImpl<>(Collections.singletonList(project)));

		cleanScreenshotsJob.execute(null);
	}
}