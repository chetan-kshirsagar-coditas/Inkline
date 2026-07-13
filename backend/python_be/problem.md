# Project Assignment: Inkline — A Digital Content Publishing Platform

## Overview

Inkline is a platform where writers create content, editors review and refine it, and approved content gets published for readers to browse. What makes Inkline different from a simple blogging tool is that every piece of content passes through an intelligent review panel before a human editor ever looks at it — the panel reads the content, checks it against multiple criteria, and hands the editor a clear, structured recommendation instead of a blank submission.

The platform has three kinds of people using it, each with a different view of the product and a different set of things they're allowed to do. Everything in the product — from logging in, to writing, to reviewing, to reading — should feel connected and alive: people should see things update in front of them without needing to refresh the page, and they should get timely emails when something needs their attention.

Build the complete product described below. Nothing here is optional unless explicitly marked as optional.

---

## Who Uses Inkline

There are exactly three roles. Every person using the platform belongs to one of these roles, and what they can see and do is strictly limited by their role.

**1. Author**
An Author writes content. They can create drafts, edit their own drafts, attach images and supporting files to their drafts, and submit drafts for review. An Author can only see and manage their own content — they cannot see drafts written by other Authors, and they cannot review or approve anything.

**2. Editor**
An Editor reviews content that has been submitted by Authors. They can see everything the intelligent review panel found about a submission, read the content itself, and then approve it, reject it, or send it back to the Author with comments requesting changes. An Editor can see all submissions in the queue but cannot create content of their own, and cannot manage other users.

**3. Admin**
An Admin manages the platform itself. They can create and remove user accounts, assign or change what role a person has, create and manage the categories that content can be organized under, and view everything happening on the platform — every draft, every review, every published piece. An Admin does not write or review content as part of their day-to-day role, but has full visibility into the platform.

A person attempting an action outside their role should be clearly and immediately blocked from doing so. For example, an Author should never be able to open another Author's draft, and an Editor should never be able to access user management.

---

## Module 1: Accounts and Access

Nobody sets a password on Inkline. To log in, a person enters their email address, and a one-time code is sent to that email. They enter the code on the platform and are logged in. This is the only way to log in — there is no separate password to remember or reset.

Once logged in, every person has a profile page where they can set their display name, write a short bio, and upload a profile picture. This profile picture, along with any other files uploaded anywhere on the platform, must be stored securely and reliably, and must load quickly whenever it's displayed.

An Admin is the only one who can create new accounts and decide what role each account has. When an Admin creates an account, the person receives an email inviting them to log in for the first time.

---

## Module 2: Writing and Submitting Content

This is where Authors work. An Author can start a new piece of content, give it a title, write the body of the content, and optionally organize it under a category (categories are set up by the Admin — for example, "Technology," "Travel," "Opinion").

While writing, an Author can attach a cover image for their piece and additional supporting files (for example, reference documents, extra images, or charts) as attachments. These files should be properly stored, retrievable at any time, and never lost even if the Author closes their browser or comes back days later.

A piece of content lives as a **draft** until the Author chooses to submit it. Drafts can be saved, edited, and re-edited any number of times. Once an Author submits a draft, it leaves their hands and enters the review process — the Author can no longer edit it unless an Editor sends it back to them with requested changes. The Author should always be able to see the current status of everything they've written: draft, under AI review, under editor review, changes requested, approved, or published.

---

## Module 3: The Intelligent Review Panel

This is the heart of what makes Inkline different, and it is the part of this assignment you should give the most thought and care to.

When an Author submits a piece of content, it does not go straight to a human Editor. Instead, it is first read and evaluated by a panel of independent reviewers — each one focused on a different concern, each one doing its job without depending on a human to tell it what to look for. Think of this like a small editorial board that reads the piece from different angles before the Editor even sees it. Design and build **at least four such reviewers**, each with a clearly distinct responsibility. For example (you are free to design your own, as long as each has a genuinely distinct focus and adds real value):

- A reviewer that reads the piece for overall quality — clarity, structure, grammar, and readability — and produces specific, actionable feedback.
- A reviewer that checks whether the piece is complete and internally consistent — for instance, whether it has a proper introduction and conclusion, whether claims made early in the piece are followed through later, or whether the piece actually matches the category it was submitted under.
- A reviewer that reads the tone and style of the piece and checks it against a simple, clearly defined set of platform guidelines (for example: no offensive language, no unsupported claims presented as fact, consistent voice throughout).
- A reviewer that generates a short summary of the piece and suggests relevant tags or keywords for it, to help readers discover it later.

Each of these reviewers should genuinely read the content and produce its own independent findings — not just repeat a generic response. Once all reviewers have finished, their individual findings should be brought together by a final step that produces one clear, consolidated recommendation for the Editor: a short summary of what the panel found, the key concerns (if any), and an overall suggestion of whether the piece looks ready to publish, needs minor changes, or needs major rework.

This entire process should run automatically the moment a piece is submitted, without any person needing to trigger it manually. The Author and Editor should be able to see, in real time, that the piece is currently being read by the panel, and see the panel's full findings the moment they're ready — appearing on their screen without needing to refresh the page.

The quality, independence, and usefulness of this panel — not just that it technically runs — is the single most important part of this assignment.

---

## Module 4: Editorial Review and Decisions

Once the intelligent review panel has finished its work, the piece appears in the Editor's queue, along with the panel's full consolidated findings displayed clearly alongside the actual content. The Editor reads both the content and the panel's recommendation and then makes one of three decisions:

- **Approve** — the piece is accepted and moves toward publishing.
- **Reject** — the piece is turned down, with a reason given to the Author.
- **Request changes** — the piece is sent back to the Author with specific comments explaining what needs to change. Once the Author edits and resubmits it, it should go through the intelligent review panel again before returning to the Editor.

While an Editor has a piece open, if another Editor tries to open the same piece, they should be able to see that it's already being looked at, so two Editors don't accidentally review the same thing at the same time. Any decision made by an Editor should be reflected instantly on the Author's side — the Author should see the status change and the comments appear live, without refreshing their page.

---

## Module 5: Publishing and the Content Library

Once a piece is approved, it becomes publicly visible in the Content Library — a browsable, searchable collection of everything that has been published on the platform. Anyone (including someone not logged in) should be able to browse the library, filter or search by category, and open any published piece to read it in full, including its cover image and any attachments meant to be public.

Each published piece should clearly show its title, author name, category, publish information, and the full content. The library should feel like a finished, polished reading experience — not a raw list of database entries.

---

## Module 6: Notifications and Activity

People should never have to go looking for updates — Inkline should tell them. Build a notification system covering at least the following moments, each triggering both an email and an instantly visible update inside the platform itself:

- A one-time login code, sent the moment someone tries to log in.
- A welcome email, sent the moment an Admin creates a new account for someone.
- A confirmation to the Author the moment their piece finishes being read by the intelligent review panel, letting them know it's now with an Editor.
- A notification to the Editor the moment a new piece is ready in their queue.
- A notification to the Author the moment an Editor approves, rejects, or requests changes on their piece, including any comments left.
- A confirmation to the Author the moment their piece is actually published and live in the library.

Inside the platform, every person should have an activity area (for example, a notification bell or panel) that fills up with these updates the moment they happen, without needing to refresh the page. Read and unread updates should be clearly distinguishable.

---

## What "Done" Looks Like

By the end of this assignment, someone should be able to:

1. Log in without ever typing a password.
2. Write a piece of content, attach a cover image and supporting files to it, and submit it.
3. Watch, in real time, as an intelligent panel of at least four independent reviewers reads the piece and produces a genuinely useful, consolidated recommendation.
4. See an Editor act on that recommendation, sending decisions and comments back to the Author instantly.
5. See a published piece appear in a clean, public content library.
6. Receive timely emails and in-platform updates at every meaningful moment along the way.
7. See every one of the above behave correctly and safely depending on whether the person doing it is an Author, an Editor, or an Admin.

This document describes the product only. How you build it — the tools, the structure, the design of each part — is entirely up to you.
