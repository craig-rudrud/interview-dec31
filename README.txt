Instructions for running the program:
1. In a command prompt/terminal, navigate to the /out/artifacts/interviewdec30_jar/ directory.
2. Execute "java -jar interviewdec30.jar" to run the program.

Overview:
One of the design choices I made was to use polymorphism to create similar methods that behaved slightly differently depending on the user's needs and what type of data was being entered/returned. For example, depending on what type of JSON data was being parsed (either strings or integers), a specific readJSON() method would be called that would process and return relevant data.

Another idea I kept in mind was simplicity. Generating a message is a relatively simple task but requires several parts in order to make it work, especially if a custom message is being generated. I extensively reviewed Java documentation to learn about which methods I should use while writing code to generate standard or custom messages.

Language:
I picked Java for the language because it's the one I'm most familiar with. I also used an external JSON library to help with parsing the included JSON files.

Process: 
To verify the correctness of the program, I ran it several times using different inputs to see what output would be generated. If I had more time, I would have liked to include more input validation.

Summary:
If I had more time, I would have included extensive input validation to ensure the correctness of the program even further. I also would have liked to create more custom greetings in a JSON file, but I decided to opt for a standard greeting and a simple custom greeting format to save time. Lastly, I would have liked to create a simple GUI to generate messages.