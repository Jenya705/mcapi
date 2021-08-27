create table if not exists mcapi_bots (
	id int primary key auto_increment,
	token char(51) not null,
	name varchar(64) not null,
	owner_most bigint not null,
	owner_least bigint not null,
	unique(token)
);

ANOTHEROPERATION

create table if not exists mcapi_permissions (
	bot_id int not null,
	permission varchar(255) not null,
	target_most bigint not null,
	target_least bigint not null,
	toggled boolean not null,
	constraint un_mcapi_permissions unique(bot_id, permission, target_most, target_least)
);