# RainRunner

This represents someone running in the rain. There's a question I've heard people ask; do you get less wet if you run through the rain, or walk?

I heard people claim both sides, and so for fun I created a program which roughly simulates the situation. The program ended up calculating that the faster you run, the less wet you get - but it approaches a limit.

This makes sense mathmatically, because if you assume a uniform distribution of rain in the air, then the fact that the rain is moving in the z-direction is irrelevant to the person's movement in the x-direction; and vise versa. So the person will be hit by rain on their head/shoulders as long as they are outside (therefore the total will be a function of speed), but regardless of the person's speed, they're going to have to cross through all of the air directly in front of them. The number of drops that hit their head vary inversely with speed, but the number of drops which hit their front are constant for any given distance. So the total drops hit varies inversely with speed, approaching the number of drops hitting their front, as predicted by the model.

The person's speed in this program is measured relative to the x-velocity of the rain, and the program also assumes that the person never gets fully drenched; that they can always get more wet.
