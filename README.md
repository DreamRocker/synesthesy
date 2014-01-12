synesthesy
==========

The synesthesy framework gives you a interface to analyze midi-based music events. It delivers functionality to map midi events to music notes, analyze music keys with a trained neuronal network and can determin rythmic changes.

The current state of the implementation supports only one midi instrument. It targets string based instruments (like pianos) but can be used for mostly everything.

Features
========

## Version 0.1

 - Implements functions for midi events (like noteOn, noteOff, sustain)
 - Pluggable caches to store different events or notes
 - Every note has a certain strength that decreases over time (Decayed Strength)
 - Support for the sustain pedal 
 - Music key analyze framework
   - A neuronal network that is trained to determin the music key
   - This network can be trained with csv files and is easily enhancable
 - Rythmic analyze framework (not final yet)

License
=======
LGPL
