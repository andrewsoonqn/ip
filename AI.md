# AI Usage

## General Experience with AI Tools

I had a lot of fun coding with OpenCode. I could spawn tons of sessions at once, and have plenty of credits through their free models (with limits) such as Kimi K2.5 and GLM5, which are actually quite good. Sometimes, I hit limits or have more sophisticated tasks. In these cases, I would use GitHub Copilot's API instead. It provides access to SOTA models like Claude Opus 4.6 and Sonnet 4.6.

OpenCode felt very lightweight because it is a TUI and navigable via solely the keyboard (often just keys near the homerow). E.g., switching models, or between build and plan modes takes just a second and I don't need to take my hand off the keyboard. I could also easily reference files as context, and it had great UX. 

I also use GitHub Copilot as a reviewer for my pull requests. It has helped me catch bugs and code logic flaws that are hard to spot. In many cases, it is able to detect code quality or logic issues that are hard to spot, e.g. subtle off-by-one errors in loop bounds, incorrect handling of edge cases in conditional logic, or methods that silently swallow exceptions without proper error propagation. In other cases, it is still safe as I still will look at these suggestions and decide whether they are valid, or whether there is extra context and nuance that the reviewer may have missed. I can either reference directly in a new issue to keep track of it, or sometimes spawn a Copilot session to have trivial bugs fixed, which then frees me to work on the next feature instead of tweaking a 95% done one.

## JSON Data Loading and Storing
* Tool used: Cursor
* Observations:
  * Although I did not provide much context, the LLM was able to accurately follow Jackson's API and implement correctly. I assume this is due to Jackson's API being mature and well-documented.
  * To deepen my understanding, I further conversed with the LLM to understand the API's syntax.
  * This saved me time and effort to look for the API documentation.
* Reference: Refer to [this file](ai-usage/cursor_data_loading_and_storing_strategy.md) for detailed chat log.

## Identifying Git Commit Hashes for Tags
* Tool used: OpenCode (Claude Sonnet 4.5)
* Observations:
  * I had a large backlog of requirement tags to add after extensive development work, and needed to identify which merge commits corresponded to each tag requirement.
  * The AI effectively analyzed git history using multiple parallel bash commands to examine branches, merge commits, and commit relationships. It was able to identify commits that were a match for each tag. 
  * This was an easy task as many commits were merge commits with commit messages that clearly matched the tags, e.g. "Merge branch-A-CI into master" matches the tags "A-CI".
  * I then prompt it to generate ready-to-use git tag commands with proper annotations, saving significant manual effort for writing it.
  * This was particularly useful because manually searching through extensive commit history would have been time-consuming and error-prone.
* Reference: Refer to [this file](ai-usage/opencode_git_tag_identification.md) for detailed chat log.

## User Guide Date and Time Section
* Tool used: OpenCode (Claude Sonnet 4.6)
* Observations:
  * Documentation is tedious. I wrote the code and understand the logic, plus, the logic is easily readable through the code. I just needed someone to navigate it and write it up.
  * I provided `DateTimeParser.java` and `DateTimeParserTest.java` as context. The AI read the existing user guide, identified what was missing or inaccurate, and added the date/time section.
  * The AI accurately captured all the nuances because it was explicit through code and test cases.
  * Since I coded this part, I understood the nuances of the output and checked and tweaked the AI response afterwards.
* Reference: Refer to [this file](ai-usage/opencode_user_guide_date_section.md) for detailed chat log.
