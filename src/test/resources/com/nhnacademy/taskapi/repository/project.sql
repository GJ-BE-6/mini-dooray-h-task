INSERT INTO projects values (999, 'testproject', '활성'), (1000, 'testproject2', '활성');

insert into tasks (project_id, task_id, user_id, task_status, task_description, task_name)
values (1000, 1234, 'test-user', '활성', '업무', '숙면');