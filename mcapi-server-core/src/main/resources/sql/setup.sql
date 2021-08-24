create table if not exists mcapi_bots (
	id bigint auto_increment,
	token varchar(51),
	owner_most bigint,
	owner_least bigint,
	primary key(id),
	unique(token)
);

create table if not exists mcapi_permissions (
	botId bigint,
	permission text,
	target_most bigint,
	target_least bigint,
	toggled boolean,
	unique(botId, permission, target_most, target_least)
);