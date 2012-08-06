RealTalk
========

A chat plugin for bukkit

Features
--------
### Custom login/logout message
    login: '&a&n%username%&r&c has joined the game'
    logout: '&a&n%username%&r&c has left the game!'
### Prefix and Suffix
    prefix: '&f[&4Op&f] [&5'
    suffix: '&f]&c'
### Ordering
    format: '{PREFIX}{NAME}{SUFFIX} {MESSAGE}'
### As many groups as you want
      g1:
        prefix: '&f[&aGuest&f] [&d'
        suffix: '&f]&2'
      g2:
        prefix: '&e[&9Player&e] [&9'
        suffix: '&e]&7'
      g3:
        prefix: '&6[&5Developer&6] [&b'
        suffix: '&6]&c'
      customgroups: '3'
### Commands
* /reloader -- help
* /reloader reload -- reload config
* /reloader edit -- edit config in game
* /reloader get -- get part of config
* /me -- *%NAME% action
* /m -- Message
* /msg -- Message
* /t -- Message
* /tell -- Message

Extras
------

* Chat on talk(config)
* devbug(show me info on plugin)(config)
* chat on msg(not editable)

I have finished developing this plugin unless requested my another person
=========================================================================