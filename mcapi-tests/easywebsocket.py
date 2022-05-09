import asyncio
import json
import zlib
import sys
from websockets import connect

ALL_SUBSCRIPTIONS = [
    "inventory_move",
    "player_join",
    "player_link",
    "menu_click",
    "message",
    "player_block_break",
    "player_block_place",
    "player_quit",
    "subscribe",
    "command_interaction",
    "player_unlink",
    "entity_spawn",
    "entity_despawn"
]

async def send_object(websocket, obj):
    '''
    convert dict to json then compress it and send it.
    '''
    obj_json = json.dumps(obj)
    compressed_json = zlib.compress(bytes(obj_json, "utf-8"))
    await websocket.send(compressed_json)

async def receive_object(websocket):
    '''
    receives object from websocket the decompress it and return it as dictionary
    '''
    received_bytes = await websocket.recv()
    obj_json = zlib.decompress(received_bytes).decode("utf-8")
    return json.loads(obj_json)

async def main(token, subscriptions = ALL_SUBSCRIPTIONS, uri = "ws://localhost:8080/tunnel"):
    async with connect(uri) as ws:
        await send_object(ws, {"token": token})
        await send_object(ws, {"subscriptions": subscriptions})
        while True:
            print("Received object:\n" + json.dumps(await receive_object(ws), indent = 4))

asyncio.run(main(sys.argv[1], uri=f'ws://{sys.argv[2]}/tunnel' if len(sys.argv) > 2 else "ws://localhost:8080/tunnel"))