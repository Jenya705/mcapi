insert into mcapi_permissions (bot_id, permission, target_most, target_least, toggled)
values (?, ?, ?, ?, ?)
on duplicate key update
toggled = ?;