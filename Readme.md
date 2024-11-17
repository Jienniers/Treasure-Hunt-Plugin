# Treasure Hunt Plugin

The Treasure Hunt plugin adds an exciting gameplay feature to Minecraft by enabling each player to participate in treasure chest quests. Players can embark on quests to search for hidden treasures, adding a sense of adventure and exploration to their Minecraft experience.

## Features

- **Treasure Generation**: Randomly generates treasure chests containing valuable loot at 50x50 radius from the player's location within the game world.
- **Reward System**: Players are rewarded with valuable items upon successfully completing a treasure hunt quest.

## Installation

1. **Download**: Download the latest version of the Treasure Hunt plugin (in .jar format) from the releases page.
2. **Installation**: Copy the downloaded .jar file into the "plugins" folder of your Minecraft server directory.
3. **Start Server**: Start or restart your Minecraft server.

## Usage

- **Starting a Treasure Hunt**: Use the `/startquest` command to initiate a treasure hunt quest.

## Development

### Command Class

The `Command` class handles the `/startquest` command, initiating a treasure hunt quest when executed by a player. It generates a random treasure location, creates a treasure chest, and assigns rewards to the chest.

## Support

If you encounter any issues or have suggestions for improvement, please submit an issue on the GitHub repository.

## License

This plugin is licensed under the MIT License. See the LICENSE file for details.
