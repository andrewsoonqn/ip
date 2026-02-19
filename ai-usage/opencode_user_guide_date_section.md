# User Guide Date & Time Section
_Generated on 20/02/2026 using OpenCode (Claude Sonnet 4.6)_

---

**User**

please help me improve the date section of my user guide. showcase the natural date handling

[Provided `DateTimeParser.java` and `DateTimeParserTest.java` as context]

---

**OpenCode**

[Reads the existing `docs/README.md`, then reads both provided files]

Here's a summary of all changes made to `docs/README.md`:

**Removed** the old inline bullet-point date/time note under the `deadline` section (it was incomplete and slightly wrong — it didn't mention 4-digit times or the year-omission behaviour properly).

**Added** a new top-level `## Date & Time Format` section at the end with three sub-sections:

- **Date** — table of the three input forms (`day/month/year`, `day/month/yy`, `day/month`), a prose explanation of the smart future-resolution logic with a visual example, and the 2-digit year sliding-window table (00–49 → 2000–2049, 50–99 → 1950–1999).
- **Time** — table of all four digit-length forms (1–4 digits), the default-to-23:59 rule, and an explicit note that colons are rejected.
- **Quick reference** — a single consolidated table showing eight representative inputs and their resolved date/times (drawn directly from the test cases in `DateTimeParserTest.java`).

**Cross-references** were added in the `deadline` and `event` sections pointing to the new section, and their format lines were updated to show `[time]` as optional.
