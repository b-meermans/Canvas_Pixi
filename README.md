# Todo List

## Not thought through
* Sound requests
    * Easy to add sounds to the outgoing JSON file, then having that sound played as is.
      * Trickier to think about pausing or playing a longer running sound file.
        * Java would know what file it tried to play, but potentially only as a String.
        * JS would be playing the sound file. We may need to send information about all running sound files back to Java.
* File in/out
        * Need to implement reading and writing of files on a server.
* Standard input
        * Java uses System.in for its standard input, allowing text input directly into the console.
        * Can we mimic this?
            * Would need to differentiate what typed in values on the console are from the user.
            * Would need to put this data into some structure and tell System.in to utilize that.
            * Java has a way to re-map where System.in comes from.
            * However - asking for data from System.in should halt the system temporarily until input is received.
            * Several online coding environments have difficulty with this, it may be harder than expected.
                   * Some of those systems have the users type all input in before running the program -- BAD.
                   * Most of those systems use an additional component for text input, ie: a separate dialogue box -- BLAH
                    * The systems where it fully works are running Java on a server, so you have access to a shell -- not how we are running things.
* Dynamic image and sound file list
    * GUI side - want a way to easily see all images and sounds which are available.
      * Desire to have 'active' images and sounds, which are images and sounds which are already being used in this project.
            * Would want to display those as a separate category
            1) This allows students to easily see what is already in the project
        2) This allows us to prepopulate a project with expected images chosen by the curriculum team
* Image manipulation options
    * We may want students to dynamically change how an image looks, but not by just choosing a different file
    * Instead, students may wish to take an already defined image and draw shapes on top of that image
          * Currently, Java is only keeping track of the Filename of the image. So we would need to rework how images are defined.
* Intersecting objects
  * Which types of intersections do we care about?
  * What are the best/fastest algorithms?

## Thought through
* Call methods directly on actors when paused
  * Potential conceptual way to do this.
  * When paused, we can make every Pixi sprite become active.
  * We attach a mouse listener to the sprites, which can then grab the UUID.
  * With the UUID, we can call a java function on a specific sprite to ask for its fields/methods.
  * We can then send back a list of public fields/methods on that sprite using reflection.
  * JS allows object["methodName"] notation, so we should be able to call the Java functions that way.
  * Calling any single method means we'll need to ask for the state of all actors so we can update the visuals.
* Resizable stages
  * Two version of resizing.
    1) The Java Stages have a size value, we can send that data to Pixi
    2) Different sized screens will need to scale. Pixi can do this.
       * Implemented a basic + / - keyboard shortcut to see how scaling works - it is fine.
       * Probably want a more dynamic version, resizing automatically based on window size or allowing dragging to resize

## Ready to implement / test
* Interrupt an await if it has an endless loop
          * Potentially fixed - need to fully test.
          * Aops2DRunner has a couple of methods: exitIfNeeded, addMethodToStack, removeMethodFromStack.
          * At the beginning of each loop (for, while, do-while), we will automatically insert a call to exitIfNeeded.
          * At the beginning of each method (this also includes lambda expressions) call we will insert a call to addMethodToStack.
          * At the end of each method call (and lambda expressions) we will insert removeMethodFromStack.
          * exitIfNeeded is checking a clock - if the clock has exceeded some number of seconds, we throw a special exception
          * addMethodToStack is keeping track of the number of method calls on the stack, if it exceeds a value we throw a special exception
          * On any try/catch structure, we will insert a special first catch - looking very specifically for our special exceptions.
      * If we see one - we just throw it again. This is to prevent students from catching our exceptions.
        * Our exception will eventually be printed, and we'll return null to signify to JS that the program should stop.
    * Resizing Pixi is not hard. Need to adjust the JSON being sent over to include information about the current stage.
