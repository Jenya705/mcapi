create table if not exists mcapi_bots (
	id integer primary key autoincrement,
	token varchar(51),
	name text,
	owner_most bigint,
	owner_least bigint,
	unique(token)
);

create table if not exists mcapi_permissions (
	bot_id integer,
	permission text,
	target_most bigint,
	target_least bigint,
	toggled boolean,
	unique(bot_id, permission, target_most, target_least)
);