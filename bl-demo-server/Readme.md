# bl-demo-server

This submodule represents a simulation of what would happen when:

1.  The sensor in the front carriage of the train passes through the check in detection point on the bridge:
   - Train sends message to central with GPS position, train composition id, and timestamp
2.  The front carriage passes through the first sensor on the bridge
   - The bridge sends a message to central with timestamp
3.  The sensor in the last carriage of the train passes through the checkout detection point on the bridge
   - Train sends message to central with GPS position, train composition id, and timestamp
4.  The last carriage passes through the last sensor on the bridge
   - The bridge sends a message to central with timestamp

All messages are triangulated at central and the database gaps are automatically filled out.
The train server is also updated.

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
