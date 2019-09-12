# ![Tools](https://github.com/redwarp/9-Patch-Resizer/blob/develop/res/img/icon_32.png) 9-Patch-Resizer

A resizer tool to automaticaly resize png files and 9 patches in several densities (previously hosted on https://code.google.com/p/9patch-resizer/)

[![Build Status](https://travis-ci.org/redwarp/9-Patch-Resizer.svg?branch=develop)](https://travis-ci.org/redwarp/9-Patch-Resizer)

## Download

To get the latest build (.jar or .exe file), check the release page on the github project: https://github.com/redwarp/9-Patch-Resizer/releases

The .exe file is just a wrapper around the executable .jar file, use it if you don't feel comfortable with a java archive ^_^

## What is it exactly?

Let's face it : juggling with densities for Android is a bit of a pain, especially when dealing with 9 patch png.

And then comes this tool, that takes a xhdpi PNG file, or 9.png file, and generates ldpi, mdpi and hdpi png files automatically.

As simple as drag and drop can get.

And here is the [changelog](https://github.com/redwarp/9-Patch-Resizer/wiki/Changelog)

Current version : *1.4.2*

You're using 9patch resizer for your apps ? Don't hesitate and leave me a message!

## Links

 * Images and stuff found on http://www.clker.com/ (The online royalty free public domain clip art)
 * Images are downsized using an optimized incremental scaling algorithm proposed by Chris Campbell (whoever that is) - http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html

## Roadmap

I'll be honest, I don't really maintain 9-Patch-Resizer anymore, as 9-patch are somewhat a thing of the past, and I use as many vector drawables as possible lately.

I will of course consider bug fixes, but I might not have the bandwidth for feature requests.

Sorry about that.

- [ ] A proper "Settings" panel, to handle issues such as jpeg compression, etc etc...
- [ ] A few optimisations
- [x] Command line support
- [x] Options to set the input density (if people wan't to downsize from hdpi and not from xdpi for instance)
- [x] Proper JPG support

## Contributors

 * redwarp
 * Jean-Baptiste LAB - Made the app working in command line

Join us, and together, we can rule the galaxy as coders and...

## Notable forks

 * Soymonitus did a fork that also handles iOS resources, might come in handy for some people: https://github.com/soymonitus/9-Patch-Resizer

## Anyway...

If for some weird reasons, some of your PNG files aren't resized properly, don't hesitate to send them to me, so that I can investigate !
