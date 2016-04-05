# Marvel

This is an experimental app to train myself with stuff related with architecture, designs features, tests, etc.

It uses the [Marvel API](http://developer.marvel.com/). To execute it you need to add your public and secret key to the marvel.properties file on the assets folder.

Next steps:

- Use RX Java, and remove deprecated Event Bus
- Dagger2 has been integrated on a basic way, I'd like to experiment with more complicated scenarios
- Some basic test has been added to test their behaviour and configuration, but they are quite simple. I'd like to dedicate more time in the future to create more real tests convering more complicated scenarios

Note: If you wonder why there is no documentation in the code, you should read the [The Clean Code](https://it-ebooks24.com/ebook/clean-code). I write my code trying to do it clear, understandable and easy to read. This should be enough for not API projects.

I'm not using "Hungarian notation" neither, basically because there is no real reason to do it. This is an interesting post about this topic: [Just Say mNo to Hungarian Notation](http://jakewharton.com/just-say-no-to-hungarian-notation/)
