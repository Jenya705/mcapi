insert into mcapi_permissions (bot_id, permission, target_most, target_least, toggled, regex)
values (?, ?, ?, ?, ?, ?)
on duplicate key update
toggled = ?;