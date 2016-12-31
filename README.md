# Scala Game Demo
A demo of making games in Scala.

The intent is to build one game in the `game` subproject and then implement several forms of playing.
At the moment, a HTML5 Canvas version can be played in a web browser (I've only tested it in Chrome).
I'm hoping to add the following interpreters for the Game:
- WebGL Browser
- Phaser (or Pixi or whatever) to show javascript interop
- ScalaFx for a simple desktop game
- Native OpenGL either view LWJGL or JOGL

I'm also hoping to make a multiplayer version that proves out Akka Persistence and Cluster Sharding as a viable way of managing game state.

# Attributions
The [Full plate golden armor](http://opengameart.org/content/lpc-full-plate-golden-armor) sprite by [Vivid reality](http://opengameart.org/users/vividreality) is licensed under [CC-BY 3.0](https://creativecommons.org/licenses/by/3.0/)