create table if not exists mcapi_bots(
	id integer primary key autoincrement,
	token character(51) not null,
	name varchar(64) not null,
	owner_most bigint not null,
	owner_least bigint not null,
	unique(token)
);ANOTHEROPERATION
create table if not exists mcapi_permissions(
	bot_id integer not null,
	permission varchar(255) not null,
	target_most bigint not null,
	target_least bigint not null,
	toggled boolean not null,
	regex boolean not null,
	unique(bot_id, permission, target_most, target_least, regex)
);ANOTHEROPERATION
create table if not exists mcapi_links(
    bot_id integer not null,
    target_most bigint not null,
    target_least bigint not null,
    unique(bot_id, target_most, target_least)
);