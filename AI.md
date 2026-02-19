# AI Usage

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
