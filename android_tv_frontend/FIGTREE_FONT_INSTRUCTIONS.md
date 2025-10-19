# Figtree Font Installation Instructions

The app is configured to use the Figtree font family. To complete the font integration, download the following font files from Google Fonts and place them in the appropriate directory.

## Required Font Files

Download and place these files in `app/src/main/res/font/`:

1. **figtree_regular.ttf** - Figtree Regular (Weight 400)
2. **figtree_medium.ttf** - Figtree Medium (Weight 500)
3. **figtree_semibold.ttf** - Figtree SemiBold (Weight 600)
4. **figtree_bold.ttf** - Figtree Bold (Weight 700)

## Download Instructions

1. Visit Google Fonts: https://fonts.google.com/specimen/Figtree
2. Click "Download family" button
3. Extract the downloaded ZIP file
4. Locate the static font files for the weights listed above
5. Rename them to match the names above (lowercase, underscores)
6. Place all four .ttf files in `app/src/main/res/font/` directory

## Alternative Download (Direct Links)

You can also download the Figtree font from:
- GitHub: https://github.com/erikdkennedy/figtree

## Font Configuration

The font family is already configured in `figtree.xml` and integrated into all text styles in `styles.xml`. Once you add the TTF files, the app will automatically use Figtree font across all text elements.

## Font Weight Mapping

- **Regular (400)**: Default body text
- **Medium (500)**: Intermediate weight
- **SemiBold (600)**: Device names, switch labels, count badges, titles
- **Bold (700)**: Header text (e.g., "Devices")

## Verification

After adding the font files, rebuild the app. All text should render in Figtree font with appropriate weights throughout the application.

## Note

Until the actual TTF files are added, the app will fall back to the system default fonts.
