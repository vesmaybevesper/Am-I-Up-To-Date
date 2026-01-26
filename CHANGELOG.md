- Re-write
- Use more efficient JSON library
- Switch config to YACL
  - Split certain end user settings out to a different file
    - Modpack authors there is a migration tool that runs on start-up to migrate settings from the old config to the new, but you should double-check your settings!
      - Beta Users: It should detect that there has been activity on your config and not touch it
- Use [new `?include_changelog=false` parameter](https://ibb.co/sJz32vwp) on the Modrinth API URL
  - Existing users this will auto append to your URL if it isn't present
- All text is translatable now
- Fixed the "Ignore Update" clickable message not working ([#10](https://github.com/vesmaybevesper/Am-I-Up-To-Date/issues/10))
- Add Compatibility with 'Vanilla Notebook' ([#14](https://github.com/vesmaybevesper/Am-I-Up-To-Date/issues/14))


_I've decided to delay my FancyMenu support as it will require a change in the way I break up versions. It should be out shortly though!_ 