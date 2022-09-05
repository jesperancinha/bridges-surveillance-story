import asyncio
import logging
from pickle import PUT

from aiocoap import *

logging.basicConfig(level=logging.INFO)


async def main():
    context = await Context.create_client_context()
    message_raw = b"I wear big glasses, my name sounds like a Boulevard and my favorite language is Small Talk."
    request = Message(code=PUT, payload=message_raw, uri="coap://127.0.0.1:5683")
    try:
        response = await context.request(request).response
    except Exception as e:
        print('Failed to fetch resource:')
        print(e)
    else:
        print('Result: %s\n%r' % (response.code, response.payload))


if __name__ == "__main__":
    asyncio.run(main())
