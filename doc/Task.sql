CREATE TABLE `projects` (
	`project_id`	BIGINT	NOT NULL,
	`project_name`	VARCHAR(50)	NOT NULL,
	`project_status`	VARCHAR(50)	NOT NULL
);

CREATE TABLE `project_members` (
	`user_id`	VARCHAR(20)	NOT NULL,
	`project_id`	BIGINT	NOT NULL,
	`role`	VARCHAR(50)	NOT NULL
);

CREATE TABLE `tasks` (
	`task_id`	BIGINT	NOT NULL,
	`task_name`	VARCHAR(255)	NOT NULL,
	`description`	TEXT	NOT NULL,
	`status`	VARCHAR(50)	NOT NULL,
	`project_id`	BIGINT	NOT NULL,
	`user_id`	VARCHAR(20)	NOT NULL
);

CREATE TABLE `comments` (
	`comment_id`	BIGINT	NOT NULL,
	`content`	TEXT	NOT NULL,
	`user_id`	VARCHAR(20)	NOT NULL,
	`task_id`	BIGINT	NOT NULL
);

CREATE TABLE `tags` (
	`tag_id`	BIGINT	NOT NULL,
	`tag_name`	VARCHAR(50)	NOT NULL,
	`project_id`	BIGINT	NOT NULL
);

CREATE TABLE `milestones` (
	`milestone_id`	BIGINT	NOT NULL,
	`milestone_name`	VARCHAR(50)	NOT NULL,
	`start_date`	DATE	NULL,
	`due_date`	DATE	NULL,
	`project_id`	BIGINT	NOT NULL,
	`task_id`	BIGINT	NULL
);

CREATE TABLE `task_tags` (
	`tag_id`	BIGINT	NOT NULL,
	`task_id`	BIGINT	NOT NULL
);

ALTER TABLE `projects` ADD CONSTRAINT `PK_PROJECTS` PRIMARY KEY (
	`project_id`
);

ALTER TABLE `project_members` ADD CONSTRAINT `PK_PROJECT_MEMBERS` PRIMARY KEY (
	`user_id`,
	`project_id`
);

ALTER TABLE `tasks` ADD CONSTRAINT `PK_TASKS` PRIMARY KEY (
	`task_id`
);

ALTER TABLE `comments` ADD CONSTRAINT `PK_COMMENTS` PRIMARY KEY (
	`comment_id`
);

ALTER TABLE `tags` ADD CONSTRAINT `PK_TAGS` PRIMARY KEY (
	`tag_id`
);

ALTER TABLE `milestones` ADD CONSTRAINT `PK_MILESTONES` PRIMARY KEY (
	`milestone_id`
);

ALTER TABLE `task_tags` ADD CONSTRAINT `PK_TASK_TAGS` PRIMARY KEY (
	`tag_id`,
	`task_id`
);

ALTER TABLE `project_members` ADD CONSTRAINT `FK_projects_TO_project_members_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `projects` (
	`project_id`
);

ALTER TABLE `tasks` ADD CONSTRAINT `FK_projects_TO_tasks_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `projects` (
	`project_id`
);

ALTER TABLE `comments` ADD CONSTRAINT `FK_tasks_TO_comments_1` FOREIGN KEY (
	`task_id`
)
REFERENCES `tasks` (
	`task_id`
);

ALTER TABLE `tags` ADD CONSTRAINT `FK_projects_TO_tags_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `projects` (
	`project_id`
);

ALTER TABLE `milestones` ADD CONSTRAINT `FK_projects_TO_milestones_1` FOREIGN KEY (
	`project_id`
)
REFERENCES `projects` (
	`project_id`
);

ALTER TABLE `milestones` ADD CONSTRAINT `FK_tasks_TO_milestones_1` FOREIGN KEY (
	`task_id`
)
REFERENCES `tasks` (
	`task_id`
);

ALTER TABLE `task_tags` ADD CONSTRAINT `FK_tags_TO_task_tags_1` FOREIGN KEY (
	`tag_id`
)
REFERENCES `tags` (
	`tag_id`
);

ALTER TABLE `task_tags` ADD CONSTRAINT `FK_tasks_TO_task_tags_1` FOREIGN KEY (
	`task_id`
)
REFERENCES `tasks` (
	`task_id`
);

