insert into mcapi_permissions (bot_id, permission, target_most, target_least, toggled, regex)
values (?, ?, ?, ?, ?, ?)
on conflict(toggled) do update set
toggled = ?;