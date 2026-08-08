# No Hotbar Swap Animation

A tiny Fabric client mod for Minecraft 1.21.11 that does three things:

1. Removes the item-switch ("equip") animation — the little dip/rise the
   held item does when you change hotbar slots. Switching slots is instant.
2. Hides the white selection-box outline that normally shows which hotbar
   slot is currently selected, so there's no visual indicator of which
   slot you're on (note: this also means **you** won't be able to see your
   own selected slot while playing — there's no way to hide it from
   others but keep it visible to yourself).
3. Hides the small pop-up text Sodium Extra shows when you toggle its
   "Light Updates" option (e.g. "Light updates disabled"). This works by
   filtering any pop-up message that mentions "light update" — it doesn't
   depend on Sodium Extra directly, so it'll keep working even if that mod
   changes internally, but it will also hide any other message containing
   that same phrase, from any source.

## Why you have to build this yourself

Compiling a Fabric mod requires downloading Minecraft's own libraries and
mapping files from Mojang/FabricMC's servers during the build (via Gradle).
I can't reach those servers from where I generated this project, so I
couldn't produce a finished `.jar` directly. The good news: the actual
build step is one command and takes a couple of minutes.

## Easiest option: let GitHub build the jar for you (no installs needed)

This project includes a GitHub Actions workflow (`.github/workflows/build.yml`)
that compiles the mod on GitHub's own servers and hands you the finished jar
as a download — no Java, Gradle, or anything else needed on your computer.

1. Go to https://github.com/new and create a new repository (public or
   private, doesn't matter, it's free either way). Don't initialize it with
   a README/gitignore.
2. On the new repo's page, click **"uploading an existing file"** and drag
   in the entire unzipped contents of this project — all folders and files,
   including the hidden `.github` folder. (If your file browser hides
   dotfiles/folders, either show hidden files first, or use `git` to push
   instead: `git init && git add -A && git commit -m init && git push`.)
3. Click the **Actions** tab at the top of the repo. A run called
   "Build mod jar" should already be running (if not, click it and press
   **"Run workflow"**).
4. Wait 1–2 minutes for the green checkmark.
5. Click into that run, scroll down to **Artifacts**, and download
   `no-hotbar-swap-anim-jar` — it's a zip containing your real
   `no-hotbar-swap-anim-1.0.0.jar`. That's the file to install or upload to
   Modrinth.

If you'd rather build it locally instead, here's how:

## What you need installed

- **Java Development Kit (JDK) 21** — https://adoptium.net/ (Minecraft 1.21.11 requires Java 21; make sure `java -version` shows 21)
- **Gradle isn't required separately** — you'll generate a wrapper below, or use a system Gradle 8.x/9.x if you already have one.

## Build steps

1. Unzip this project.
2. Open a terminal in the project folder.
3. If you have Gradle installed already, just run:
   ```
   gradle build
   ```
   If you don't have Gradle installed, generate the wrapper first (needs internet, one time):
   ```
   gradle wrapper --gradle-version 8.8
   ./gradlew build          # Linux/macOS
   gradlew.bat build        # Windows
   ```
4. The finished mod jar will be at:
   ```
   build/libs/no-hotbar-swap-anim-1.0.0.jar
   ```
   (There will also be a `...-sources.jar` — you only need the plain one.)

## Installing it locally to test

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.11.
2. Drop `no-hotbar-swap-anim-1.0.0.jar` into your `.minecraft/mods` folder.
3. Launch the Fabric 1.21.11 profile and switch hotbar slots — no more animation.

## Uploading to Modrinth

1. Create a new project at https://modrinth.com/dashboard/projects → "New project" → "Mod".
2. Set the loader to **Fabric**, game version **1.21.11**, environment **Client**.
3. Upload `no-hotbar-swap-anim-1.0.0.jar` as the file for that version.
4. Fill in the project name/description/icon as you like — the description in
   `fabric.mod.json` is just a starting point.

## Notes / customizing

- This targets **Minecraft 1.21.11** specifically (mappings + Minecraft version
  are pinned in `gradle.properties`), using Yarn mappings, Fabric Loader 0.18.1+,
  Loom 1.14, and Java 21 (all required for this Minecraft version). Note that
  1.21.11 is the last obfuscated Minecraft release — the very next version
  drops Yarn mappings entirely in favor of Mojang's own unobfuscated names, so
  a future port to that version would need more than a properties change.
- To target a *different* version instead, update `minecraft_version` and
  `yarn_mappings` in `gradle.properties` — field/method names inside
  `HeldItemRendererMixin.java` can shift between versions (they already changed
  between 1.20.1 and 1.21.11: `prevEquipProgress...` became `lastEquipProgress...`),
  so double-check them against that version's Yarn mappings if the build fails.
- All the actual logic is in one file:
  `src/main/java/com/example/nohotbaranim/mixin/HeldItemRendererMixin.java`
- This is a **client-side only** mod — it doesn't need to be installed on servers,
  and Modrinth will show it as client-only automatically based on `environment: client`
  in `fabric.mod.json`.
